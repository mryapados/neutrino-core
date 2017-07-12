package org.neutrinocms.core.bo.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
@Repeatable(value = BOResources.class)
public @interface BOResource {
	public enum ResourceType {
		CSS, JS
	}
	
	ResourceType type() default ResourceType.CSS;

	String value();
}
