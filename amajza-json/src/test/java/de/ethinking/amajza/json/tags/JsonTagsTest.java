package de.ethinking.amajza.json.tags;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.mock.web.MockPageContext;

public class JsonTagsTest extends TestCase {

    private MockPageContext mockPageContext;

    protected void setUp() throws Exception {
        super.setUp();

        // Create the MockPageContext passing in the mock servlet context
        // created above
        mockPageContext = new MockPageContext();
    }

    public void test01_JsonObjectTag() throws Exception {
        JsonObjectTag tag = new JsonObjectTag();
        tag.setPageContext(mockPageContext);

        tag.doStartTag();
        tag.doEndTag();

        Assert.assertEquals(new JSONObject().toString(), mockPageContext.getContentAsString());
    }

    public void test02_JsonEntryTag() throws Exception {

        JsonTagBuilder tagBuilder = JsonTagBuilderFactory.createTagBuilder(mockPageContext);
        JsonArrayTagBuilder parentArray = tagBuilder.appendArrayTag();

        JsonObjectTagBuilder parent = parentArray.appendObjectTag();
        parent.appendEntryTag().withName("testTrue").withValue("true").withType(JsonEntryType.BOOLEAN);
        parent.appendEntryTag().withName("testNumber").withValue("1").withType(JsonEntryType.NUMBER);
        parent.appendEntryTag().withName("testFloat").withValue("1.1").withType(JsonEntryType.FLOAT);

        parent = parentArray.appendObjectTag();
        parent.appendEntryTag().withName("testTrue").withValue("false").withType(JsonEntryType.BOOLEAN);
        parent.appendEntryTag().withName("testNumber").withValue("2").withType(JsonEntryType.NUMBER);
        parent.appendEntryTag().withName("testFloat").withValue("1.2").withType(JsonEntryType.FLOAT);
        JsonArrayTagBuilder childArrayParent = parent.appendEntryTag().withName("testEntries").appendArrayTag();

        parent = childArrayParent.appendObjectTag();
        parent.appendEntryTag().withName("testFloat1").withValue("2.1").withType(JsonEntryType.FLOAT);
        parent.appendEntryTag().withName("testFloat2").withValue("2.2").withType(JsonEntryType.FLOAT);
        parent = childArrayParent.appendObjectTag();
        parent.appendEntryTag().withName("testFloat1").withValue("3.1").withType(JsonEntryType.FLOAT);
        parent.appendEntryTag().withName("testFloat2").withValue("3.2").withType(JsonEntryType.FLOAT);

        tagBuilder.invokeTags();
        System.out.println(mockPageContext.getContentAsString());
    }
}
