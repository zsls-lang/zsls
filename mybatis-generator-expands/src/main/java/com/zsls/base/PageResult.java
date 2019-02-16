//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zsls.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
	private static final long serialVersionUID = -4087907067478468052L;
	private Integer pageNum;
	private Integer pageSize;
	private Long totalPage = 0L;
	private Long totalRows;
	private List<T> data;

	public PageResult() {
	}

	public PageResult(List<T> data, Long queryTotal, int pageNum, int pageSize) {
		this.data = data;
		this.totalRows = queryTotal;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.getTotalPage();
	}

	public Long getTotalPage() {
		if (this.getPageSize() != null) {
			return this.getPageSize() == 0 ? 0L : (this.getTotalRows() + (long)this.getPageSize() - 1L) / (long)this.getPageSize();
		} else {
			return this.totalPage;
		}
	}

	public Integer getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalRows() {
		return this.totalRows;
	}

	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}

	public List<T> getData() {
		return this.data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
