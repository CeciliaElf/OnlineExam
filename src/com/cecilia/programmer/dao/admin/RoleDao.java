package com.cecilia.programmer.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cecilia.programmer.entity.admin.Role;

/**
 * 角色 role dao
 * @author cecilia
 */
// Dao 用这个注解来表示是数据操作层
@Repository
public interface RoleDao {
	public int add(Role role);
	public int edit(Role role);
	public int delete(Long id);
	public List<Role> findList(Map<String, Object> queryMap);
	public int getTotal(Map<String, Object> queryMap);
	public Role find(Long id);
}
