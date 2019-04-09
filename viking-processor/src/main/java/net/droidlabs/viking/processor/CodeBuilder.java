package net.droidlabs.viking.processor;

import com.squareup.javapoet.TypeSpec;

public interface CodeBuilder {
  TypeSpec buildTypeSpec(AnnotatedClass annotatedClass);
}
