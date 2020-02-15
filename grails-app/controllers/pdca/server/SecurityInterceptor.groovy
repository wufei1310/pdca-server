package pdca.server

import com.elusiyu.pdca.User

import javax.servlet.http.HttpSession


class SecurityInterceptor {

    // 注入监听对象
    def sessionTracker

    SecurityInterceptor() {
        matchAll()
                .except(controller:'user', action:'doLogin')
                .except(controller:'user', action:'doRegister')
                .except(controller:'user', action:'illegalReq')
                .except(controller:'user', action:'invalidUserReq')
    }

    boolean before() {
        String token = request.getHeader("PDCA-Token")

        log.info("当前请求的默认会话是:"+request.getSession().getId())
        log.info("当前请求的Token是:"+token)

        if(token){
            HttpSession session = sessionTracker.getSessionById(token);
            log.info("当前请求的会话是:"+session.getId())
            //log.info(session.toString())
            if(session){
                User user = session.session_user
                if(user){
                    log.info("当前请求的用户是:"+user.toString())
                    return true
                }else{
                    redirect(controller: "user",action: "invalidUserReq")
                    return false
                }
            }else{
                redirect(controller: "user",action: "illegalReq")
                return false
            }



        }else{
            redirect(controller: "user",action: "illegalReq")
            return false
        }

        return true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
