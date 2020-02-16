package com.elusiyu.pdca

import com.elusiyu.response.MsgResp
import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils
import pdca.server.SessionTracker


class PDCAController extends BaseController{

    def index() {
        render "ok"
    }

    @Transactional
    def update(){

        def pdcaDate = params.pdcaDate?params.pdcaDate:"2020-02-12"

        User user = SessionTracker.getSeesionUser(session);
        PDCA pdca = PDCA.findByU_idAndPdcaDate(user.id,pdcaDate)
        if(!pdca) {
            pdca = new PDCA();
            pdca.u_id = user.id
            pdca.pdcaDate = pdcaDate
        }

        pdca.properties = params
        pdca.save(flash:true);
        render new Resp(10002,pdca) as JSON;

    }

    @Transactional
    def save(){

    }

    @Transactional
    def show(){



        def pdcaDate = params.pdcaDate?params.pdcaDate:DateFormatUtils.format(new Date(),'yyyy-MM-dd')
        //log.info("show方法中的会话是:"+session.getId())
        User user = SessionTracker.getSeesionUser(session);
        PDCA pdca = PDCA.findByU_idAndPdcaDate(user.id,pdcaDate)

        //log.info("用户:" + user.id + " 在 【"+pdcaDate+"】的记录是:" + pdca )

        if(!pdca){
            pdca = new PDCA();
            pdca.u_id = user.id
            pdca.pdcaDate = pdcaDate
            pdca.save(flash:true)
        }

        Resp resp = new Resp(10001,pdca);
        render resp as JSON
    }
}
