package net.droidlabs.viking.processor

import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror

class AnnotatedClass<T>( val typeElement2: TypeElement,
                        val executableElements: List<ExecutableElement>,
                        private val annotatedClass: TypeElement
) {

    val className: String = typeElement2.simpleName.toString()

    val typeMirror: TypeMirror
        get() = typeElement2.asType()

    val classPackage: String
        get() = Util.getPackage(getTypeElement()).qualifiedName.toString()

    fun getTypeElement(): TypeElement {
        return annotatedClass
    }

}
