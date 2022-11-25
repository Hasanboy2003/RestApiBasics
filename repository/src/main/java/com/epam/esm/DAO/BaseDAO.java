package com.epam.esm.DAO;

import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * @author Hasanboy Makhmudov
 * @project Rest api basics
 * Base DAO
 */

public interface BaseDAO <T,ID>{

    boolean existByName(String name);
    T getByName(String name);
    T getById(ID id);

    List<T> findAll();

    boolean deleteById(ID  id);

    boolean   save(T entity);

    void deleteConnection(ID id);

    boolean existsById(ID  id);
}
