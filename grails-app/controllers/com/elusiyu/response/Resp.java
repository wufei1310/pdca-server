package com.elusiyu.response;

public class Resp {



    Integer code;
    String subcode;
    String msg;
    Object body;

    public Resp(Integer code, Object body){
        this.setCode(code);
        this.setBody(body);
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
