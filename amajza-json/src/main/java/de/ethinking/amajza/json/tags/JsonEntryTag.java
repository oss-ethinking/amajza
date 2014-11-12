package de.ethinking.amajza.json.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonEntryTag extends BodyTagSupport implements JsonObjectParentTag {

    String name;
    Object value;
    JsonEntryType type;
    JSONObject jsonObject;

    @Override
    public int doStartTag() throws JspException {
        JsonObjectTag parentObjectTag = (JsonObjectTag) findAncestorWithClass(this, JsonObjectTag.class);
        if (parentObjectTag != null) {
            jsonObject = parentObjectTag.getJsonObject();
        } else {
            throw new JspException("no enclosing JsonObjectTag found");
        }
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        if (value == null) {
            BodyContent bodyContent = getBodyContent();
            if (bodyContent != null) {
                value = getBodyContent().getString();
            }
        }
        if (value != null) {
            try {
                if (type != null && !(value instanceof JSONObject) && !(value instanceof JSONArray)) {
                    switch (type) {
                    case BOOLEAN:
                        jsonObject.put(name, Boolean.parseBoolean(value.toString()));
                        break;
                    case FLOAT:
                        jsonObject.put(name, Double.parseDouble(value.toString()));
                        break;
                    case NUMBER:
                        jsonObject.put(name, Long.parseLong(value.toString()));
                        break;
                    case STRING:
                        jsonObject.put(name, value.toString());
                        break;
                    }
                } else {
                    jsonObject.put(name, value);
                }
            } catch (Exception e) {
                throw new JspException(e);
            }
        }
        return super.doEndTag();
    }

    @Override
    public void release() {
        super.release();
        name = null;
        value = null;
        type = null;
        jsonObject = null;
    }

    public void setType(JsonEntryType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public void setJsonObject(JSONObject jsonObject) {
        setValue(jsonObject);
    }
}
