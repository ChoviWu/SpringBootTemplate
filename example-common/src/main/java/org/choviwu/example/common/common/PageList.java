package org.choviwu.example.common.common;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * ChoviWu Created By 2018年4月10日15:10:19
 */
public class PageList implements Serializable {
    private Paginator paginator;
    private List list;

    public PageList(List list) {

        if(list instanceof Page){
            paginator=new Paginator((Page)list);
            this.list=list;
        }
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
