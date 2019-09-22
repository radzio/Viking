package net.droidlabs.viking.processor

import com.squareup.kotlinpoet.TypeSpec

interface CodeBuilder {
    fun buildTypeSpec(annotatedClass: AnnotatedClass<*>): TypeSpec
}
