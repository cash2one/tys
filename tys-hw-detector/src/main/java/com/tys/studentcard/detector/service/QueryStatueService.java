package com.tys.studentcard.detector.service;

import com.tys.studentcard.detector.DetectorChannelHolder;
import com.tys.studentcard.detector.req.QueryStatusReq;
import com.tys.studentcard.detector.res.QueryStatusRes;

public class QueryStatueService {


    public QueryStatusReq queryStatusReq(String terminalNo) {
        QueryStatusRes queryStatusRes = new QueryStatusRes();
        queryStatusRes.setSequence((int) Math.random() * 9999);
        return (QueryStatusReq) DetectorChannelHolder.sendMesage(terminalNo, queryStatusRes);
    }

}
