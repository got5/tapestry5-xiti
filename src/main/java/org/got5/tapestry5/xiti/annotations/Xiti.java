package org.got5.tapestry5.xiti.annotations;

import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.COMPONENT;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.MIXIN;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.PAGE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.tapestry5.ioc.annotations.UseWith;

/**
* This annotation can be used to track a page
*
*/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@UseWith({COMPONENT, MIXIN, PAGE})
public @interface Xiti {

	// Parent.document or top.document or document	
	String xtnv() default "document";
		
	String xtsd() default "";
		
	// Site number
	String xtsite() default "";
	
	// Level 2 site
	String xtn2() default "";
	
	// Page name (with the use of :: to create chapters)
	String xtpage() default "";

	// Implication degree
	String xtdi() default "";
	
	// Customized indicators
	String xt_multc() default "";
	
	// Numeric identifier
	String xt_an() default "";
	
	// Category
	String xt_ac() default "";
}
