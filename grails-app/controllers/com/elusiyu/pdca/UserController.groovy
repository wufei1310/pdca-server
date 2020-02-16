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
        user.password = params.password.encodeAsBase64().encodeAsBase64();
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

        //User user = User.findByEmailAndPasswordAndState(params.email,params.password, UserState.ACTIVATE)
        User user = User.findByEmail(params.email)
        Resp resp = new Resp();
        if(user){

            if(user.state == UserState.FREEZE){
                user.password = "" //将密码置空，不回传到前端
                resp.setFailCode(50002)
            }

            else if(user.state == UserState.NEWREGISTRATION){
                user.password = "" //将密码置空，不回传到前端
                resp.setFailCode(50003)
            }

            else if(user.password != params.password.encodeAsBase64().encodeAsBase64()){
                user.password = "" //将密码置空，不回传到前端
                resp.setFailCode(50004)
            }

            else if(user.password == params.password.encodeAsBase64().encodeAsBase64()){
                user.password = "" //将密码置空，不回传到前端
                SessionTracker.setSeesionUser(session,user)
                log.info("当前请求的用户是:"+SessionTracker.getSeesionUser(session).id)
                resp.setCode(10003)
                resp.getExtrainfo().put(Tokens.MEMBER_TOKEN,session.getId());



            }
        }else {
            resp.setFailCode(50000)

        }

        resp.setBody(user)
        render resp as JSON




    }
}
