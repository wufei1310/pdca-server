package com.elusiyu.pdca

import com.elusiyu.pdca.enums.UserState

class User {

    static constraints = {
        source nullable: true;
    }

    String email
    String password
    UserState state

    String source;//第三方来源

    Date dateCreated;
    Date lastUpdated;
}
