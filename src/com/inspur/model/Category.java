package com.inspur.model;

import java.util.List;

public class Category {
	private int cid;
	private String cname;
	private List<Category> children;//所有子分类
	private Category parent;//父分类
	private String image;
	private String descs;
	private int pid;

	
	public Category() {
		
	  }
	/*
	 *生成一级分类的构造方法 
	 */
	public Category(String cname, String image, String descs) {
		this.cname = cname;
		this.image = image;
		this.descs = descs;
	}
    
	public Category(String cname, String descs, int pid) {
		this.cname = cname;
		this.descs = descs;
		this.pid = pid;
	}
	public Category(int cid, String cname, String image, String desc) {
		this.cid = cid;
		this.cname = cname;
		this.image = image;
		this.descs = descs;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
