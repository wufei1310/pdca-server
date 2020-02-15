package pdca.server

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.web.context.WebApplicationContext

import javax.servlet.http.HttpSession
import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SessionTracker implements HttpSessionListener, ApplicationContextAware {

    private static final ConcurrentMap<String, HttpSession> sessions = new ConcurrentHashMap<String, HttpSession>();

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        def servletContext = ((WebApplicationContext) applicationContext).getServletContext()
        servletContext.addListener(this);
    }

    void sessionCreated(HttpSessionEvent httpSessionEvent) {
        sessions.putAt(httpSessionEvent.session.id, httpSessionEvent.session)
    }

    void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        sessions.remove(httpSessionEvent.session.id)
    }

    HttpSession getSessionById(String id) {
        sessions.get(id)
    }


}
