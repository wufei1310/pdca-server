package com.elusiyu.pdca

import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils


class Demo2Controller {



    def isok(){
        render "this is ok"
    }

    @Transactional
    def initRecords(){

    }



}
