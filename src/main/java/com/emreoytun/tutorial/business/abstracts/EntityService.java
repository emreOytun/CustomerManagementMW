package com.emreoytun.tutorial.business.abstracts;

import java.util.List;

// Interface for crud operations.
public interface EntityService<E> {
    void add(E entity);
    List<E> getAll();
    void delete(int id);
    void update(E entity);
    E getById(int id);
}
