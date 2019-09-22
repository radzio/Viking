package net.droidlabs.viking.processor.module

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeSpec.Companion.classBuilder
import com.squareup.kotlinpoet.asClassName
import dagger.Module
import net.droidlabs.viking.processor.AnnotatedClass
import net.droidlabs.viking.processor.ExtendedCodeBuilder
import net.droidlabs.viking.processor.annotation.AnnotationAttributesBuilder
import net.droidlabs.viking.processor.annotation.AnnotationUtil

class ModuleScopedCodeGenerator(private val moduleName: String) : ExtendedCodeBuilder {

    override fun buildTypeSpec(vararg annotatedClasses: AnnotatedClass<*>): TypeSpec {
        val module = moduleName + "Fragments_Module"
        val builder = classBuilder(module).addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)

        builder.addAnnotation(Module::class.java)

        for (annotatedClass in annotatedClasses) {
            builder.addFunction(buildProvidesMethod(annotatedClass))
        }

        return builder.build()
    }

    private fun buildProvidesMethod(annotatedClass: AnnotatedClass<*>): FunSpec {
        return FunSpec.builder("provides" + annotatedClass.className)
                .addAnnotation(buildDaggerAnnotation(annotatedClass))
                .addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)
                .returns(annotatedClass.getTypeElement().asClassName())
                .build()
    }

    private fun buildDaggerAnnotation(annotatedClass: AnnotatedClass<*>): AnnotationSpec {
        val annotationBuilder = AnnotationSpec.builder(ClassName("dagger.android", "ContributesAndroidInjector"))

        val modules = AnnotationAttributesBuilder()
                .addAttribute(annotatedClass.classPackage, annotatedClass.className + "_Module")
                .addAttributes(AnnotationUtil.getValuesForAttribute("includes", annotatedClass))
                .build()

        annotationBuilder.addMember(modules)

        return annotationBuilder.build()
    }
}
