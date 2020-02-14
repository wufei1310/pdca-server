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
        msgMap.put(10001,"获取一条PDCA记录成功");
        msgMap.put(10002,"修改一条PDCA成功");
    }



}
