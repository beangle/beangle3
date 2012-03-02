package org.beangle.struts2.view;

public interface UIIdGenerator {

	public static final String GENERATOR = "UIIdGenerator";

	public String generate(Class<?> clazz);
}
