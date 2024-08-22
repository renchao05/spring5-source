package com.renchao.annotation.pack04;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author ren_chao
 * @since 2024-08-22
 */
public class MyImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		System.out.println("====================");
		// 获取当前配置类的名称（包名 + 类名）
		System.out.println(importingClassMetadata.getClassName());
		// 获取当前配置类上的所有注解类型（注解的类名）
		System.out.println(importingClassMetadata.getAnnotationTypes());
		// getAnnotationAttributes(String annotationName): 获取指定注解的所有属性及其值。
		// hasAnnotation(String annotationName): 判断类上是否存在指定的注解。
		// hasMetaAnnotation(String metaAnnotationName): 判断类上的注解是否被指定的元注解标注。
		// getAllAnnotationAttributes(String annotationName): 获取指定注解的所有属性及其可能的多
		// 。。。。
		System.out.println("====================");
		return new String[]{"com.renchao.annotation.pack04.Bean041"};
	}
}
