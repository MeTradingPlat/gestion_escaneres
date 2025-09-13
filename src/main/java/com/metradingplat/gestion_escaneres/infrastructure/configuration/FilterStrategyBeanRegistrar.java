package com.metradingplat.gestion_escaneres.infrastructure.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.lang.NonNull;

import com.metradingplat.gestion_escaneres.domain.strategies.filtros.IEstrategiaFiltro;

import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

@Configuration
public class FilterStrategyBeanRegistrar implements BeanDefinitionRegistryPostProcessor {

    private static final String BASE_PACKAGE = "net.metradingplat.ms_escaner.domain.strategies.Filtros";

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(IEstrategiaFiltro.class));

        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(BASE_PACKAGE)) {
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> strategyClass = Class.forName(beanClassName);
                if (!strategyClass.isInterface() && IEstrategiaFiltro.class.isAssignableFrom(strategyClass)) {
                    registry.registerBeanDefinition(strategyClass.getSimpleName(), new RootBeanDefinition(strategyClass));
                }
            } catch (ClassNotFoundException e) {
                // Handle exception, e.g., log it
                System.err.println("Could not load class: " + beanClassName + " - " + e.getMessage());
            }
        }
    }

    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) {
        // No additional processing needed for the bean factory in this case
    }
}