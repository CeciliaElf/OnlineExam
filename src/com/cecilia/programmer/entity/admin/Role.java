package com.cecilia.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 角色 Role 实体
 * @author cecilia
 * id: 角色 id
 * name: 角色名称
 * remark: 角色备注
 */
@Component
public class Role {
	private Long id;
	
	private String name;
	
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
