package com.elusiyu.pdca

import com.elusiyu.pdca.enums.UserState

class User {

    static constraints = {
    }

    String email
    String password
    UserState state

    Date dateCreated;
    Date lastUpdated;
}
