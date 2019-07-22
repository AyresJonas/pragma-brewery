package com.pragma.brewery.repository;

import java.util.HashSet;
import java.util.Set;

import com.pragma.brewery.domain.GenericEntityWithId;

public class GenericRepository<T extends GenericEntityWithId> {
  protected Set<T> data;

  public GenericRepository() {
    this.data = new HashSet<T>();
  }

  public T insert(T obj) {
    data.add(obj);

    return obj;
  }

  public boolean delete(T obj) {
    T objToDelete = findById(obj.getId());
    if(objToDelete == null) {
      return false;
    }

    return data.remove(objToDelete);
  }

  public T update(T obj) {
    T oldObj = findById(obj.getId());

    if(!delete(oldObj)) {
      return null;
    }

    insert(obj);

    return obj;
  }

  public T findById(String id) {
    for(T obj : data) {
      if(obj.getId().equals(id)) {
        return obj;
      }
    }

    return null;
  }

  public Set<T> findAll() {
    return data;
  }
}
