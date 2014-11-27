package de.ethinking.amajza.json.tags;

import org.json.JSONObject;

@SuppressWarnings("serial")
public abstract class AbstractJsonObjectParentTag extends AbstractJsonTag {

    protected abstract void setJsonObject(JSONObject jsonObject);
}
