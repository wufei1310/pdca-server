package com.elusiyu.pdca

import com.elusiyu.response.MsgResp
import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.time.DateFormatUtils


class PDCAController extends BaseController{

    def sessionTracker;

    def index() {
        render "ok"
    }

    @Transactional
    def update(){

        def pdcaDate = params.pdcaDate?params.pdcaDate:"2020-02-12"

        User user = sessionTracker.getSeesionUser(request);
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
        log.info("show方法中的会话是:"+session.getId())
        User user = sessionTracker.getSeesionUser(request);
        PDCA pdca = PDCA.findByU_idAndPdcaDate(user.id,pdcaDate)


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
