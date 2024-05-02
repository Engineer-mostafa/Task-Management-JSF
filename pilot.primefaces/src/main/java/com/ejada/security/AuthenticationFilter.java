package com.ejada.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejada.bean.UserLoginBean;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

	private FilterConfig config;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		if (((HttpServletRequest) req).getSession().getAttribute(UserLoginBean.AUTH_KEY) == null) {
			((HttpServletResponse) resp).sendRedirect("index.xhtml");
			
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	@Override
	public void destroy() {
		config = null;
	}

}
