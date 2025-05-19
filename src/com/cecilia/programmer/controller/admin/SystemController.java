package com.cecilia.programmer.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cecilia.programmer.entity.admin.Authority;
import com.cecilia.programmer.entity.admin.Menu;
import com.cecilia.programmer.entity.admin.Role;
import com.cecilia.programmer.entity.admin.User;
import com.cecilia.programmer.service.admin.AuthorityService;
import com.cecilia.programmer.service.admin.LogService;
import com.cecilia.programmer.service.admin.MenuService;
import com.cecilia.programmer.service.admin.RoleService;
import com.cecilia.programmer.service.admin.UserService;
import com.cecilia.programmer.util.CpachaUtil;
import com.cecilia.programmer.util.MenuUtil;
import com.github.pagehelper.util.StringUtil;
/**
 * 系统操作控制类
 * @author cecilia
 */
@Controller
@RequestMapping("/system")
public class SystemController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private LogService logService;
	
	/**
	 * 系统登录后主界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<Menu> userMenus = (List<Menu>)request.getSession().getAttribute("userMenus");
	    // 处理userMenus为null的情况
	    if (userMenus == null) {
	        userMenus = new ArrayList<Menu>(); // 初始化为空列表，避免空指针异常
	    }
		model.addObject("topMenuList", MenuUtil.getAllTopMenu(userMenus));
		model.addObject("secondMenuList", MenuUtil.getAllSecondMenu(userMenus));
		model.setViewName("system/index");
		return model;  // WEB-INF/views/system/index.jsp
	}
	
	/**
	 * 欢迎界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(ModelAndView model) {
		model.setViewName("system/welcome");
		return model;
	}
	
	/**
	 * 登录界面
	 * @param model
	 * @return
	 */
	// GET 打开一个页面，POST 提交表单
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		return model;
	}
	
	/**
	 * 登录表单提交处理控制器
	 * @param user
	 * @param cpacha
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody  // 返回的时候只返回一个 json 字符串
	public Map<String, String> loginAct(User user, String cpacha, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type", "error");
			ret.put("msg", "请填写用户讯息");
			return ret;
		}
		if(StringUtil.isEmpty(cpacha)) {
			ret.put("type", "error");
			ret.put("msg", "请填写验证码");
			return ret;
		}
		if(StringUtil.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "请填写用户名");
			return ret;
		}
		if(StringUtil.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "请填写密码");
			logService.add("用户名为" + user.getUsername() + "的用户登录时密码错误");
			return ret;
		}
		// 从 request 里获取session
		Object loginCpacha = request.getSession().getAttribute("loginCpacha");
		if(loginCpacha == null) {
			ret.put("type", "error");
			ret.put("msg", "会话已过期，请刷新页面");
			return ret;
		}
		if(!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())) {
			ret.put("type", "error");
			ret.put("msg", "验证码错误");
			logService.add("用户名为" + user.getUsername() + "的用户登录时输入验证码错误");
			return ret;
		}
		User findByUsername = userService.findByUsername(user.getUsername());
		if(findByUsername == null) {
			ret.put("type", "error");
			ret.put("msg", "该用户名不存在");
			logService.add("登录时，用户名为" + user.getUsername() + "的用户不存在");
			return ret;
		}
		if(!user.getPassword().equals(findByUsername.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码错误");
			return ret;
		}
		// 说明用户名密码以及验证码都正确
		// 此时需要查询用户的角色权限
		Role role = roleService.find(findByUsername.getRoleId());
		List<Authority> authorityList =  authorityService.findListByRoleId(role.getId()); // 根据角色获取权限列表
		String menuIds = "";
		for (Authority authority: authorityList) {
			menuIds += authority.getMenuId() + ",";
		}
		if (!StringUtils.isEmpty(menuIds)) {
			menuIds = menuIds.substring(0, menuIds.length() - 1);
		}
		List<Menu> userMenus =  menuService.findListByIds(menuIds); // 原始的用户列表讯息
		// 把角色信息，菜单信息放到 session 中
		request.getSession().setAttribute("admin", findByUsername);
		request.getSession().setAttribute("role", role);
		request.getSession().setAttribute("userMenus", userMenus);
		ret.put("type", "success");
		ret.put("msg", "登录成功");
		logService.add("用户名为" + user.getUsername()  + "角色为" + role.getName() + "的用户," + "登录成功");
		return ret;
	}
	
	/**
	 * 后台退出注销功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("admin", null);
		session.setAttribute("role", null);
		request.getSession().setAttribute("userMenus", null);
		return "redirect:/system/login";
	}
	
	/**
	 * 修改密码页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit_password", method = RequestMethod.GET)
	public ModelAndView editPassword(ModelAndView model) {
		model.setViewName("system/edit_password");
		return model;
	}
	
	@RequestMapping(value = "/edit_password", method = RequestMethod.POST)
	@ResponseBody  // 返回的时候只返回一个 json 字符串
	public Map<String, String> editPasswordAct(String newPassword,String oldPassword, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(newPassword)) {
			ret.put("type", "error");
			ret.put("msg", "请填写新密码");
			return ret;
		}
		User user =  (User)request.getSession().getAttribute("admin");
		if (!user.getPassword().equals(oldPassword)) {
			ret.put("type", "error");
			ret.put("msg", "原密码错误");
			return ret;
		}
		user.setPassword(newPassword);
		if (userService.editPassword(user) <= 0) {
			ret.put("type", "error");
			ret.put("msg", "密码修改失败，请联系管理员");
		}
		ret.put("type", "success");
		ret.put("msg", "密码修改成功");
		logService.add("用户名为" + user.getUsername() + "的用户" + "成功修改密码");
		return ret;
	}
	
	/**
	 * 所有的验证码都采用该方法
	 * @param vcodeLen
	 * @param height
	 * @param width
	 * @param cpachaType: 用来区别验证码的类型，传入字符串
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/get_cpacha", method = RequestMethod.GET)
	public void generateCpacha(
			@RequestParam(name = "vl",required = false, defaultValue = "4") Integer vcodeLen,
			@RequestParam(name = "w", required = false, defaultValue = "100") Integer width,
			@RequestParam(name = "h", required = false, defaultValue = "30") Integer height,
			@RequestParam(name = "type", required = true, defaultValue = "loginCpacha") String cpachaType,
			HttpServletRequest request, 
			HttpServletResponse response) {
		CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen, width, height);
		String generatorVCode = cpachaUtil.generatorVCode(); // 生成 4 位验证码
		request.getSession().setAttribute(cpachaType, generatorVCode); // 将验证码存入 session
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream()); // 将图片通过Response二进制流发送给前端（类似返回一张.jpg文件）
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
