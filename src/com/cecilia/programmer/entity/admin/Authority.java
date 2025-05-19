package com.cecilia.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 权限实体
 * @author cecilia
 */
@Component
public class Authority {
	private Long id;
	private Long roleId;   // 角色Id
	private Long MenuId;   // 菜单Id
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return MenuId;
	}
	public void setMenuId(Long menuId) {
		MenuId = menuId;
	}
}
