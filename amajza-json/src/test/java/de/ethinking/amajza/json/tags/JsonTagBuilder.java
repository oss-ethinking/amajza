package de.ethinking.amajza.json.tags;

import javax.servlet.jsp.PageContext;

public class JsonTagBuilder extends AbstractJsonTagBuilder<DummyTag> {

    public JsonTagBuilder(PageContext pageContext) {
        super(pageContext, new DummyTag());
    }

    public JsonObjectTagBuilder appendObjectTag() {
        return addTag(new JsonObjectTagBuilder(getPageContext()), this);
    }

    public JsonArrayTagBuilder appendArrayTag() {
        return addTag(new JsonArrayTagBuilder(getPageContext()), this);
    }
}
