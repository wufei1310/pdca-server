package com.elusiyu.pdca

import com.elusiyu.pdca.enums.Tokens
import com.elusiyu.pdca.enums.UserState
import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.util.Environment
import me.zhyd.oauth.config.AuthConfig
import me.zhyd.oauth.model.AuthCallback
import me.zhyd.oauth.model.AuthResponse
import me.zhyd.oauth.request.AuthRequest
import me.zhyd.oauth.request.AuthWeChatEnterpriseRequest
import me.zhyd.oauth.utils.AuthStateUtils
import pdca.server.SessionTracker

import javax.servlet.http.HttpSession

class AuthLoginController {

    // 注入监听对象
    def sessionTracker

    def index() { }


    private AuthRequest getAuthWeChatEnterpriseRequest() {
        String redirectUri = ""
        if(Environment.current==Environment.DEVELOPMENT){
            redirectUri = grailsApplication.config.getProperty('myConfig.PDCASERVERRedirectURL_DEV')
        }

        if(Environment.current==Environment.PRODUCTION){
            redirectUri = grailsApplication.config.getProperty('myConfig.PDCASERVERRedirectURL_PRO')
        }


        return new AuthWeChatEnterpriseRequest(AuthConfig.builder()
                .agentId("1000059")
                .clientId("ww945bf9288db4810d")
                .clientSecret("WSz4AAl8BDA3x7vGe3gTVC2boe7iVH8rXaofFvFkw4c")
                .redirectUri(redirectUri)
                .build());
    }

    def renderAuth(){
        AuthRequest authRequest = getAuthWeChatEnterpriseRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }


    def verifyToken(){


        HttpSession session = sessionTracker.getSessionById(params.token);
        User user = sessionTracker.getSessionUser(session);

        Resp resp = new Resp();
        if(user){
            resp.setCode(10003);
            resp.setBody(user);
        }else {
            resp.setCode(40002);
        }



        render resp as JSON
    }

    def authLoginSuccess(AuthCallback callback){

        AuthRequest authRequest = getAuthWeChatEnterpriseRequest();
        AuthResponse authResponse = authRequest.login(callback)

        log.info("第三方登录成功后回调")
        String source = authResponse.getData().getAt("source")
        String email = authResponse.getData().getAt("email")
        User user = User.findByEmailAndSource(email,source)

        if(!user) {
            user = new User();
            user.email = email;
            user.password = "password".encodeAsBase64().encodeAsBase64();
            user.state = UserState.ACTIVATE;
            user.source = source;
            user.save(flash: true)
        }

        if(user.state == UserState.ACTIVATE){
            sessionTracker.setLoginSessionUser(request,user)
            log.info("第三方登录用户是:"+sessionTracker.getSessionUser(request).id)
        }

        Resp resp = new Resp();
        resp.setCode(10006)
        resp.getExtrainfo().put(Tokens.PDCA_TOKEN,session.getId());

        //response.setHeader(Tokens.PDCA_TOKEN.toString(),session.getId())
        if(Environment.current==Environment.DEVELOPMENT){
            response.sendRedirect(grailsApplication.config.getProperty('myConfig.PDCARedirectURL_DEV')+"?t="+session.getId())
        }

        if(Environment.current==Environment.PRODUCTION){
            response.sendRedirect(grailsApplication.config.getProperty('myConfig.PDCARedirectURL_PRO')+"?t="+session.getId())
        }




        //render email + ":" + source

    }
}
