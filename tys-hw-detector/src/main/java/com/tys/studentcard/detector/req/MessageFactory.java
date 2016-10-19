package com.tys.studentcard.detector.req;

import java.util.HashMap;
import java.util.Map;


public class MessageFactory {

    public final static Map<String, MessageReq> massageFactory = new HashMap<String, MessageReq>();

    static {
        massageFactory.put("05", new TicketMessageReq());
        massageFactory.put("10", new AuthenticateReq());
        massageFactory.put("09", new LeaveSchoolReq());
        massageFactory.put("82", new QueryStatusReq());
        massageFactory.put("11", new AlarmReq());
    }

    public static MessageReq buildMessage(String type) throws CloneNotSupportedException {
        return (MessageReq) massageFactory.get(type).clone();
    }
}
