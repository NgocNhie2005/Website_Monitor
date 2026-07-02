package com.monitor.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComparisonStrategyTest {

    @Test
    void contentSize_sameSize_returnsFalse() {
        ContentSizeStrategy s = new ContentSizeStrategy();
        assertFalse(s.hasChanged("hello", "world"));
    }

    @Test
    void contentSize_differentSize_returnsTrue() {
        ContentSizeStrategy s = new ContentSizeStrategy();
        assertTrue(s.hasChanged("hi", "hello world"));
    }

    @Test
    void contentSize_bothEmpty_returnsFalse() {
        ContentSizeStrategy s = new ContentSizeStrategy();
        assertFalse(s.hasChanged("", ""));
    }

    @Test
    void contentSize_oneEmpty_returnsTrue() {
        ContentSizeStrategy s = new ContentSizeStrategy();
        assertTrue(s.hasChanged("", "some content"));
    }

    @Test
    void html_identicalContent_returnsFalse() {
        HtmlContentStrategy s = new HtmlContentStrategy();
        assertFalse(s.hasChanged("<p>hello</p>", "<p>hello</p>"));
    }

    @Test
    void html_differentContent_returnsTrue() {
        HtmlContentStrategy s = new HtmlContentStrategy();
        assertTrue(s.hasChanged("<p>hello</p>", "<p>world</p>"));
    }

    @Test
    void html_sameTextDifferentTags_returnsTrue() {
        HtmlContentStrategy s = new HtmlContentStrategy();
        assertTrue(s.hasChanged("<b>hi</b>", "<i>hi</i>"));
    }

    @Test
    void html_bothEmpty_returnsFalse() {
        HtmlContentStrategy s = new HtmlContentStrategy();
        assertFalse(s.hasChanged("", ""));
    }

    @Test
    void text_sameVisibleText_differentTags_returnsFalse() {
        TextContentStrategy s = new TextContentStrategy();
        assertFalse(s.hasChanged("<b>hello</b>", "<i>hello</i>"));
    }

    @Test
    void text_differentVisibleText_returnsTrue() {
        TextContentStrategy s = new TextContentStrategy();
        assertTrue(s.hasChanged("<p>hello</p>", "<p>world</p>"));
    }

    @Test
    void text_whitespaceOnly_returnsFalse() {
        TextContentStrategy s = new TextContentStrategy();
        assertFalse(s.hasChanged("hello", "hello  "));
    }

    @Test
    void text_nullOldContent_returnsTrue() {
        TextContentStrategy s = new TextContentStrategy();
        assertTrue(s.hasChanged(null, "hello"));
    }
}