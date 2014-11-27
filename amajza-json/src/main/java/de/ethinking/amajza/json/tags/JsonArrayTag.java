package de.ethinking.amajza.json.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class JsonArrayTag extends AbstractJsonObjectParentTag {

    private JSONArray jsonArray;

    @Override
    public int doStartTag() throws JspException {
        pushStack();
        jsonArray = new JSONArray();
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
                        JSONArray bodyJson = new JSONArray(body);
                        for (int i = 0; i < bodyJson.length(); i++) {
                            jsonArray.put(bodyJson.get(i));
                        }
                    }
                } catch (Exception e) {
                    LOG.error("failed in JSON object body: " + bodyContent.getString(), e);
                }
            }
            // if has parent, add object there, else render json string to page
            JsonEntryTag parentTag = findParentTag(JsonEntryTag.class);
            if (parentTag != null) {
                // parents of JsonObjectTag or JsonArrayTag need to be used
                parentTag.setValue(jsonArray);
            } else {
                try {
                    pageContext.getOut().print(jsonArray.toString(4));
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

    @Override
    public void setJsonObject(JSONObject jsonObject) {
        //if (jsonObject.length() > 0) {
            jsonArray.put(jsonObject);
        //}
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }
}
