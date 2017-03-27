package com.emxcel.dms.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author ADMIN.
 */
public class SessionListener implements HttpSessionListener {

	private static int totalActiveSessions;

	/**
	 * @return int
	 */
	public static int getTotalActiveSession() {
		return totalActiveSessions;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		totalActiveSessions++;
		event.getSession().setMaxInactiveInterval(15 * 60);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		totalActiveSessions--;
	}
}