package com.example.myutil;

public class Page {

	private int totalPage;// һ���ж���ҳ
	private int everPageNum;// ÿһҳ���м�������
	private int totalNum;// �����ݸ���
	private boolean hasNext;// �Ƿ�����һҳ
	private boolean hasFront;// �Ƿ�����һҳ
	private int currentPage;// ��ǰҳ��
	private int indexPage;// ÿһҳ��ʼ��λ��

	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Page(int totalPage, int everPageNum, int totalNum, boolean hasNext,
			boolean hasFront, int currentPage, int indexPage) {
		super();
		this.totalPage = totalPage;
		this.everPageNum = everPageNum;
		this.totalNum = totalNum;
		this.hasNext = hasNext;
		this.hasFront = hasFront;
		this.currentPage = currentPage;
		this.indexPage = indexPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getEverPageNum() {
		return everPageNum;
	}

	public void setEverPageNum(int everPageNum) {
		this.everPageNum = everPageNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasFront() {
		return hasFront;
	}

	public void setHasFront(boolean hasFront) {
		this.hasFront = hasFront;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	
}
