package com.renchao.annotation.pack03;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author ren_chao
 * @since 2024-08-22
 */
public class MyTypeFilter implements TypeFilter {

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
		// 过滤_Test结尾的Bean
		return classMetadata.getClassName().endsWith("_Test");
	}
}
