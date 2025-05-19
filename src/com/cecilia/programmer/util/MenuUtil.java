package com.cecilia.programmer.util;

import java.util.ArrayList;
import java.util.List;

import com.cecilia.programmer.entity.admin.Menu;

/**
 * 关于菜单操作的一些公用方法
 * @author cecilia
 */
public class MenuUtil {
	/**
	 * 从给定的菜单中返回所有顶级菜单
	 * @param menuList
	 * @return
	 */
	public static List<Menu> getAllTopMenu(List<Menu> menuList){
		List<Menu> ret = new ArrayList<Menu>(); // 创建一个空的 ArrayList 用于存放找到的顶级菜单
		for(Menu menu:menuList){ // 遍历输入列表 menuList 中的每一个 Menu 对象。在每次循环中，当前遍历到的 Menu 对象会被赋值给变量 menu。
			if(menu.getParentId() == 0){
				ret.add(menu); // 如果 menu.getParentId() 的值确实是 0，那么就认为这个 menu 是一个顶级菜单，将它添加到之前创建的 ret 列表中
			}
		}
		return ret;
	}
	
	/**
	 * 获取所有二级菜单
	 * @param menuList
	 * @return
	 */
	public static List<Menu> getAllSecondMenu(List<Menu> menuList){
		List<Menu> ret = new ArrayList<Menu>(); // 创建一个空的 ArrayList ret 来存放找到的二级菜单。
		List<Menu> allTopMenu =  getAllTopMenu(menuList);
		for (Menu menu:menuList){
			for (Menu topMenu: allTopMenu) {
				if (menu.getParentId() == topMenu.getId()) {
					ret.add(menu);
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 获取某个二级菜单下的按钮
	 * @param menuList
	 * @param url
	 * @return
	 */
	public static List<Menu> getAllThirdMenu(List<Menu> menuList, Long secondMenuId) { // secondMenuId 想要查找其子菜单的那个特定的二级菜单的 ID。
		List<Menu> ret = new ArrayList<Menu>();
		for (Menu menu:menuList){
			if (menu.getParentId() == secondMenuId) {
				ret.add(menu);
			}
		}
		return ret;
	}
}
