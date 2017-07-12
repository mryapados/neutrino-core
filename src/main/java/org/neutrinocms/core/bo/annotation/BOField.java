package org.neutrinocms.core.bo.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
public @interface BOField {
	public enum ValueType {
		NULL, 
		BOOLEAN, INTEGER, FLOAT, DOUBLE, 
		PASSWORD, VARCHAR50, VARCHAR255, TEXT, HTML,
		DATETIME, DATE, TIME, 
		IMAGE, FILE, URL, 
		COLOR, 
		TOBJECT, NTOBJECT, OBJECT, 
		COLLECTION,
		ENUM,
		ICON
	}
	public enum SortType {
		NULL, ASC, DESC
	}



	
	ValueType type();

	ValueType ofType() default ValueType.NULL;

	boolean inList() default true;
	boolean inView() default true;
	boolean editable() default true;
	
	SortType sortBy() default SortType.NULL;
	int sortPriority() default 100;
	
	boolean defaultField() default false;
	
	int displayOrder() default 9999;
	
	String tabName() default "";
	String groupName() default "";
	
	public enum Default {
		NULL
	}
	Class<? extends Enum<?>> ofEnum() default Default.class;
	
	String defaultValue() default "";
}
