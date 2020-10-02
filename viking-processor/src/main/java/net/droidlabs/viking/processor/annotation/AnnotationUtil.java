package net.droidlabs.viking.processor.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.type.TypeMirror;

import net.droidlabs.viking.processor.AnnotatedClass;
import net.droidlabs.viking.annotations.AutoModule;

import static net.droidlabs.viking.processor.Util.getAnnotation;

public final class AnnotationUtil {
  private AnnotationUtil() {
  }

  public static List<TypeMirror> getValuesForAttribute(String attribute,
      AnnotatedClass annotatedClass) {
    Map<String, Object> parsedAnnotation = getAnnotation(AutoModule.class,
        annotatedClass.getTypeElement());

    Object[] types = (Object[]) parsedAnnotation.get(attribute);
    List<TypeMirror> typeMirrors = new ArrayList<>();
    for (Object type : types) {
      TypeMirror typeMirror = (TypeMirror) type;
      typeMirrors.add(typeMirror);
    }

    return typeMirrors;
  }
}
