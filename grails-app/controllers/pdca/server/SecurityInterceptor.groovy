package pdca.server

import com.elusiyu.pdca.User

import javax.servlet.http.Cookie
import javax.servlet.http.HttpSession


class SecurityInterceptor {

    // 注入监听对象
    def sessionTracker

    SecurityInterceptor() {
        matchAll()
                .except(controller:'demo2', action:'isok')
                .except(controller:'user', action:'doLogin')
                .except(controller:'user', action:'doLogout')
                .except(controller:'user', action:'doRegister')
                .except(controller:'user', action:'illegalReq')
                .except(controller:'user', action:'invalidUserReq')
    }

    boolean before() {



        String token = request.getHeader("PDCA-Token")
        Cookie[] cookies = request.getCookies(); //vue前端如果是直接刷新浏览器，request的Header中将没有PDCA-Token，这时候就要从cookie中去取
        cookies.each {it->
            if(it.name == "PDCA-Token"){
                token = it.value;
            }
        }
        log.info("当前请求的Token是【"+token+"】")

        if(token){
            HttpSession session = sessionTracker.getSessionById(token);

            if(session){
                log.info("根据当前Token【" + token + "】获得的会话是:【"+session.getId()+"】")
                User user = session.session_user
                if(user){
                    log.info("当前请求的用户是【"+user.toString()+"】")
                    return true
                }else{
                    forward(controller: "user",action: "invalidUserReq")
                    return false
                }
            }else{
                log.info("根据当前Token:【" + token + "】没有获得的会话")
                forward(controller: "user",action: "illegalReq")
                return false
            }



        }else{
            forward(controller: "user",action: "illegalReq")
            return false
        }

        return true
    }

    boolean after() {
        true
    }

    void afterView() {
        // no-op
    }
}
