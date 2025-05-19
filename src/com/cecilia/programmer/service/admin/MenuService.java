package com.cecilia.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cecilia.programmer.entity.admin.Menu;

/**
 * 菜单管理 service
 * @author cecilia
 */
@Service
public interface MenuService {
	public int add(Menu menu);
	// 调用 DAO 方法查询符合条件的菜单列表
	public List<Menu> findList(Map<String, Object> queryMap);
	public List<Menu> findTopList();
	public int getTotal(Map<String, Object> queryMap);
	public int edit(Menu menu);
	public int delete(Long id);
	public List<Menu> findChildrenList(Long parnetId);
	public List<Menu> findListByIds(String ids);
}
