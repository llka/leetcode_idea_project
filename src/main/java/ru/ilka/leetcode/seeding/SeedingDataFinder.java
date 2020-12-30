package ru.ilka.leetcode.seeding;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.HashSet;
import java.util.Set;

public class SeedingDataFinder<T> {

    public Set<T> findInitialData(String scanPackage) {
        Set<T> initialEntities = new HashSet<>();
        ClassPathScanningCandidateComponentProvider classScanner =
                new ClassPathScanningCandidateComponentProvider(false);
        classScanner.addIncludeFilter(new AnnotationTypeFilter(SeedingData.class));
        for (BeanDefinition beanDef : classScanner.findCandidateComponents(scanPackage)) {
            InitialData initialData = createInstance(beanDef);
            initialEntities.addAll(initialData.getEntities());
        }
        return initialEntities;
    }

    private InitialData createInstance(BeanDefinition beanDef) {
        try {
            Class<?> clazz = Class.forName(beanDef.getBeanClassName());
            return (InitialData) clazz.getDeclaredConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + beanDef.getBeanClassName(), e);
        }
    }
}
