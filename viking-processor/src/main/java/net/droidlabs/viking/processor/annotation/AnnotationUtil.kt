package net.droidlabs.viking.processor.annotation

import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.processor.AnnotatedClass
import net.droidlabs.viking.processor.Util.getAnnotation
import java.util.*
import javax.lang.model.type.TypeMirror

object AnnotationUtil {

    fun getValuesForAttribute(attribute: String,
                              annotatedClass: AnnotatedClass<*>): List<TypeMirror> {
        val parsedAnnotation = getAnnotation(AutoModule::class.java,
                annotatedClass.typeElement2)

        val types = parsedAnnotation!![attribute] as Array<Any>
        val typeMirrors = ArrayList<TypeMirror>()
        for (type in types) {
            val typeMirror = type as TypeMirror
            typeMirrors.add(typeMirror)
        }

        return typeMirrors
    }
}
