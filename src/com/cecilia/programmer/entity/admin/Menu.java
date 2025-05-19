package com.cecilia.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 菜单实体类
 * @author cecila
 */

/**
 * id: id
 * parentId: 双亲Id 默认值为-1 代表顶级分类
 * _parentId: 用来匹配 easyui 的父类 id
 * name: 菜单名称
 * url: 点击后的 url
 * icon：菜单 icon 图标
 */
@Component
public class Menu {
	private Long id;
	private Long parentId;
	private Long _parentId;
	private String name;
	private String url;
	private String icon;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Long get_parentId() {
		_parentId = parentId;
		return _parentId;
	}
	public void set_parentId(Long _parentId) {
		this._parentId = _parentId;
	}
}
