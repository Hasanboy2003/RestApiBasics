package com.epam.esm.dao;

import java.util.List;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Base DAO
 */


public interface BaseDAO <T,I>{

    boolean existByName(String name);
    T getByName(String name);
    T getById(I id);

    List<T> findAll();

    boolean deleteById(I  id);

    boolean   save(T entity);

    void deleteConnection(I id);

    boolean existsById(I  id);
}
