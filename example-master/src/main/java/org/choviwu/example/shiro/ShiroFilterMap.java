package org.choviwu.example.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChoviWu on 2018/04/11
 * Description:
 */
@ConfigurationProperties(prefix = "spring.shiro.static.filter")
public class ShiroFilterMap {

    private List<String> anon = new ArrayList<String>();

    public List<String> getAnon() {
        return anon;
    }

    public void setAnon(List<String> anon) {
        this.anon = anon;
    }
}
