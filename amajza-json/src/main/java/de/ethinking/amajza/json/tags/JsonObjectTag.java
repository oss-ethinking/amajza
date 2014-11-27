package de.ethinking.amajza.json.tags;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class JsonObjectTag extends AbstractJsonTag {

    private JSONObject jsonObject;

    @Override
    public int doStartTag() throws JspException {
        jsonObject = new JSONObject();
        pushStack();
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            BodyContent bodyContent = getBodyContent();
            if (bodyContent != null) {
                try {
                    String body = bodyContent.getString().trim();
                    if (body.length() > 1) {
                        JSONObject bodyJson = new JSONObject(bodyContent.getString());
                        for (@SuppressWarnings("unchecked")
                        Iterator<String> it = bodyJson.keys(); it.hasNext();) {
                            String key = it.next();
                            if (jsonObject.opt(key) == null) {
                                jsonObject.put(key, bodyJson.get(key));
                            }
                        }
                    }
                } catch (Exception e) {
                    LOG.error("failed in JSON object body: " + bodyContent.getString(), e);
                }
            }
            // if has parent, add object there, else render json string to page
            AbstractJsonObjectParentTag parentTag = findParentTag(AbstractJsonObjectParentTag.class);
            if (parentTag != null) {
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
        } finally {
            release();
            popStack();
        }
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
