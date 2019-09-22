package net.droidlabs.viking.processor.annotation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.type.TypeMirror

class AnnotationAttributesBuilder {
    private val codeBlockBuilder = CodeBlock.builder()
    private var isNotFirst = false

    fun addAttribute(typeMirror: TypeMirror): AnnotationAttributesBuilder {
        addCommaIfNotFirst()
        codeBlockBuilder.add("%T::class", typeMirror.asTypeName())
        return this
    }


    fun addAttribute(typeName : TypeName): AnnotationAttributesBuilder {
        addCommaIfNotFirst()
        codeBlockBuilder.add("%T::class", typeName)
        return this
    }

    fun addAttribute(packageName: String, className: String): AnnotationAttributesBuilder {
        addCommaIfNotFirst()
        codeBlockBuilder.add("%T::class", ClassName(packageName, className))
        return this
    }

    fun addAttributes(typeMirrors: List<TypeMirror>): AnnotationAttributesBuilder {
        for (typeMirror in typeMirrors) {
            addCommaIfNotFirst()
            codeBlockBuilder.add("%T::class", typeMirror.asTypeName())
        }
        return this
    }

    fun build(): CodeBlock {
        return CodeBlock.builder()
                .add("modules = [")
                .add(codeBlockBuilder.build())
                .add("]")
                .build()
    }

    private fun addCommaIfNotFirst() {
        if (isNotFirst) {
            codeBlockBuilder.add(", ")
        }
        isNotFirst = true
    }
}
