package com.elusiyu.pdca

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

        println "====="
        println params

        def pdcaDate = params.pdcaDate?params.pdcaDate:"2020-02-12"
        PDCA pdca = PDCA.findByPdcaDate(pdcaDate)
        pdca.properties = params
        pdca.save();
        render pdca as JSON

    }

    @Transactional
    def save(){

    }

    @Transactional
    def show(){

        def pdcaDate = params.pdcaDate?params.pdcaDate:DateFormatUtils.format(new Date(),'yyyy-MM-dd')

        PDCA pdca = PDCA.findByPdcaDate(pdcaDate)

        render pdca as JSON
    }
}
