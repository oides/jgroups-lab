package br.edu.ifba.gsort.inf623.distributeddatabase.persistence;

import java.io.Serializable;
import java.util.Collection;

public interface GenericDao<T, PK extends Serializable> {
	
    T create(T t);
    T read(PK id);
    T update(T t);
    void delete(T t);
    Collection<T> findAll();
    
}