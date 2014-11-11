package de.ethinking.amajza.json.tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class AbstractJsonTagBuilder<T extends BodyTagSupport> {

    private List<AbstractJsonTagBuilder<?>> tags = new ArrayList<AbstractJsonTagBuilder<?>>();
    private PageContext pageContext;
    private T tag;

    public AbstractJsonTagBuilder(PageContext pageContext, T tag) {
        this.pageContext = pageContext;
        this.tag = tag;
        tag.setPageContext(pageContext);
    }

    public void invokeTags() throws Exception {
        tag.doStartTag();
        for (AbstractJsonTagBuilder<?> tag : tags) {
            tag.invokeTags();
        }
        tag.doEndTag();
    }

    protected PageContext getPageContext() {
        return pageContext;
    }

    protected <X extends AbstractJsonTagBuilder<?>> X addTag(X x, AbstractJsonTagBuilder<?> parent) {
        tags.add(x);
        x.getTag().setParent(parent.getTag());
        return x;
    }

    protected T getTag() {
        return tag;
    }
}
