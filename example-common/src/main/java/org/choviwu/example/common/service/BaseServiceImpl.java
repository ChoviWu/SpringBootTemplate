package org.choviwu.example.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by ChoviWu
 * Date: 2017-12-05
 * Description :
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected Mapper<T> mapper;

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public T selectOne(T entity){
        return mapper.selectOne(entity);
    }

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }
    @Override
    public int saveNotNull(T entity){
        return mapper.insertSelective(entity);
    }

    @Override
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> selectAll() {

        return mapper.selectAll();
    }

}
