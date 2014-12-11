package de.ethinking.amajza.json.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class JsonSetTag extends AbstractJsonObjectParentTag {

    private Object json;
    private String var;

    @Override
    public int doStartTag() throws JspException {
        pushStack();
        json = null;
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            if (var!=null && json!=null) {
                pageContext.setAttribute(var, json);
            } else {
                pageContext.removeAttribute(var);
            }
            return EVAL_PAGE;
        } finally {
            release();
            popStack();
        }
    }

    @Override
    public void setJsonObject(JSONObject jsonObject) {
        json = jsonObject;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public void release() {
        super.release();
        var = null;
    }
}
