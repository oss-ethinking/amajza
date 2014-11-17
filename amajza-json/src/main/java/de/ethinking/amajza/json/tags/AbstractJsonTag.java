package de.ethinking.amajza.json.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AbstractJsonTag extends BodyTagSupport {

    public static final String ENCLOSING_JSON_OBJECT = "de.ethinking.amajza.json.parent_json_object";

    protected Logger LOG = LogManager.getLogger(getClass().getName());
}
