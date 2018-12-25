package com.inspur.util;

import java.util.List;

/**
 * ��ҳBean
 * ��װ�������ҳ��ص�����
 * @author qdmmy6
 *
 */
public class Page<T> {
	private int pc;//��ǰҳ��
	private int tr;//�ܼ�¼��
	private int ps;//ÿҳ��¼��
	private List<T> dataList;//��ǰҳ��¼
	private String url;//�����url
	
	/**
	 * ������ҳ��
	 * @return
	 */
	public int getTp() {
		int tp = tr / ps;//�ܼ�¼��/ÿҳ��¼��
		return tr % ps == 0 ? tp : tp+1;//���������������tp�������ټ�1��
	}
	
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
