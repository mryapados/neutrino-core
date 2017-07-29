package org.neutrinocms.core.conf;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
 
public class SessionListener implements HttpSessionListener {
	private Logger logger = Logger.getLogger(SessionListener.class);
	
    @Override
    public void sessionCreated(HttpSessionEvent event) {
    	logger.debug("==== Session is created ====");
        event.getSession().setMaxInactiveInterval(15*60);
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	logger.debug("==== Session is created ====");
    }
}