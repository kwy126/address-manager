package com.circle.service;

/**
 * Created by keweiyang on 2017/3/16.
 */
public interface IBaseService<M>  {
    public void create(M m);

    public void update(M m);

    public void delete(int uuid);

    public M getByUuid(int uuid);

   /* public Page<M> getByCondition(QM qm);*/
}
