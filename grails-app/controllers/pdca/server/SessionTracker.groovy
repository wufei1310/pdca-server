package pdca.server

import com.elusiyu.pdca.User
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

        println("一个新会话产生了:" + httpSessionEvent.session.id)
        sessions.putAt(httpSessionEvent.session.id, httpSessionEvent.session)
    }

    void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        println("一个会话消失了:" + httpSessionEvent.session.id)
        sessions.remove(httpSessionEvent.session.id)
    }

    HttpSession getSessionById(String id) {
        sessions.get(id)
    }

    public static HttpSession getSeesion(HttpSession session){
        return sessions.get(session.id)
    }

    public static  putSession(HttpSession session){
        sessions.putAt(session.id,session)
    }

    public static ConcurrentMap<String, HttpSession> getSessions(){
        return sessions
    }

    public static setSeesionUser(HttpSession session, User user){
        session.session_user = user
    }

    public static User getSeesionUser(HttpSession session){
        return session.session_user
    }


}
