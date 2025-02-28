package io.github.gr3gdev.fenrir.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * The entity manager factory.
 */
@RequiredArgsConstructor
class FenrirEntityManagerFactory {

    private final List<Class<?>> entityClasses;
    private final Properties fenrirProperties;

    /**
     * @return EntityManager
     */
    EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    private EntityManagerFactory getEntityManagerFactory() {
        final PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), null).build();
    }

    private FenrirPersistenceUnitInfo getPersistenceUnitInfo(String name) {
        return new FenrirPersistenceUnitInfo(name, getEntityClassNames(), this.fenrirProperties);
    }

    private List<String> getEntityClassNames() {
        return this.entityClasses.parallelStream()
                .map(Class::getName)
                .collect(Collectors.toList());
    }
}
