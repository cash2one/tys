package com.tys.netty.util;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tys.netty.dto.AddressInfo;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;

public class HttpClientUtils {
    private final static String CELL_URL = "http://112.126.65.114/iuwtech/httpReq/cellQuery.do?%s&ta=%s&flag=3";

    private final static String BAIDU_URL = "http://api.map.baidu.com/geocoder/v2/?ak=3E331673dbeb74663cfe2885edc4f24a&output=json&location=%f,%f";
    
    private final static String TRANSLATE_GPS_TO_TENCENT_URL="http://apis.map.qq.com/ws/coord/v1/translate?locations=%s&type=%d&key=Y7UBZ-J7IH5-A5UI6-Q24SC-GFKGQ-J5FVV";

    private final static String TENCENT_ACCURATE_POSITIONING_URL="http://apis.map.qq.com/ws/geocoder/v1/?location=%s&key=Y7UBZ-J7IH5-A5UI6-Q24SC-GFKGQ-J5FVV&get_poi=%d";

    public static AddressInfo fetchCell(String cel, String ta) throws IOException {
        String url = String.format(CELL_URL, cel, ta);
        AddressInfo addressInfo = new AddressInfo();
        HttpRpcService httpRpcService = HttpRpcService.getInstance();
        String responseBody = httpRpcService.httpGet(url);
        MUtil.log("responseBody:" + responseBody + "  cel:" + cel + " ta:" + ta);
        if(MStrUtil.isNull(responseBody)){
            return addressInfo;
        }
        JSONObject result = (JSONObject) JSON.parse(responseBody);
        if (NumberUtil.equals(Integer.valueOf(1), result.getInteger("result"))) {
            addressInfo.setAddress(result.getString("address"));
            addressInfo.setLng(result.getDouble("longitude"));
            addressInfo.setLat(result.getDouble("latitude"));
        }
        return addressInfo;
    }

    public static AddressInfo fetchBaiduAddress(double lat, double lng) throws IOException {
        MUtil.log("fetchBaiduAddress start = " + DateUtil.getNowTime());
        String url = String.format(BAIDU_URL, lat, lng);
        HttpRpcService httpRpcService = HttpRpcService.getInstance();
        String responseBody = httpRpcService.httpGet(url);
        if (MStrUtil.isNull(responseBody)) {
            return null;
        }

        JSONObject result = (JSONObject) JSON.parse(responseBody);
        if (result.getIntValue("status") == 0) {
            JSONObject obj = (JSONObject) result.get("result");
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setAddress(obj.getString("formatted_address"));
            JSONObject obj1 = (JSONObject) obj.get("location");
            addressInfo.setLng(obj1.getDouble("lng"));
            addressInfo.setLat(obj1.getDouble("lat"));
            return addressInfo;
        }
        return null;
    }
    
    
    public static AddressInfo gpsToTencent(Double lat, Double lng) throws IOException {
    	AddressInfo addressInfo = null ;
    	if(null == lat || null == lng){
    		return addressInfo;
    	}
    	
		String loactaions = lat + "," + lng;
        String url = String.format(TRANSLATE_GPS_TO_TENCENT_URL, loactaions, Integer.valueOf(1));
       
        HttpRpcService httpRpcService = HttpRpcService.getInstance();
        String responseBody = httpRpcService.httpGet(url);
        if (MStrUtil.isNull(responseBody)) {
            return addressInfo;
        }
        
        JSONObject result = (JSONObject) JSON.parse(responseBody);
        Integer status = result.getInteger("status");
        if(!NumberUtil.equals(Integer.valueOf(0), status)){
            MUtil.log("responseBody  status:" + status + "  message:" + result.getString("message"));
            return addressInfo;
        }
        
        JSONArray locationList = result.getJSONArray("locations");
        if(locationList==null || locationList.size() == 0){
            return addressInfo;
        }
        
        JSONObject latlng = locationList.getJSONObject(0);
        addressInfo = new AddressInfo();
        addressInfo.setLat(latlng.getDouble("lat"));
        addressInfo.setLng(latlng.getDouble("lng"));
        return addressInfo;
    }
    
    
    public static String accuratePositioning(Double lat, Double lng) throws IOException {
        String sAddress = "";
        if (null == lat || null == lng) {
            return sAddress;
        }

        String loactaions = lat + "," + lng;
        String url = String.format(TENCENT_ACCURATE_POSITIONING_URL, loactaions, Integer.valueOf(1));

        HttpRpcService httpRpcService = HttpRpcService.getInstance();
        String responseBody = httpRpcService.httpGet(url);
        if (MStrUtil.isNull(responseBody)) {
            return sAddress;
        }

        JSONObject result = (JSONObject) JSON.parse(responseBody);
        Integer status = result.getInteger("status");
        if (!NumberUtil.equals(Integer.valueOf(0), status)) {
            MUtil.log("responseBody  status:" + status + "  message:" + result.getString("message"));
            return sAddress;
        }

        JSONObject jResult = result.getJSONObject("result");
        if (null == jResult) {
            return sAddress;
        }

        JSONArray locationList = jResult.getJSONArray("pois");
        if (locationList == null || locationList.size() == 0) {
            return sAddress;
        }

        int size = locationList.size();
        if (size > 2) {
            size = 2;
        }

        StringBuilder addressResult = new StringBuilder();
        addressResult.append(jResult.getString("address"));
        addressResult.append(",");
        for (int i = 0; i < size; i++) {
            JSONObject addressObj = locationList.getJSONObject(i);
            String jAddress = StringUtil.notNullString(addressObj.getString("address"));
            String jTtitle = StringUtil.notNullString(addressObj.getString("title"));
            addressResult.append(jAddress);
            if (jAddress.indexOf(jTtitle) == -1) {
                addressResult.append(jTtitle);
            }
            addressResult.append(StringUtil.notNullString(addressObj.getString("_dir_desc")));
            addressResult.append("(");
            addressResult.append(StringUtil.notNullString(addressObj.getString("_distance")));
            addressResult.append("米)");
            if (i != size - 1) {
                addressResult.append(",");
            }
        }
        sAddress = addressResult.toString();
        return sAddress;
    }
    
    

    public static void main(String[] args) throws IOException {
        //HttpClientUtils.fetchCell("460*0*4663*10173*28", "0");
        //MUtil.log(HttpClientUtils.fetchBaiduAddress(22.546345,114.071540));
    	
    	//AddressInfo result = HttpClientUtils.gpsToTencent(22.546345,114.071540);
    	
    	//MUtil.log(result.getLat());
    	//MUtil.log(result.getLng());
    	
    	//MUtil.log("东北".length());
    	
    	String result = HttpClientUtils.accuratePositioning(22.546345,114.071540);
    }
}

