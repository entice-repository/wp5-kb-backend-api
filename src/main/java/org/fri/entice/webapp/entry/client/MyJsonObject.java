package org.fri.entice.webapp.entry.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Locale;
import java.util.Map;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyJsonObject extends JSONObject {

    private Object build;
    private Object test;

    public MyJsonObject() {
    }

    public MyJsonObject(JSONObject jo, String[] names) {
        super(jo, names);
    }

    public MyJsonObject(JSONTokener x) throws JSONException {
        super(x);
    }

    public MyJsonObject(Map<?, ?> map) {
        super(map);
    }

    public MyJsonObject(Object bean) {
        super(bean);
    }

    public MyJsonObject(Object object, String[] names) {
        super(object, names);
    }

    public MyJsonObject(String source) throws JSONException {
        super(source);
    }

    public MyJsonObject(String baseName, Locale locale) throws JSONException {
        super(baseName, locale);
    }

    public Object getBuild() {
        return build;
    }

    public void setBuild(Object build) {
        this.build = build;
    }

    public Object getTest() {
        return test;
    }

    public void setTest(Object test) {
        this.test = test;
    }
}
