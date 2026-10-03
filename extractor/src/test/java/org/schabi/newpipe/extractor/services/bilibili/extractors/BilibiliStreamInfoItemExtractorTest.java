package org.schabi.newpipe.extractor.services.bilibili.extractors;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import org.junit.jupiter.api.Test;
import org.schabi.newpipe.extractor.exceptions.ParsingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BilibiliStreamInfoItemExtractorTest {
    @Test
    void exposesSearchResultUploaderUrl() throws JsonParserException, ParsingException {
        final JsonObject item = JsonParser.object().from("""
                {
                  "title": "Video",
                  "arcurl": "https://www.bilibili.com/video/BV1example",
                  "pic": "//i0.hdslb.com/cover.jpg",
                  "duration": "1:02",
                  "play": 42,
                  "author": "Creator",
                  "mid": 3493144593172743,
                  "upic": "//i0.hdslb.com/avatar.jpg",
                  "pubdate": 1700000000
                }
                """);

        final BilibiliStreamInfoItemExtractor extractor =
                new BilibiliStreamInfoItemExtractor(item);

        assertEquals("https://space.bilibili.com/3493144593172743",
                extractor.getUploaderUrl());
    }
}
