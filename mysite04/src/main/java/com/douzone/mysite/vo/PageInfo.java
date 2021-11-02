package com.douzone.mysite.vo;

public class PageInfo {
	private int curPage;
	
	private int totalPage;
	private int begin;
	private int end;
	private int nextPage;
	private int prevPage;
	
	private int cntPerPage;
	private int pageRange;
	
	private int offset;
	
	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}


	public PageInfo() {
		super();
		this.cntPerPage = 5;
		this.pageRange = 5;
		
	}
	
	
	public int getPageRange() {
		return pageRange;
	}
	public void setPageRange(int pageRange) {
		this.pageRange = pageRange;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getCntPerPage() {
		return cntPerPage;
	}
	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}


	@Override
	public String toString() {
		return "PageInfo [curPage=" + curPage + ", totalPage=" + totalPage + ", begin=" + begin + ", end=" + end
				+ ", nextPage=" + nextPage + ", prevPage=" + prevPage + ", cntPerPage=" + cntPerPage + ", pageRange="
				+ pageRange + "]";
	}
	
	
}
