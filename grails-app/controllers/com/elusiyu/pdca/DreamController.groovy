package com.elusiyu.pdca

import com.elusiyu.response.MsgResp
import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.time.DateUtils

class DreamController {

    def sessionTracker

    def index() { }

    @Transactional
    def save(){
        User user = sessionTracker.getSeesionUser(request);



        Dream dream = new Dream();
        dream.u_id = user.id
        dream.no = 1
        dream.properties = params;

        List lastDream = Dream.list(max: 1, offset: 0, sort: "no", order: "desc")

        if(lastDream.size()>0){
            dream.no = lastDream.get(0).no + 1
        }


        dream.save(flash:true);


        Resp resp = new Resp(10008,dream)
        render resp as JSON
    }

    def show(){
        User user = sessionTracker.getSeesionUser(request);

        List dreamList = Dream.findAllByU_id(user.id)


        Resp resp = new Resp(10009,dreamList)
        render resp as JSON
    }
}
