package net.droidlabs.viking.processor;

import com.squareup.javapoet.TypeSpec;

public interface ExtendedCodeBuilder {
  TypeSpec buildTypeSpec(AnnotatedClass... annotatedClass);
}
