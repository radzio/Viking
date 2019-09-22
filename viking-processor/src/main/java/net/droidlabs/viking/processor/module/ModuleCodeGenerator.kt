package net.droidlabs.viking.processor.module

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeSpec.Companion.classBuilder
import dagger.Module
import dagger.Provides
import net.droidlabs.viking.annotations.AutoProvides
import net.droidlabs.viking.processor.AnnotatedClass
import net.droidlabs.viking.processor.CodeBuilder
import javax.annotation.processing.ProcessingEnvironment
import javax.inject.Named
import javax.lang.model.element.ExecutableElement
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap
import kotlin.reflect.jvm.internal.impl.name.FqName

class ModuleCodeGenerator(private val processingEnvironment: ProcessingEnvironment) : CodeBuilder {

    override fun buildTypeSpec(annotatedClass: AnnotatedClass<*>): TypeSpec {

        val module = annotatedClass.className + "_Module"
        val builder = classBuilder(module).addModifiers(KModifier.PUBLIC)

        builder.addAnnotation(Module::class.java)

        for (element in annotatedClass.executableElements) {
            builder.addFunction(createProvidesMethod(annotatedClass, element))
        }

        return builder.build()
    }

    private fun createProvidesMethod(annotatedClass: AnnotatedClass<*>,
                                     element: ExecutableElement): FunSpec {
        val builder = FunSpec.builder("provides" + element.simpleName)
                .addAnnotation(Provides::class.java)

        if (shouldBeNamed(element)) {
            builder.addAnnotation(AnnotationSpec
                    .builder(Named::class.java)
                    .addMember("value = %S", resolveNamedValue(element))
                    .build())
        }

        return builder.addModifiers(KModifier.PUBLIC)
                .addParameter(ParameterSpec.builder(
                        name = "view",
                        type = ClassName.bestGuess(annotatedClass.getTypeElement().qualifiedName.toString()) as TypeName
                ).build())
                .addCode("return view.%N();", element.simpleName)
                .returns(ClassName.bestGuess(element.returnType.toString()).javaToKotlinType())
                .build()
    }

    private fun resolveNamedValue(element: ExecutableElement): String {
        return element.getAnnotation(AutoProvides::class.java).value
    }

    private fun shouldBeNamed(element: ExecutableElement): Boolean {
        return !element.getAnnotation(AutoProvides::class.java).value.isEmpty()
    }

    // https://github.com/square/kotlinpoet/issues/236
    private fun TypeName.javaToKotlinType(): TypeName = if (this is ParameterizedTypeName) {
        (rawType.javaToKotlinType() as ClassName).parameterizedBy(
                *typeArguments.map { it.javaToKotlinType() }.toTypedArray()
        )
    } else {
        val className = JavaToKotlinClassMap.INSTANCE
                .mapJavaToKotlin(FqName(toString()))?.asSingleFqName()?.asString()
        if (className == null) this
        else ClassName.bestGuess(className)
    }
}
