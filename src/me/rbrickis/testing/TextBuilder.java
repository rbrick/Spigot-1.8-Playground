package me.rbrickis.testing;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Ryan on 12/3/2014
 * <p/>
 * Project: Spigot-1.8
 */
public class TextBuilder {

    JSONObject object = new JSONObject();
    JSONArray extra = new JSONArray();

    public TextBuilder(String text) {
        object.put("text", text);
    }

    public TextBuilder append(String text) {
        JSONObject object1 = new JSONObject();
        object1.put("text", text);
        extra.add(object1);
        return this;
    }

    public String build() {
        if(extra.isEmpty()) {
            return object.toJSONString();
        }
        object.put("extra", extra);
        return object.toJSONString();
    }


}
