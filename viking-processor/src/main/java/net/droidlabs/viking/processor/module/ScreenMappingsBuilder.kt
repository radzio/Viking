package net.droidlabs.viking.processor.module

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import dagger.Module
import net.droidlabs.dagger.annotations.ActivityScope
import net.droidlabs.viking.processor.AnnotatedClass
import net.droidlabs.viking.processor.Util
import net.droidlabs.viking.processor.annotation.AnnotationAttributesBuilder
import net.droidlabs.viking.processor.annotation.AnnotationUtil
import javax.lang.model.type.TypeMirror

class ScreenMappingsBuilder {

    fun buildTypeSpec(annotatedClasses: List<AnnotatedClass<*>>,
                      typesWithScope: List<TypeMirror>): TypeSpec {

        val moduleAnnotationBuilder = AnnotationSpec.builder(Module::class.java)

        val builder = TypeSpec.classBuilder("ScreenMappings")
                .addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)
                .addAnnotation(moduleAnnotationBuilder.build())

        for (annotatedClass in annotatedClasses) {
            builder.addFunction(buildProvideMethod(annotatedClass, typesWithScope))
        }

        return builder.build()
    }

    private fun buildProvideMethod(annotatedClass: AnnotatedClass<*>,
                                   typesWithScope: List<TypeMirror>): FunSpec {
        return FunSpec.builder(
                String.format("provide%s", annotatedClass.className))
                .addModifiers(KModifier.ABSTRACT, KModifier.PUBLIC)
                .addAnnotation(buildDaggerAnnotation(annotatedClass, typesWithScope))
                .addAnnotation(AnnotationSpec.builder(ActivityScope::class.asTypeName()).build())
                .returns(ClassName(annotatedClass.classPackage, annotatedClass.className))
                .build()
    }

    private fun buildDaggerAnnotation(annotatedClass: AnnotatedClass<*>,
                                      typesWithScope: List<TypeMirror>): AnnotationSpec {
        val annotationBuilder = AnnotationSpec.builder(ClassName("dagger.android", "ContributesAndroidInjector"))

        val modulesAttributesBuilder = AnnotationAttributesBuilder()
                .addAttribute(annotatedClass.classPackage, annotatedClass.className + "_Module")
                .addAttributes(AnnotationUtil.getValuesForAttribute("includes", annotatedClass))

        for (type in typesWithScope) {
            if (annotatedClass.typeMirror == type) {
                modulesAttributesBuilder
                        .addAttribute(
                                Util.getPackageName(type), Util.getSimpleTypeName(type) + "Fragments_Module")
            }
        }

        annotationBuilder.addMember(modulesAttributesBuilder.build())

        return annotationBuilder.build()
    }
}
