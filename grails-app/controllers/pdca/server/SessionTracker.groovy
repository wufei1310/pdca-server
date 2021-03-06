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

class SessionTracker implements HttpSessionListener {

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

    public HttpSession getSession(HttpServletRequest request){
        String token = request.getHeader("PDCA-TOKEN")


        if(!token){//如果Token没有值则表明不没有登录获得
            token = request.getSession().id;
            putSession(request);
        }else{
            HttpSession session = getSessionById(token)
            if(!session){//如果有token但还是找不到会话，说明服务端的会话已经消失，客户端有遗留的token，此处需要重置
                token = request.getSession().id;
                putSession(request);
            }
        }
        return getSessionById(token);
    }

    public putSession(HttpServletRequest request){
        sessions.putAt(request.getSession().id,request.getSession())
    }

    public ConcurrentMap<String, HttpSession> getSessions(){
        return sessions
    }

    public setLoginSessionUser(HttpServletRequest request, User user){
        request.getSession(true).session_user = user
    }

    public User getSessionUser(HttpServletRequest request){
        return getSession(request).session_user
    }

    public User getSessionUser(HttpSession session){
        return session.session_user
    }


}
