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
    boolean parse = false;

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
        try {
            if (value == null) {
                BodyContent bodyContent = getBodyContent();
                if (bodyContent != null) {
                    try {
                        String body = getBodyContent().getString();
                        if (parse) {
                            body = body.trim();
                            if (body.startsWith("{")) {
                                value = new JSONObject(body);
                            } else if (body.startsWith("[")) {
                                value = new JSONArray(body);
                            } else {
                                value = body;
                            }
                        } else {
                            value = body;
                        }
                    } catch (Exception e) {
                        throw new JspException("failed in JSON entry [" + name + "] body: " + bodyContent.getString(), e);
                    }
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
        } finally {
            release();
        }
    }

    @Override
    public void release() {
        super.release();
        name = null;
        value = null;
        type = null;
        jsonObject = null;
        parse = false;
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

    public void setParse(boolean parseBody) {
        this.parse = parseBody;
    }

    @Override
    public void setJsonObject(JSONObject jsonObject) {
        setValue(jsonObject);
    }
}
