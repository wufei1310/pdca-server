package com.elusiyu.response;

import org.grails.datastore.mapping.query.Query;

import java.util.HashMap;
import java.util.Map;

public class Resp {



    Integer code;
    String subcode;
    String msg;
    Object body;
    Map extrainfo;

    public Map getExtrainfo() {
        return extrainfo;
    }

    public void setExtrainfo(Map extrainfo) {
        this.extrainfo = extrainfo;
    }



    public Resp(){
        this.extrainfo = new HashMap();
    }

    public Resp(Integer code){
        this.setCode(code);
        this.extrainfo = new HashMap();
    }

    public Resp(Integer code, Object body){
        this.setCode(code);
        this.setBody(body);
        this.extrainfo = new HashMap();
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
        this.setMsg(MsgResp.getMsg(code));
    }

    public void setErrorCode(Integer code){
        this.setCode(code);
    }

    public void setFailCode(Integer code){
        this.setCode(code);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
