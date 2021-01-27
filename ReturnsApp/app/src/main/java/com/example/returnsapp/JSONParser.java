package com.example.returnsapp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    static JSONObject jsonObject;
    static JSONArray jsonArray;

    JSONParser() {
         jsonObject = new JSONObject();
         jsonArray = new JSONArray();
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void jsonMake() throws JSONException {
       jsonObject.put("to_participant", "urn:authenticateit:participant:804355519238230");

       for(String code: Handler.getCodes()) {
           if(!jsonArray.toString().contains(code))
                jsonArray.put(code);
       }

       jsonObject.put("codes", jsonArray);
       jsonObject.put("order_number", Handler.getDocumentName());
    }
}
