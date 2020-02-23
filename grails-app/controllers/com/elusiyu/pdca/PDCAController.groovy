package com.elusiyu.pdca

import com.elusiyu.response.MsgResp
import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils


class PDCAController extends BaseController{

    def sessionTracker;

    def index() {
        render "ok"
    }

    //追加PLAN到明天
    @Transactional
    def addToTomorrow(){
        Date tomorrow = DateUtils.addDays(new Date(),1);
        String now_str = DateFormatUtils.format(new Date(),'MM-dd hh:mm:ss')
        String tomorrow_str = DateFormatUtils.format(tomorrow,'yyyy-MM-dd')

        User user = sessionTracker.getSeesionUser(request);
        PDCA pdca = PDCA.findByU_idAndPdcaDate(user.id,tomorrow_str)
        if(!pdca) {
            pdca = new PDCA();
            pdca.u_id = user.id
            pdca.pdcaDate = pdcaDate
        }

        if(pdca.p){
            pdca.p = pdca.p + "\r\n" + "【"+ now_str +"】"  + params.p
        }else{
            pdca.p = "【"+ now_str +"】"  + params.p
        }



        pdca.save(flash:true);
        render new Resp(10010,pdca) as JSON;

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
//        log.info("show方法中的会话是:"+session.getId())
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


    def findMyRecords(){
        User user = sessionTracker.getSeesionUser(request);
        Date pdcaDate = DateUtils.parseDate(params.pdcaDate,'yyy-MM-dd')
        Date minDate = DateUtils.addDays(pdcaDate,-30)
        Date maxDate = DateUtils.addDays(pdcaDate,30)


        String minDateStr = DateFormatUtils.format(minDate,'yyyy-MM-dd')
        String maxDateStr = DateFormatUtils.format(maxDate,'yyyy-MM-dd')

        List<PDCA> pdcaList = PDCA.findAllByU_idAndPdcaDateBetween(user.id,minDateStr,maxDateStr);
        List<String> pdcaDateList = new ArrayList<String>();
        pdcaList.each {pdca ->
            if(pdca.p){
                pdcaDateList.add(pdca.pdcaDate)
            }
        }

        Resp resp = new Resp(10007,pdcaDateList);
        render resp as JSON


    }
}
