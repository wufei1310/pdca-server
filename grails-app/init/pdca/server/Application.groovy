package pdca.server

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import grails.util.Environment
import groovy.transform.CompileStatic

@CompileStatic
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {

        //println Environment.current
        GrailsApp.run(Application, args)
    }
}
