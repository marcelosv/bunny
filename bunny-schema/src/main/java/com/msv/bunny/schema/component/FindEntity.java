package com.msv.bunny.schema.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

@Component
public class FindEntity {

	private List<Class<? extends Entity>> listClass = null;

	public List<Class<? extends Entity>> findAnnotatedClass(String scanPackage) {

		if (listClass != null) {
			return listClass;
		}

		this.listClass = findAnnotatedClass(scanPackage);
		
		return listClass;
	}

	private List<Class<? extends Entity>> findClass(String scanPackage) {
		ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
		List<Class<? extends Entity>> lista = new ArrayList<Class<? extends Entity>>();
		for (BeanDefinition beanDef : provider
				.findCandidateComponents(scanPackage)) {
			
			Class clazz = getClass(beanDef);
			
			if (clazz == null) {
				continue;
			}
			
			lista.add(clazz);
		}

		return lista;

	}

	private ClassPathScanningCandidateComponentProvider createComponentScanner() {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		return provider;
	}

	private Class<? extends Entity> getClass(BeanDefinition beanDef) {
		try {
			Class<? extends Entity> cl = (Class<? extends Entity>) Class.forName(beanDef.getBeanClassName());
			return cl;
		} catch (Exception e) {
			
		}

		return null;
	}

}
