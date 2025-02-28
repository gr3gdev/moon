package io.github.gr3gdev.fenrir.jpa;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.github.gr3gdev.fenrir.jpa.JPAManager.*;

/**
 * Interface for CRUD actions with database.
 *
 * @param <E> the entity
 * @param <K> the type of the primary key
 */
public interface JpaCrudRepository<E, K> extends JpaTransactionalRepository {

    /**
     * @return Class of the entity
     */
    Class<E> getDomainClass();

    /**
     * @return Class of the primary key
     */
    Class<K> getIdClass();

    /**
     * Find all entities.
     *
     * @return List of entity
     */
    default List<E> findAll() {
        return entityManager()
                .createQuery(selectFromClass(getDomainClass()))
                .getResultList();
    }

    /**
     * Find an entity by primary key.
     *
     * @param id the primary key value
     * @return Optional of entity
     */
    default Optional<E> findById(K id) {
        return entityManager()
                .createQuery(selectFromClass(getDomainClass(), getPrimaryKeyFilter(getDomainClass(), id), Map.of()))
                .getResultList()
                .stream()
                .findFirst();
    }

    /**
     * Add or update an entity.
     * <p>
     * If the primary key is null then persist a new entity. Else update the entity.
     *
     * @param entity the entity (not null)
     * @return Entity with updates
     */
    default E save(E entity) {
        if (entity == null) {
            throw new RuntimeException("Cannot save a null entity");
        }
        return executeInTransaction(() -> {
            if (getIdValue(getDomainClass(), getIdClass(), entity) == null) {
                entityManager().persist(entity);
                entityManager().flush();
                return entity;
            } else {
                return entityManager().merge(entity);
            }
        });
    }

    /**
     * Delete an entity.
     *
     * @param entity the entity (not null)
     */
    default void delete(E entity) {
        if (entity == null) {
            throw new RuntimeException("Cannot delete a null entity");
        }
        executeInTransaction(() -> entityManager().remove(entity));
    }

    /**
     * Delete an entity by primary key.
     *
     * @param id the primary key value
     * @return count of deleted lines
     */
    default int deleteById(K id) {
        return executeInTransaction(() -> entityManager().createQuery(
                        deleteFromClass(getDomainClass(), getPrimaryKeyFilter(getDomainClass(), id)))
                .executeUpdate());
    }
}
