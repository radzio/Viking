package net.droidlabs.viking.processor.module

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeSpec.Companion.interfaceBuilder
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import dagger.Subcomponent
import net.droidlabs.dagger.annotations.ActivityScope
import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.di.ScreenComponent
import net.droidlabs.viking.di.ScreenComponentBuilder
import net.droidlabs.viking.processor.AnnotatedClass
import net.droidlabs.viking.processor.CodeBuilder
import net.droidlabs.viking.processor.Util
import net.droidlabs.viking.processor.annotation.AnnotationAttributesBuilder
import java.util.*
import javax.lang.model.type.TypeMirror

class ComponentCodeBuilder : CodeBuilder {
    override fun buildTypeSpec(annotatedClass: AnnotatedClass<*>): TypeSpec {
        val module =  ClassName(annotatedClass.classPackage, annotatedClass.className + "_Module")


        val builder = interfaceBuilder(
                annotatedClass.className + "_Component")
                .addModifiers(KModifier.PUBLIC)
                .addSuperinterface(ScreenComponent::class.asClassName()
                        .parameterizedBy(annotatedClass.getTypeElement().asClassName())
                )

        val parsedAnnotation = Util.getAnnotation(AutoModule::class.java,
                annotatedClass.getTypeElement())

        val typeMirrors = parsedAnnotation!!["includes"] as Array<Any>

        val types = ArrayList<TypeName>()

        for (obj in typeMirrors) {
            val typeMirror = obj as TypeMirror
            types.add(typeMirror.asTypeName())
        }

        types.add(module)

        val annotationAttributesBuilder = AnnotationAttributesBuilder()

        types.forEach {
            annotationAttributesBuilder.addAttribute(it)
        }


        builder.addAnnotation(
                AnnotationSpec.builder(ActivityScope::class.asClassName()).build())

        builder.addAnnotation(AnnotationSpec.builder(Subcomponent::class.asClassName())
                .addMember(annotationAttributesBuilder.build())
                .build())

        val builderBuilder = interfaceBuilder("Builder")
                .addModifiers(KModifier.PUBLIC)
                .addAnnotation(Subcomponent.Builder::class.java)
                .addSuperinterface(

                        ScreenComponentBuilder::class.asClassName()
                                .parameterizedBy(
                                        ClassName(annotatedClass.classPackage, annotatedClass.className + "_Module"),
                                        ClassName(annotatedClass.classPackage, annotatedClass.className + "_Component")
                                )

                )

        for (obj in typeMirrors) {
            val typeMirror = obj as TypeMirror

            builderBuilder.addFunction(FunSpec.builder("screenModule")
                    .addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)
                    .addParameter("module", typeMirror.asTypeName())
                    .returns(ClassName(annotatedClass.classPackage, annotatedClass.className + "_Component"))
                    .build())
        }

        builder.addType(builderBuilder.build())

        return builder.build()
    }
}
