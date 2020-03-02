package com.elusiyu.pdca

import com.elusiyu.response.Resp
import grails.converters.JSON
import grails.gorm.transactions.Transactional

class DreamController {

    def sessionTracker

    def index() { }

    @Transactional
    def save(){
        User user = sessionTracker.getSessionUser(request);



        Dream dream = new Dream();
        dream.properties = params;
        dream.u_id = user.id
        dream.dream_no = 1



        List lastDream = Dream.findAllByU_id(user.id,[max: 1, offset: 0, sort: "dream_no", order: "desc"])

        if(lastDream.size()>0){
            dream.dream_no = lastDream.get(0).dream_no + 1
        }




        dream.save(flash:true);


        Resp resp = new Resp(10008,dream)
        render resp as JSON
    }

    def show(){
        User user = sessionTracker.getSessionUser(request);

        List dreamList = Dream.findAllByU_id(user.id)


        Resp resp = new Resp(10009,dreamList)
        render resp as JSON
    }
}
