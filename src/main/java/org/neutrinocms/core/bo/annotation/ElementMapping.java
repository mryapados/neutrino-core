package org.neutrinocms.core.bo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface ElementMapping {

	String[] value();
	
}

