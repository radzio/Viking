package net.droidlabs.viking.processor

import com.squareup.kotlinpoet.TypeSpec

interface ExtendedCodeBuilder {
    fun buildTypeSpec(vararg annotatedClass: AnnotatedClass<*>): TypeSpec
}
