package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class CustomFooterPostProcessorTest {
    private static final String ASCII_DOC = "= Document Title\n" +
            "\n" +
            "[ditaa,asciidoctor-diagram-process]\n" +
            "....\n" +
            "                   +-------------+\n" +
            "                   | Asciidoctor |-------+\n" +
            "                   |   diagram   |       |\n" +
            "                   +-------------+       | PNG out\n" +
            "                       ^                 |\n" +
            "                       | ditaa in        |\n" +
            "                       |                 v\n" +
            " +--------+   +--------+----+    /---------------\\\n" +
            " |        |---+ Asciidoctor +--->|               |\n" +
            " |  Text  |   +-------------+    |   Beautiful   |\n" +
            " |Document|   |   !magic!   |    |    Output     |\n" +
            " |     {d}|   |             |    |               |\n" +
            " +---+----+   +-------------+    \\---------------/\n" +
            "     :                                   ^\n" +
            "     |          Lots of work             |\n" +
            "     +-----------------------------------+\n" +
            "....\nA statement.footnote:[Clarification about this statement.]";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"literalblock\">\n" +
                "<div class=\"content\">\n" +
                "<pre>                   +-------------+\n" +
                "                   | Asciidoctor |-------+\n" +
                "                   |   diagram   |       |\n" +
                "                   +-------------+       | PNG out\n" +
                "                       ^                 |\n" +
                "                       | ditaa in        |\n" +
                "                       |                 v\n" +
                " +--------+   +--------+----+    /---------------\\\n" +
                " |        |---+ Asciidoctor +---&gt;|               |\n" +
                " |  Text  |   +-------------+    |   Beautiful   |\n" +
                " |Document|   |   !magic!   |    |    Output     |\n" +
                " |     {d}|   |             |    |               |\n" +
                " +---+----+   +-------------+    \\---------------/\n" +
                "     :                                   ^\n" +
                "     |          Lots of work             |\n" +
                "     +-----------------------------------+</pre>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"paragraph\">\n" +
                "<p>A statement.<sup class=\"footnote\">[<a id=\"_footnoteref_1\" class=\"footnote\" href=\"#_footnote_1\" title=\"View footnote.\">1</a>]</sup></p>\n" +
                "</div>\n" +
                "<div id=\"footnotes\">\n" +
                "<hr>\n" +
                "<div class=\"footnote\" id=\"_footnote_1\">\n" +
                "<a href=\"#_footnoteref_1\">1</a>. Clarification about this statement.\n" +
                "</div>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {
        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();

        extensionRegistry.postprocessor(new CustomFooterPostProcessor(new HashMap<>()));

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        String expectedContent = "<html>\n" +
                " <head></head>\n" +
                " <body>\n" +
                "  <div class=\"literalblock\"> \n" +
                "   <div class=\"content\"> \n" +
                "    <pre>                   +-------------+\n" +
                "                   | Asciidoctor |-------+\n" +
                "                   |   diagram   |       |\n" +
                "                   +-------------+       | PNG out\n" +
                "                       ^                 |\n" +
                "                       | ditaa in        |\n" +
                "                       |                 v\n" +
                " +--------+   +--------+----+    /---------------\\\n" +
                " |        |---+ Asciidoctor +---&gt;|               |\n" +
                " |  Text  |   +-------------+    |   Beautiful   |\n" +
                " |Document|   |   !magic!   |    |    Output     |\n" +
                " |     {d}|   |             |    |               |\n" +
                " +---+----+   +-------------+    \\---------------/\n" +
                "     :                                   ^\n" +
                "     |          Lots of work             |\n" +
                "     +-----------------------------------+</pre> \n" +
                "   </div> \n" +
                "  </div> \n" +
                "  <div class=\"paragraph\"> \n" +
                "   <p>A statement.<sup class=\"footnote\">[<a id=\"_footnoteref_1\" class=\"footnote\" href=\"#_footnote_1\" title=\"View footnote.\">1</a>]Copyright Acme, Inc.</sup></p> \n" +
                "  </div> \n" +
                "  <div id=\"footnotes\"> \n" +
                "   <hr /> \n" +
                "   <div class=\"footnote\" id=\"_footnote_1\"> \n" +
                "    <a href=\"#_footnoteref_1\">1</a>. Clarification about this statement. \n" +
                "   </div> \n" +
                "  </div>\n" +
                " </body>\n" +
                "</html>";

        assertEquals(expectedContent, content);
    }
}
