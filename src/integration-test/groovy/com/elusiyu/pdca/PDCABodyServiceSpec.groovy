package com.elusiyu.pdca

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PDCABodyServiceSpec extends Specification {

    PDCABodyService PDCABodyService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PDCABody(...).save(flush: true, failOnError: true)
        //new PDCABody(...).save(flush: true, failOnError: true)
        //PDCABody PDCABody = new PDCABody(...).save(flush: true, failOnError: true)
        //new PDCABody(...).save(flush: true, failOnError: true)
        //new PDCABody(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //PDCABody.id
    }

    void "test get"() {
        setupData()

        expect:
        PDCABodyService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PDCABody> PDCABodyList = PDCABodyService.list(max: 2, offset: 2)

        then:
        PDCABodyList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        PDCABodyService.count() == 5
    }

    void "test delete"() {
        Long PDCABodyId = setupData()

        expect:
        PDCABodyService.count() == 5

        when:
        PDCABodyService.delete(PDCABodyId)
        sessionFactory.currentSession.flush()

        then:
        PDCABodyService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PDCABody PDCABody = new PDCABody()
        PDCABodyService.save(PDCABody)

        then:
        PDCABody.id != null
    }
}
