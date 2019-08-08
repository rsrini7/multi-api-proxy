package com.github.rsrini7.api.config.elide;

import com.yahoo.elide.core.DataStoreTransaction;
import com.yahoo.elide.core.PersistentResource;
import com.yahoo.elide.core.RequestScope;
import com.yahoo.elide.core.filter.expression.FilterExpression;
import com.yahoo.elide.core.pagination.Pagination;
import com.yahoo.elide.core.sort.Sorting;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Optional;


@Slf4j
public class ExternalApiTransaction implements  DataStoreTransaction {

    /**
     * Constructor.
     */
    protected ExternalApiTransaction() {
        super();
    }

    /**
     * External Api transaction, do nothing.
     * @param entity - the object to save.
     * @param scope - contains request level metadata.
     */
    @Override
    public void save(Object entity, RequestScope scope) {
        // External Api transaction, do nothing.
    }

    /**
     * External Api transaction, do nothing.
     * @param entity - the object to delete.
     * @param scope - contains request level metadata.
     */
    @Override
    public void delete(Object entity, RequestScope scope) {
        // External Api transaction, do nothing.
    }

    /**
     * External Api transaction, do nothing.
     * @param scope the request scope for the current request
     */
    @Override
    public void flush(RequestScope scope) {
        // External Api transaction, do nothing.
    }

    /**
     * External Api transaction, do nothing.
     * @param scope the request scope for the current request
     */
    @Override
    public void commit(RequestScope scope) {
        // External Api transaction, do nothing.
    }

    /**
     * External Api transaction, do nothing.
     * @param entity - the object to create in the data store.
     * @param scope - contains request level metadata.
     */
    @Override
    public void createObject(Object entity, RequestScope scope) {
        // External Api transaction, do nothing.
    }

    /**
     * External Api transaction, do nothing.
     * @param entityClass the type of class to load
     * @param id - the ID of the object to load.
     * @param filterExpression - security filters that can be evaluated in the data store.
     * @param scope - the current request scope. It is optional for the data store to attempt evaluation.
     * @return a new persistent resource with a new instance of {@code entityClass}
     */
    @Override
    public Object loadObject(Class<?> entityClass,
                             Serializable id,
                             Optional<FilterExpression> filterExpression,
                             RequestScope scope) {
        // Loads are supported but empty object (with specified id) is returned.
        // NOTE: This is primarily useful for enabling objects of solely computed properties to be fetched.
        Object entity;
        try {
            entity = entityClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            log.error("Could not load object {} through NoopStore", entityClass, e);
            throw new RuntimeException(e);
        }

        String uuid = scope.getUUIDFor(entity);
        // Side-effecting method of the PersistentResource :( however, it enables us to do this without reinventing
        // the wheel. Should probably be refactored eventually nonetheless.
        new PersistentResource<>(entity, null, uuid, scope).setId(id.toString());

        return entity;
    }

    /**
     * External Api transaction, do nothing.
     * @param entityClass - the class to load
     * @param filterExpression - filters that can be evaluated in the data store.
     * It is optional for the data store to attempt evaluation.
     * @param sorting - sorting which can be pushed down to the data store.
     * @param pagination - pagination which can be pushed down to the data store.
     * @param scope - contains request level metadata.
     * @return a {@link Collections#singletonList} with a new persistent resource with id 1
     */
    @Override
    public Iterable<Object> loadObjects(Class<?> entityClass,
                                        Optional<FilterExpression> filterExpression,
                                        Optional<Sorting> sorting,
                                        Optional<Pagination> pagination,
                                        RequestScope scope) {

        log.debug("Filter : {} - Sorting : {} - Pagination : {}",filterExpression.orElseGet(() -> null),sorting.orElseGet(() -> null),pagination.orElseGet(() -> null));

        // Default behavior: load object 1 and return as an array
        return Collections.singletonList(this.loadObject(entityClass, 1L, filterExpression, scope));
    }

    /**
     * External Api transaction, do nothing.
     */
    @Override
    public void close() throws IOException {
        // External Api transaction, do nothing.
    }

}
