package com.elusiyu.pdca

import com.elusiyu.response.MsgResp
import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils


class PDCAController extends BaseController{

    def index() {
        render "ok"
    }

    @Transactional
    def update(){

        def pdcaDate = params.pdcaDate?params.pdcaDate:"2020-02-12"
        PDCA pdca = PDCA.findByPdcaDate(pdcaDate)
        pdca.properties = params
        pdca.save();

        log.info(params.toString())
        render new Resp(10002,pdca) as JSON;

    }

    @Transactional
    def save(){

    }

    @Transactional
    def show(){



        def pdcaDate = params.pdcaDate?params.pdcaDate:DateFormatUtils.format(new Date(),'yyyy-MM-dd')
        log.info("show方法中的会话是:"+session.getId())
        PDCA pdca = PDCA.findByU_idAndPdcaDate(session.session_user.id,pdcaDate)



        if(!pdca){
            pdca = new PDCA();
            pdca.u_id = session.session_user.id
            pdca.save(flash:true)
        }

        Resp resp = new Resp(10001,pdca);
        //log.info(params)
        log.info(resp.toString())

        render resp as JSON
    }
}
