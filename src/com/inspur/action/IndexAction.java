package com.inspur.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.inspur.dao.CategoryDao;
import com.inspur.model.Category;
import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport  implements ServletRequestAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
    CategoryDao categorydao=new CategoryDao();
	 
	
	
	
	@Override
	public String execute() throws Exception {
		 List<Category> list=categorydao.listCategory();
		 request.setAttribute("list",list);
		 return "success";
	}
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
	
}
