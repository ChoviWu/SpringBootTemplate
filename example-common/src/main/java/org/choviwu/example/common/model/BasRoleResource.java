package org.choviwu.example.common.model;

import javax.persistence.*;

@Table(name = "bas_role_resource_t")
public class BasRoleResource {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 资源id
     */
    @Column(name = "source_id")
    private Integer sourceId;

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
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取资源id
     *
     * @return source_id - 资源id
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * 设置资源id
     *
     * @param sourceId 资源id
     */
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}