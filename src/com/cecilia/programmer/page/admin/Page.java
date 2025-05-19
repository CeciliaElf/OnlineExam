package com.cecilia.programmer.page.admin;

import org.springframework.stereotype.Component;

/**
 * 分页基本讯息
 * @author cecilia
 */
@Component
public class Page {
	private int page = 1;   // 当前页码
	private int rows;        // 每页显示数量
	private int offset;     // 偏移量，对应数据库中的偏移量
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int row) {
		this.rows = row;
	}
	public int getOffset() {
		this.offset = (page - 1) * rows;  // 每页显示多少条
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
