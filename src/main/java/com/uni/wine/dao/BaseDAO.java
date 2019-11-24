package com.uni.wine.dao;

public interface BaseDAO<T> {

    void add(T element);

    T getById(int id);

    void removeById(int id);

    void update(T element);

    int count();
}
