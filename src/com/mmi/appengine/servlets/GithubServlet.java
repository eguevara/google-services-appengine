package com.mmi.appengine.servlets;

import java.io.IOException;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.mmi.appengine.utils.ServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GithubServlet extends AbstractAppEngineAuthorizationCodeServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		String userId = getUserId(req);
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world " + userId);
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws IOException {
		return ServiceUtils.newFlow();
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req) {
		return ServiceUtils.getRedirectUri(req);
	}
}
