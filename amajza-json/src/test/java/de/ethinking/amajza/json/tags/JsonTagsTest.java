package de.ethinking.amajza.json.tags;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.jsp.tagext.BodyContent;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.mock.web.MockBodyContent;
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
        MockBodyContent bodyContent = new MockBodyContent("{ \"bodyContent\" : \"value\" }", new StringWriter());
        parent.getTag().setBodyContent(bodyContent);

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

        JSONArray result = new JSONArray(mockPageContext.getContentAsString());

        Assert.assertEquals(2, result.length());

        Assert.assertTrue(result.getJSONObject(0).getBoolean("testTrue"));
        Assert.assertEquals(1, result.getJSONObject(0).getLong("testNumber"));
        Assert.assertEquals(1.1d, result.getJSONObject(0).getDouble("testFloat"), 0.0d);
        Assert.assertEquals("value", result.getJSONObject(0).getString("bodyContent"));

        Assert.assertFalse(result.getJSONObject(1).getBoolean("testTrue"));
        Assert.assertEquals(2, result.getJSONObject(1).getLong("testNumber"));
        Assert.assertEquals(1.2d, result.getJSONObject(1).getDouble("testFloat"), 0.0d);
        Assert.assertEquals(2, result.getJSONObject(1).getJSONArray("testEntries").length());
        Assert.assertEquals(2.1d, result.getJSONObject(1).getJSONArray("testEntries").getJSONObject(0).getDouble("testFloat1"), 0.0d);
        Assert.assertEquals(2.2d, result.getJSONObject(1).getJSONArray("testEntries").getJSONObject(0).getDouble("testFloat2"), 0.0d);
        Assert.assertEquals(3.1d, result.getJSONObject(1).getJSONArray("testEntries").getJSONObject(1).getDouble("testFloat1"), 0.0d);
        Assert.assertEquals(3.2d, result.getJSONObject(1).getJSONArray("testEntries").getJSONObject(1).getDouble("testFloat2"), 0.0d);
    }
}
