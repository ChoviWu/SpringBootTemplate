package org.choviwu.example.common.model;

import javax.persistence.*;

@Table(name = "bas_role_t")
public class BasRole {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 角色
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色
     *
     * @return role_name - 角色
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色
     *
     * @param roleName 角色
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取角色描述
     *
     * @return description - 角色描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置角色描述
     *
     * @param description 角色描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}