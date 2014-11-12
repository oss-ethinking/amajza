package de.ethinking.amajza.json.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectTag extends BodyTagSupport {

    private JSONObject jsonObject;

    @Override
    public int doStartTag() throws JspException {
        jsonObject = new JSONObject();
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        BodyContent bodyContent = getBodyContent();
        if (bodyContent != null) {
            JSONObject bodyJson = new JSONObject(bodyContent.getString()); 
            for (Object key : bodyJson.keySet()) {
                jsonObject.putOnce((String) key, bodyJson.get((String) key));
            }
        }
        // if has parent, add object there, else render json string to page
        JsonObjectParentTag parentTag = null;
        if (getParent() != null && (parentTag = (JsonObjectParentTag) findAncestorWithClass(this, JsonObjectParentTag.class)) != null) {
            // parents of JsonObjectTag or JsonArrayTag need to be used
            parentTag.setJsonObject(jsonObject);
        } else {
            try {
                pageContext.getOut().print(jsonObject.toString(4));
            } catch (JSONException e) {
                throw new JspException(e);
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return EVAL_PAGE;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
