package com.github.rsrini7.api.config.elide;

import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.core.DataStoreTransaction;
import com.yahoo.elide.core.EntityDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.Objects;
import java.util.Set;

public class ExternalApiDataStore implements DataStore {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiDataStore.class);

    protected final AutowireCapableBeanFactory beanFactory;
    protected final EntityManager entityManager;
    protected final boolean isSpringDependencyInjection;

    /**
     * Constructor.
     *
     * @param beanFactory   Spring AutowireCapableBeanFactory
     * @param entityManager EntityManager
     */
    public ExternalApiDataStore(
            AutowireCapableBeanFactory beanFactory,
            EntityManager entityManager,
            boolean isSpringDependencyInjection

    ) {
        this.beanFactory = beanFactory;
        this.entityManager = entityManager;
        this.isSpringDependencyInjection = isSpringDependencyInjection;
    }


    @Override
    public void populateEntityDictionary(EntityDictionary dictionary) {
        if (isSpringDependencyInjection) {
            logger.info("Spring Dependency Injection is enabled, "
                    + "Spring will try to inject beans each time an object of entityClass type is instantiated by Elide. ");
        }
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        /* bind all entities */
        entities.stream()
                .map(EntityType::getJavaType)
                .filter(Objects::nonNull)
                .forEach(mappedClass -> {
                    try {
                        dictionary.lookupEntityClass(mappedClass);
                        // Bind if successful
                        dictionary.bindEntity(mappedClass);
                        if (isSpringDependencyInjection) {
                            // Bind Spring Dependency Injection
                            dictionary.bindInitializer(beanFactory::autowireBean, mappedClass);
                        }
                    } catch (IllegalArgumentException e) {
                        // Ignore this entity
                        logger.debug("Ignoring entity", e);
                    }
                });
    }

    @Override
    public DataStoreTransaction beginTransaction() {
        return new ExternalApiTransaction();
    }
}
