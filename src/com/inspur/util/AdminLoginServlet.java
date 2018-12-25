package com.inspur.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.inspur.model.Admin;

public class AdminLoginServlet implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		Admin admin = (Admin)httpReq.getSession().getAttribute("Curentadmin");
		if(admin == null) {
			request.setAttribute("msg", "源码由阿莫之家社区提供！");
			httpReq.getRequestDispatcher("/admin/login.jsp").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}
}
