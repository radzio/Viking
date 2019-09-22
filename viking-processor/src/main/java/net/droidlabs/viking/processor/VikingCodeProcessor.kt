package net.droidlabs.viking.processor

import com.google.auto.service.AutoService
import com.google.common.collect.ImmutableSet
import com.google.common.collect.Iterables
import com.google.common.collect.ListMultimap
import com.google.common.collect.MultimapBuilder
import com.squareup.kotlinpoet.FileSpec
import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.annotations.AutoProvides
import net.droidlabs.viking.processor.annotation.AnnotationUtil
import net.droidlabs.viking.processor.module.ComponentCodeBuilder
import net.droidlabs.viking.processor.module.ModuleCodeGenerator
import net.droidlabs.viking.processor.module.ModuleScopedCodeGenerator
import net.droidlabs.viking.processor.module.ScreenMappingsBuilder
import java.io.File
import java.io.IOException
import java.util.*
import java.util.Collections.singleton
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.SourceVersion.latestSupported
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic.Kind.ERROR


@AutoService(Processor::class)
class VikingCodeProcessor : AbstractProcessor() {
    private var processingEnvironment: ProcessingEnvironment? = null
    private var messager: Messager? = null
    private val typesWithScope = ArrayList<TypeMirror>()

    private lateinit var kotlinFilePath : File

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        processingEnvironment = processingEnv
        messager = processingEnv.messager
    }

    override fun getSupportedOptions(): Set<String> {
        return singleton(KAPT_KOTLIN_GENERATED_OPTION_NAME)
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(AutoModule::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return latestSupported()
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: run {
            processingEnv.messager.printMessage(ERROR, "Can't find the target directory for generated Kotlin files.")
            return false
        }


        kotlinFilePath = File(kaptKotlinGeneratedDir)
        handleAutoModule(roundEnv)
        return true
    }

    private fun handleAutoModule(roundEnv: RoundEnvironment) {
        val annotatedClasses = ArrayList<AnnotatedClass<*>>()
        val annotatedClassesWithScopeAttribute = MultimapBuilder.hashKeys().arrayListValues().build<TypeMirror, AnnotatedClass<*>>()
        for (annotatedElement in roundEnv.getElementsAnnotatedWith(AutoModule::class.java)) {

            val annotatedTypeElement = annotatedElement as TypeElement
            if (!isValidClass(annotatedTypeElement)) {
                return
            }

            val annotatedClass = buildAnnotatedClass(annotatedTypeElement)

            val saved = saveScopeRelated(annotatedClassesWithScopeAttribute, annotatedClass)
            if (!saved) {
                annotatedClasses.add(annotatedClass)
            }
        }

        try {
            val allAnnotatedClasses = Iterables
                    .concat(annotatedClasses, annotatedClassesWithScopeAttribute.values())

            generateModules(ImmutableSet.copyOf<AnnotatedClass<*>>(allAnnotatedClasses).asList())
            generateScopeRelated(annotatedClassesWithScopeAttribute)
            generateMappings(annotatedClasses)
        } catch (e: IOException) {
            messager!!.printMessage(ERROR, "Couldn't generate class")
        }

    }

    private fun saveScopeRelated(
            annotatedClassesWithScopeAttribute: ListMultimap<TypeMirror, AnnotatedClass<*>>,
            annotatedClass: AnnotatedClass<*>): Boolean {
        val typeMirrors = AnnotationUtil.getValuesForAttribute("scopes", annotatedClass)
        for (typeMirror in typeMirrors) {
            addToTypesWithScope(typeMirror)
            addToScopedAnnotatedClass(annotatedClassesWithScopeAttribute, annotatedClass, typeMirror)
        }

        return !typeMirrors.isEmpty()
    }

    private fun addToTypesWithScope(typeMirror: TypeMirror) {
        for (type in typesWithScope) {
            if (type == typeMirror) {
                return
            }
        }
        typesWithScope.add(typeMirror)
    }

    private fun addToScopedAnnotatedClass(
            annotatedClassesWithScopeAttribute: ListMultimap<TypeMirror, AnnotatedClass<*>>,
            annotatedClass: AnnotatedClass<*>, typeMirror: TypeMirror) {
        val classesWithinOneScope = annotatedClassesWithScopeAttribute.get(typeMirror)
        classesWithinOneScope.add(annotatedClass)
    }

    private fun isValidClass(annotatedClass: TypeElement): Boolean {

        if (!ClassValidator.isPublic(annotatedClass)) {
            val message = String.format("Classes annotated with %s must be public.",
                    ANNOTATION)
            messager!!.printMessage(ERROR, message, annotatedClass)
            return false
        }

        if (ClassValidator.isAbstract(annotatedClass)) {
            val message = String.format("Classes annotated with %s must not be abstract.",
                    ANNOTATION)
            messager!!.printMessage(ERROR, message, annotatedClass)
            return false
        }

        return true
    }

    private fun buildAnnotatedClass(annotatedClass: TypeElement): AnnotatedClass<*> {
        val variableNames = ArrayList<ExecutableElement>()
        for (element in annotatedClass.enclosedElements) {

            val pojo = element.getAnnotation(AutoProvides::class.java) ?: continue

            val variableElement = element as ExecutableElement
            variableNames.add(variableElement)
        }

        return AnnotatedClass<Any>(annotatedClass, variableNames, annotatedClass)
    }

    @Throws(IOException::class)
    private fun generateModules(annotatedClasses: List<AnnotatedClass<*>>) {
        for (annotatedClassClass in annotatedClasses) {
            val moduleTypeSpec = ModuleCodeGenerator(processingEnvironment!!).buildTypeSpec(
                    annotatedClassClass)
            val moduleFile = FileSpec.get(annotatedClassClass.classPackage, moduleTypeSpec).toBuilder().build()
            moduleFile.writeTo(kotlinFilePath)
        }
    }

    @Throws(IOException::class)
    private fun generateScopeRelated(
            annotatedClassesWithScopeAttribute: ListMultimap<TypeMirror, AnnotatedClass<*>>) {
        for (typeMirror in annotatedClassesWithScopeAttribute.keySet()) {
            val annotatedClasses = annotatedClassesWithScopeAttribute.get(typeMirror)

            val moduleTypeSpec = ModuleScopedCodeGenerator(Util.getSimpleTypeName(typeMirror))
                    .buildTypeSpec(*annotatedClasses.toTypedArray<AnnotatedClass<*>>())
            val moduleFile =  FileSpec.get(Util.getPackageName(typeMirror), moduleTypeSpec).toBuilder().build()
            moduleFile.writeTo(kotlinFilePath)
        }
    }

    @Throws(IOException::class)
    private fun generateMappings(annotatedClasses: List<AnnotatedClass<*>>) {
        if (annotatedClasses.size == 0) {
            return
        }

        for (annotatedClassClass in annotatedClasses) {
            val componentTypeSpec = ComponentCodeBuilder().buildTypeSpec(annotatedClassClass)
            val componentFile = FileSpec.get(annotatedClassClass.classPackage,
                    componentTypeSpec).toBuilder().build()
            componentFile.writeTo(kotlinFilePath)
        }

        val screenMappingsFile = FileSpec.get("net.droidlabs.viking.di",
                ScreenMappingsBuilder().buildTypeSpec(annotatedClasses, typesWithScope)).toBuilder().build()
        screenMappingsFile.writeTo(kotlinFilePath)
    }

    companion object {
        private val ANNOTATION = "@" + AutoModule::class.java.simpleName
        private const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}
