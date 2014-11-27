package de.ethinking.amajza.json.tags;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@SuppressWarnings({ "unchecked", "serial" })
public class AbstractJsonTag extends BodyTagSupport {

    private static final String JSON_TAG_STACK = "de.ethinking.amajza.json.json_stack";

    protected Logger LOG = LogManager.getLogger(getClass().getName());

    protected void pushStack() {
        Deque<AbstractJsonTag> stack = (Deque<AbstractJsonTag>) pageContext.getRequest().getAttribute(JSON_TAG_STACK);
        if (stack == null) {
            stack = new ArrayDeque<>();
            pageContext.getRequest().setAttribute(JSON_TAG_STACK, stack);
        }
        stack.push(this);
    }

    protected void popStack() {
        Deque<AbstractJsonTag> stack = (Deque<AbstractJsonTag>) pageContext.getRequest().getAttribute(JSON_TAG_STACK);
        if (stack != null) {
            stack.pop();
        }
    }

    protected <T extends AbstractJsonTag> T findParentTag(Class<T> clazz) {
        Deque<AbstractJsonTag> stack = (Deque<AbstractJsonTag>) pageContext.getRequest().getAttribute(JSON_TAG_STACK);
        if (stack != null) {
            for (Iterator<AbstractJsonTag> it = stack.iterator(); it.hasNext();) {
                AbstractJsonTag tag = it.next();
                if (tag != this && clazz.isInstance(tag)) {
                    return (T) tag;
                }
            }
        }
        return null;
    }
}
