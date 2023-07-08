

/*
 * Copyright (c) 2022-2023 the original author Brody Gaudel MOUNANGA
 * You may use this file and this project under the terms defined by the license
 */

package org.mounangabouka.brodygaudel.repository;

import java.util.List;

public interface Repository <T>{

    /**
     * find by id (id must be a string)
     * @param id the id you want to find object
     * @return object found (can return null if object not found)
     */
    T findById(String id);

    /**
     * save object
     * @param object the object you want to save
     * @return object saved (null if object not saved)
     */
    T save(T object);

    /**
     * update object
     * @param object object yout want to update
     * @return object updated
     */
    T update(T object);

    /**
     * get all object
     * @return list of object found
     */
    List<T> findAll();

    /**
     * delete object by id
     * @param id the id of object you want to delete
     */
    void deleteById(String id);
}
