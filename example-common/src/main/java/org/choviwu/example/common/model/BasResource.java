package org.choviwu.example.common.model;

import javax.persistence.*;

@Table(name = "bas_resource_t")
public class BasResource {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 资源代码
     */
    @Column(name = "resource_code")
    private String resourceCode;

    /**
     * 代码名称
     */
    @Column(name = "resource_name")
    private String resourceName;

    /**
     * 1-菜单 2-按钮
     */
    @Column(name = "resource_type")
    private Byte resourceType;

    /**
     * url链接
     */
    @Column(name = "resource_url")
    private String resourceUrl;

    /**
     * 父菜单ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "resource_sort")
    private Integer resourceSort;

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
     * 获取资源代码
     *
     * @return resource_code - 资源代码
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * 设置资源代码
     *
     * @param resourceCode 资源代码
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    /**
     * 获取代码名称
     *
     * @return resource_name - 代码名称
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * 设置代码名称
     *
     * @param resourceName 代码名称
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * 获取1-菜单 2-按钮
     *
     * @return resource_type - 1-菜单 2-按钮
     */
    public Byte getResourceType() {
        return resourceType;
    }

    /**
     * 设置1-菜单 2-按钮
     *
     * @param resourceType 1-菜单 2-按钮
     */
    public void setResourceType(Byte resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 获取url链接
     *
     * @return resource_url - url链接
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * 设置url链接
     *
     * @param resourceUrl url链接
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    /**
     * 获取父菜单ID
     *
     * @return parent_id - 父菜单ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单ID
     *
     * @param parentId 父菜单ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return resource_sort
     */
    public Integer getResourceSort() {
        return resourceSort;
    }

    /**
     * @param resourceSort
     */
    public void setResourceSort(Integer resourceSort) {
        this.resourceSort = resourceSort;
    }
}