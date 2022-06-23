
package com.frank.prueba.interfaces;

import java.util.List;

public interface ICRUD <T>{
    List<T> getAll();
    T getOneByID(Long id);
    boolean create(T entity);
    boolean delete(Long id);
    boolean update(T entity);
}
