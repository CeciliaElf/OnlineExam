package com.cecilia.programmer.entity.admin;

import org.springframework.stereotype.Component;

/**
 * 用户实体类
 * @author cecilia
 */

/**
 * id: 用户id，设置自增
 * username: 用户名，登录名
 * password: 密码
 * roleId: 所属角色 Id
 * photo: 大头贴
 * sex: 性别  0：未知 1：男 2：女
 * age: 年龄
 * address: 家庭住址
 */
@Component
public class User {
	private Long id;
	private String username;
	private String password;
	private Long roleId;
	private String photo;
	private String sex;
	private Integer age;
	private String address;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
