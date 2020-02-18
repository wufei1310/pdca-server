package pdca.server

import com.elusiyu.pdca.User
import com.elusiyu.pdca.enums.Tokens
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.http.HttpRequest
import org.springframework.web.context.WebApplicationContext

import javax.servlet.http.HttpServletRequest
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
        return sessions.get(id)

    }

    public HttpSession getSeesion(HttpServletRequest request){

        String token = request.getHeader(Tokens.MEMBER_TOKEN.toString())
        if(!token){//如果Token没有值则表明不没有登录获得
            token = request.getSession().id;
            putSession(request);

        }
        return getSessionById(token);
    }

    public putSession(HttpServletRequest request){
        sessions.putAt(request.getSession().id,request.getSession())
    }

    public ConcurrentMap<String, HttpSession> getSessions(){
        return sessions
    }

    public setLoginSeesionUser(HttpServletRequest request, User user){
        request.getSession(true).session_user = user
    }

    public User getSeesionUser(HttpServletRequest request){
        return getSeesion(request).session_user
    }

    public User getSeesionUser(HttpSession session){
        return session.session_user
    }


}
