package de.ethinking.amajza.json.tags;

import org.springframework.mock.web.MockPageContext;

public class JsonTagBuilderFactory {

    public static JsonTagBuilder createTagBuilder(MockPageContext mockPageContext) {
        return new JsonTagBuilder(mockPageContext);
    }

}
