package com.replad.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter{
	private ArrayList<String> urls;
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getServletPath();
        boolean allowedRequest = false;
         
        if(urls.contains(url)) {
            allowedRequest = true;
        }
        if (!allowedRequest) {
            HttpSession session = request.getSession(false);
            if (null == session) {
                response.sendRedirect("index.jsp");
            }
        }
        chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String urlList = filterConfig.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urlList, ",");
        urls = new ArrayList<String>();
        while (token.hasMoreTokens()) {
        	urls.add(token.nextToken());
        }
	}
}
