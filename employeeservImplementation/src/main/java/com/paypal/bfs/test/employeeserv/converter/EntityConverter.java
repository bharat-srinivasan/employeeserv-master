package com.paypal.bfs.test.employeeserv.converter;

/**
 * Converter for handling conversions between Entity class and model class
 *
 * @param <M> Model class type
 * @param <E> Entity class type
 */
public interface EntityConverter<M, E> {
    E toEntity(M model);

    M toModel(E entity);
}
