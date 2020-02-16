package pdca.server



class CorsInterceptor {

    CorsInterceptor() {
        matchAll()
    }

    boolean before() {
        log.info("当前的请求路径是【" + controllerName + "/" + actionName + "】参数【"+params+"】")
        log.info("当前请求的默认会话是【"+request.getSession().getId()+"】")
        log.info("当前所有会话【"+SessionTracker.getSessions().toString()+"】")

        //用于开发环境：服务器端热加载后会话会清空，而客户端仍然保留之前的cookies
        if(!SessionTracker.getSessions()||!SessionTracker.getSeesion(request.getSession())){
            SessionTracker.putSession(request.getSession());
            log.info("会话清空后，及时更新当前所有会话【"+SessionTracker.getSessions().toString()+"】")
        }

        //header( "Access-Control-Allow-Credentials", "true" )
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
