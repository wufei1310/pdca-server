package com.elusiyu.response;

import java.util.HashMap;
import java.util.Map;

public class MsgResp {

    private static MsgResp msgResp = new MsgResp();
    private static Map<Integer,String> msgMap;

    public static MsgResp getMsgResp(){
        return msgResp;
    }

    private MsgResp(){
        msgMap = new HashMap<Integer, String>();
        initMsg(msgMap);
    }

    private Map<Integer, String> getMsgMap() {
        return msgMap;
    }

    public static String getMsg(Integer code){
        return MsgResp.getMsgResp().getMsgMap().get(code);
    }


    private void initMsg(Map<Integer,String> msgMap){

        //10000~40000 正常的业务请求-成功
        msgMap.put(10001,"获取一条PDCA记录成功");
        msgMap.put(10002,"修改一条PDCA成功");
        msgMap.put(10003,"登录成功");

        msgMap.put(10004,"注册成功");

        //50000~90000 正常的业务请求-失败
        msgMap.put(50000,"登录失败");
        msgMap.put(50001,"邮箱已经存在");



        //40000~50000 非法的请求
        msgMap.put(40000,"非法请求");
        msgMap.put(40001,"会话已失效，非法请求");

    }



}
