package com.fanwe.hybrid.model;


@SuppressWarnings("serial")
public class BaseActListModel extends BaseActModel{
	private int has_next; // 是否还有下一页
	private int page; // 当前页
	
	public int getHas_next() {
		return has_next;
	}
	public void setHas_next(int has_next) {
		this.has_next = has_next;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	

	public boolean hasNext() {
		return this.has_next == 1;
	}
}
