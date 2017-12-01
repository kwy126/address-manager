package com.circle.dao;

public interface BaseDAO<M> {
    public void create(M m);

    public void update(M m);

    public void delete(int uuid);

    public M getByUuid(int uuid);

}