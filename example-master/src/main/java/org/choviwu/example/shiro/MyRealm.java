package org.choviwu.example.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.choviwu.example.common.exception.CrudException;
import org.choviwu.example.common.model.*;
import org.choviwu.example.common.util.MD5;
import org.choviwu.example.mapper.BasResourceMapper;
import org.choviwu.example.mapper.BasRoleMapper;
import org.choviwu.example.mapper.BasUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by ChoviWu on 2018/04/10
 * Description:
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private BasUserMapper userMapper;
    @Autowired
    private BasRoleMapper roleMapper;
    @Autowired
    private BasResourceMapper resourceMapper;

    /**
     * 赋权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        BasUser user=(BasUser) principalCollection.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
        List<String> permissions=new ArrayList<>();
        List<String> roleList=new ArrayList<>();
        Map map = new HashMap();
        map.put("userId",user.getId());
        List<BasRole> roles = roleMapper.getRoleByUserId(user.getId());
        if(roles.size()>0) {
            for(BasRole role : roles) {
                List<BasResource> resourceList = resourceMapper.getResourceListByRoleId(role.getId());
                if(resourceList.size()>0) {
                    for(BasResource resource : resourceList) {
                        permissions.add(resource.getResourceUrl());
                    }
                }
                roleList.add(role.getRoleName());
            }
        }

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setRoles(new HashSet<>(roleList));
        info.addStringPermissions(permissions);//将权限放入shiro中.
        return info;
    }

    /**
     * 登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken utoken=(UsernamePasswordToken) authenticationToken;//获取用户输入的token
        BasUser user = userMapper.getUserByUserName(utoken.getUsername());
        if(StringUtils.isEmpty(user))
            throw new CrudException("user.login.username.no_such_user");
        Map map = new HashMap();
        String salt = user.getSalt();
        String password = null;
        try {
            password = MD5.toMD5(utoken.getPassword()+salt);
            ((UsernamePasswordToken) authenticationToken).setPassword(password.toCharArray());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.clear();
        //登录校验
        map.put("mobile",user.getPhone());
        map.put("password",user.getPassword());
        user = userMapper.getNameAndPassword(map);
        if(StringUtils.isEmpty(user))
            throw new CrudException("user.login.username.no_such_user");
        //放入shiro.调用CredentialsMatcher检验密码
        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());
    }
}
