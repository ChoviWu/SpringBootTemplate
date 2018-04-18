package org.choviwu.example.service.impl;

import com.github.pagehelper.PageHelper;
import org.choviwu.example.common.annatation.LogAnnotation;
import org.choviwu.example.common.common.PageList;
import org.choviwu.example.common.common.Paginator;
import org.choviwu.example.common.model.Aa;
import org.choviwu.example.mapper.AaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ChoviWu on 2018/04/10
 * Description:
 */
@Service
public class AaServiceImpl {

    private final AaMapper aaMapper;

    @Autowired
    public AaServiceImpl(AaMapper aaMapper){
        this.aaMapper = aaMapper;
    }

    @LogAnnotation(value = "CBC")
    public PageList getList(Paginator p){
        PageHelper.startPage(p.getPageNum(),p.getPageSize());
        List list =  aaMapper.selectAll();
        return new PageList(list);
    }
    @Transactional
    public void insert(Aa a){
        aaMapper.insert(a);
    }
}
