package com.cecilia.programmer.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cecilia.programmer.entity.admin.Log;

/**
 * 日志 Service 接口
 * @author cecilia
 */
@Service
public interface LogService {
	public int add(Log log);
	public int add(String content);
	public List<Log> findList(Map<String, Object> queryMap);
	public int getTotal(Map<String, Object> queryMap);
	public int delete(String ids);
}
