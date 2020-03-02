package pdca.server

import com.elusiyu.pdca.User
import com.elusiyu.pdca.enums.Tokens

import javax.servlet.http.Cookie
import javax.servlet.http.HttpSession


class SecurityInterceptor {

    // 注入监听对象
    def sessionTracker

    SecurityInterceptor() {
        matchAll()
                .except(controller:'demo2', action:'isok')
                .except(controller:'authLogin', action:'*')

                .except(controller:'user', action:'doLogin')
                .except(controller:'user', action:'doLogout')
                .except(controller:'user', action:'doRegister')
                .except(controller:'user', action:'illegalReq')
                .except(controller:'user', action:'invalidUserReq')
    }

    boolean before() {

        String token = request.getHeader("PDCA-TOKEN")
        //log.info("当前请求Header获得的的Token是【"+token+"】")
        Cookie[] cookies = request.getCookies(); //vue前端如果是直接刷新浏览器，request的Header中将没有PDCA-Token，这时候就要从cookie中去取


        if(token){
            cookies.each {it->
                if(it.name=="PDCA-TOKEN"){
                    token = it.value;
                    //log.info("Header中没有，从cookie中获的Token是【"+token+"】")
                }
            }
        }



        if(token){
            HttpSession session = sessionTracker.getSessionById(token);

            if(session){
//                log.info("根据当前Token【" + token + "】获得的会话是:【"+session.getId()+"】")
                User user = sessionTracker.getSessionUser(session)


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
