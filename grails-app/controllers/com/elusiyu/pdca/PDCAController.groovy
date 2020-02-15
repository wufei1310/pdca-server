package com.elusiyu.pdca

import com.elusiyu.response.MsgResp
import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils


class PDCAController {

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

        PDCA pdca = PDCA.findByPdcaDate(pdcaDate)

        Resp resp = new Resp(10001,pdca);
        //log.info(params)
        log.info(resp.toString())

        render resp as JSON
    }
}
