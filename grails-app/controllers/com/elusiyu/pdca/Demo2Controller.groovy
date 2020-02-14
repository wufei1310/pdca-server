package com.elusiyu.pdca

import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils


class Demo2Controller {




    @Transactional
    def initRecords(){
        Date startPdcaDate = new Date();
        for(int i = -100 ;i < 300;i++){
            PDCA initPdca = new PDCA();
            initPdca.pdcaDate = DateFormatUtils.format(DateUtils.addDays(startPdcaDate,i),'yyyy-MM-dd')
            initPdca.u_id = 1
            initPdca.save()
            println("======= " + initPdca)
        }
    }



}
