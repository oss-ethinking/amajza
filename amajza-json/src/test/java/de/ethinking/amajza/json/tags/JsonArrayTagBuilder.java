package de.ethinking.amajza.json.tags;

import javax.servlet.jsp.PageContext;

public class JsonArrayTagBuilder extends AbstractJsonTagBuilder<JsonArrayTag> {

    public JsonArrayTagBuilder(PageContext pageContext) {
        super(pageContext, new JsonArrayTag());
    }

    public JsonObjectTagBuilder appendObjectTag() {
        return addTag(new JsonObjectTagBuilder(getPageContext()), this);
    }
}
