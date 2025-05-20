package com.cecilia.programmer.interceptor.home;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
/**
 * 前端登录拦截器
 * @author cecilia
 */
public class LoginInterceptor implements HandlerInterceptor {

	// 在拦截之后做
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	// 在拦截的时候做
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	// 在请求之前做
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		Object student = request.getSession().getAttribute("student");
		if (student == null) {
			// 表示未登录或者登录失效，重定向到登录界面：response.sendRedirect()
			System.out.println("链接" + requestURI + "进入拦截器");
			String header = response.getHeader("X-Requested-With");
			// 判斷是否是 Ajax 请求
			if ("XMLHttpRequest".equals(header)) {
				// 表示是 Ajax 请求
				Map<String, String> ret = new HashMap<String, String>();
				ret.put("type", "error");
				ret.put("msg", "登录会话超时或还未登录，请重新登录");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			}
			// 表示是普通连接跳转，直接重定向到登录界面
			response.sendRedirect(request.getServletContext().getContextPath() +"/home/login");
			return false;
		}
		return true;
	}

}
