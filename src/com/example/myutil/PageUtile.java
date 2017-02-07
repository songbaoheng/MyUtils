package com.example.myutil;

public class PageUtile {

	/*
	 * ��ȡpage����
	 */
	public static  Page getPage(int everPageNum, int totalNum, int currentPage) {
		Page page = null;
		currentPage=GetCurrentPage(currentPage);
		everPageNum = getEverPageNum(everPageNum);
		int totalPage = getTotalPage(everPageNum, totalNum);
		boolean hasNext = hasNext(currentPage, totalPage);
		boolean hasFront = hasFront(currentPage);
		int indexPage = getBeginIndex(currentPage, everPageNum);

		return new Page(totalPage, everPageNum, totalNum, hasNext, hasFront,
				currentPage, indexPage);

	}

	/*
	 * ���ÿһҳ�������ʾ������ ��С��10��ʱ����ʾ������Ĭ��Ϊ10��������10��ʱ��ҳ�����ʾ����Ϊ��ǰ�趨��ֵ
	 */
	public static int getEverPageNum(int everPageNum) {
		return everPageNum == 0 ? 10 : everPageNum;
	}

	/*
	 * ��õ�ǰ��ҳ������Ϊ0ʱ��Ϊ1�������趨Ϊ��ǰ�Ĵ�������ֵ
	 */
	public static int GetCurrentPage(int currentPage) {
		return currentPage == 0 ? 1 : currentPage;
	}

	/*
	 * ��ȡ��ҳ����ͨ����������everPageNum��totalNum���м���
	 * ��totalNum��everPageNumȡ��Ϊ���ʱ��ҳ������Ϊ����������Ľ������֮���Ҫ����1
	 */
	public static int getTotalPage(int everPageNum, int totalNum) {

		int totalePage = 0;
		if (totalNum != 0 && totalNum % everPageNum == 0) {
			totalePage = totalNum / everPageNum;
		} else {
			totalePage = totalNum / everPageNum + 1;
		}
		return totalePage;

	}

	/*
	 * �ж��Ƿ�����һҳ
	 */
	public static boolean hasNext(int currentPage, int totalPage) {
		return currentPage == totalPage || totalPage == 0 ? false : true;

	}

	/*
	 * �ж��Ƿ�����һҳ
	 */
	public static boolean hasFront(int currentPage) {
		return currentPage == 1 ? false : true;
	}

	/*
	 * ���ÿһҳ����ʼλ��
	 */
	public static int getBeginIndex(int currentPage, int everPageNum) {
		return (currentPage - 1) * everPageNum;
	}
}
