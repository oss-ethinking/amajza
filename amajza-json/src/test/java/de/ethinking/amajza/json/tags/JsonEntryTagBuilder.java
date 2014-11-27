package de.ethinking.amajza.json.tags;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

public class JsonEntryTagBuilder extends AbstractJsonTagBuilder<JsonEntryTag> {

    public JsonEntryTagBuilder(PageContext pageContext) {
        super(pageContext, new JsonEntryTag());
    }

    public JsonEntryTagBuilder withName(String name) {
        getTag().setName(name);
        return this;
    }

    public JsonEntryTagBuilder withValue(Object value) {
        getTag().setValue(value);
        return this;
    }

    public JsonEntryTagBuilder withType(JsonEntryType type) {
        getTag().setType(type);
        return this;
    }

    public JsonEntryTagBuilder withTagContent(BodyContent content) {
        getTag().setParse(true);
        getTag().setBodyContent(content);
        return this;
    }
    public JsonArrayTagBuilder appendArrayTag() {
        return addTag(new JsonArrayTagBuilder(getPageContext()), this);
    }
}
