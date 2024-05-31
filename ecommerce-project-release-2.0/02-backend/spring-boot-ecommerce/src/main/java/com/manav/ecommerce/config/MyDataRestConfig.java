package com.manav.ecommerce.config;

import com.manav.ecommerce.entity.Product;
import com.manav.ecommerce.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(repositoryRestConfiguration, cors);


        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};


        //disable HTTP methods for Product: PUT, POST and DELETE
        repositoryRestConfiguration.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));


        //disable HTTP methods for ProductCategory: PUT, POST and DELETE
        repositoryRestConfiguration.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));


        // call an internal helper method
        exposeIds(repositoryRestConfiguration);

    }

    private void exposeIds(RepositoryRestConfiguration repositoryRestConfiguration) {
        // expose entity ids

        // get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // create an array of the entity types

        List<Class<?>> entityClasses = new ArrayList<>();

        for (EntityType<?> tempEntityTypes : entities) {
            entityClasses.add(tempEntityTypes.getJavaType());
        }

        // expose the entity ids for the array of entity/domain types
        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
//        Class<?>[] domainTypes;

        repositoryRestConfiguration.exposeIdsFor(domainTypes);
    }
}
