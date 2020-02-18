package pdca.server



class CorsInterceptor {

    // 注入监听对象
    def sessionTracker

    CorsInterceptor() {
        matchAll()
    }

    boolean before() {
        log.info("当前的请求路径是【" + controllerName + "/" + actionName + "】参数【"+params+"】")
//        log.info("当前请求的默认会话是【"+request.getSession().getId()+"】")
//        log.info("当前所有会话【"+sessionTracker.getSessions().toString()+"】")


        //header( "Access-Control-Allow-Credentials", "true" )
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
