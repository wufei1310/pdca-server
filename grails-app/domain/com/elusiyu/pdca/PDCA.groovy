package com.elusiyu.pdca

class PDCA {

    static constraints = {

        p nullable: true;
        d nullable: true;
        c nullable: true;
        a nullable: true;


    }

    String u_id;
    String p;
    String d;
    String c;
    String a;
    String pdcaDate;//yyyy-MM-dd
    Date dateCreated;
    Date lastUpdated;
}
