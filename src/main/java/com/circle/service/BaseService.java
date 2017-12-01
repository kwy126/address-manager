package com.circle.service;

import com.circle.dao.BaseDAO;
import com.circle.util.LogUtil;

/**
 * Created by keweiyang on 2017/3/16.
 */
public class BaseService<M> extends LogUtil implements IBaseService<M> {
    private BaseDAO dao = null;

    public void setDao(BaseDAO dao) {
        this.dao = dao;
    }

    public void create(M m) {
        dao.create(m);

    }

    public void update(M m) {
        dao.update(m);

    }

    public void delete(int uuid) {
        dao.delete(uuid);

    }

    public M getByUuid(int uuid) {
        return (M) dao.getByUuid(uuid);
    }

   /* public Page<M> getByCondition(QM qm) {
        List<M> list = dao.getByConditionPage(qm);
        qm.getPage().setResult(list);

        return qm.getPage();
    }*/

}
