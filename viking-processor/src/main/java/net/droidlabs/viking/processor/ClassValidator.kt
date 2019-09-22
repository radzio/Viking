package net.droidlabs.viking.processor

import javax.lang.model.element.Modifier.ABSTRACT
import javax.lang.model.element.Modifier.PUBLIC
import javax.lang.model.element.TypeElement

internal object ClassValidator {

    fun isPublic(annotatedClass: TypeElement): Boolean {
        return annotatedClass.modifiers.contains(PUBLIC)
    }

    fun isAbstract(annotatedClass: TypeElement): Boolean {
        return annotatedClass.modifiers.contains(ABSTRACT)
    }
}
