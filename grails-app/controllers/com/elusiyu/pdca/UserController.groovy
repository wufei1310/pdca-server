package com.elusiyu.pdca

import com.elusiyu.pdca.enums.Tokens
import com.elusiyu.pdca.enums.UserState
import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import pdca.server.SessionTracker

class UserController extends BaseController{

    def index() { }

    def illegalReq(){
        render new Resp(40000) as JSON;
    }

    def invalidUserReq(){
        render new Resp(40001) as JSON;
    }


    @Transactional
    def doRegister(){
        User user = User.findByEmail(params.email)
        Resp resp = new Resp();
        if(user){
            resp.setFailCode(50001)
            render resp as JSON
        }
        user = new User();
        user.email = params.email;
        user.password = params.password;
        user.state = UserState.ACTIVATE;
        user.save(flash:true)


        resp.setCode(10004);
        resp.setBody(user);

        render resp as JSON


    }

    def doLogout(){

        SessionTracker.getSeesion(session).invalidate();
        render new Resp(10005) as JSON

    }

    def doLogin(){

        User user = User.findByEmailAndPasswordAndState(params.email,params.password, UserState.ACTIVATE)

        Resp resp = new Resp();
        if(user){



            SessionTracker.setSeesionUser(session,user)

            log.info("当前请求的用户是:"+SessionTracker.getSeesionUser(session).id)

            resp.setCode(10003)
            resp.setBody(user)
            resp.getExtrainfo().put(Tokens.MEMBER_TOKEN,session.getId());
        }else {
            resp.setFailCode(50000)
            resp.setBody(user)
        }

        render resp as JSON




    }
}
