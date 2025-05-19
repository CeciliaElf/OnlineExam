package com.cecilia.programmer.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cecilia.programmer.entity.admin.Authority;

/**
 * 权限 Service 接口
 * @author cecilia
 */
@Service
public interface AuthorityService {
	public int add(Authority authority);
	public int deleteByRoleId(Long roleId);
	public List<Authority> findListByRoleId(Long roleId);
}
