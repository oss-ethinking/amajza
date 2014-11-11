package de.ethinking.amajza.json.tags;

import javax.servlet.jsp.PageContext;

public class JsonObjectTagBuilder extends AbstractJsonTagBuilder<JsonObjectTag> {

    public JsonObjectTagBuilder(PageContext pageContext) {
        super(pageContext, new JsonObjectTag());
    }

    public JsonEntryTagBuilder appendEntryTag() {
        return addTag(new JsonEntryTagBuilder(getPageContext()), this);
    }
}
