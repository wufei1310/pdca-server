package com.elusiyu.pdca

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class DemoSpec extends Specification implements DomainUnitTest<Demo> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
