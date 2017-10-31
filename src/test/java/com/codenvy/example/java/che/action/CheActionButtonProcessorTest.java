package com.codenvy.example.java.che.action;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CheActionButtonProcessorTest {
    private static final String ASCII_DOC = "Try AsciiDoc\n" +
        "------------\n" +
        "\n" +
        "To open file click: action:open-file[label=\"Some text\", line=33, path=\"/Some/ file\"]  Action .\n";

    private static final String ASCII_DOC2 = "In::\n" +
            "  action:open-file[label=\"Some text\", line=33, path=\"/Some/ file\"].\n" +
            "  Fusce euismod commodo velit.\n" +
            "\n" +
            "  Fusce euismod commodo velit.\n" +
            "\n" +
            "Ipsum:: Vivamus fringilla mi eu lacus.\n" +
            "  * Vivamus fringilla mi eu lacus.\n" +
            "  * Donec eget arcu bibendum nunc action:open-file[label=\"Some text\", line=33, path=\"/Some/ file\"] lobortis.\n" +
            "Dolor::\n" +
            "  Donec eget arcu bibendum nunc consequat lobortis.\n" +
            "  Suspendisse;;\n" +
            "    A massa id sem aliquam auctor.\n" +
            "  Morbi;;\n" +
            "    Pretium nulla vel lorem.\n" +
            "  In;;\n" +
            "    fgdgdf\n" +
            "    Vivamus::: Fringilla mi eu lacus.\n" +
            "    Donec:::   Eget arcu bibendum nunc consequat lobortis.";

    private static final String ASCII_DOC3 = "[width=\"15%\"]\n" +
            "|=======\n" +
            "|1 |2 |action:open-file[label=\"Some text\", line=33, path=\"/Some/ file\"]\n" +
            "|3 |4 |B\n" +
            "|5 |6 |C\n" +
            "|=======";

    private static final String ASCII_DOC4 = "[qanda]\n" +
            "Question one::\n" +
            "        Answer one. action:open-file[label=\"Some text\", line=33, path=\"/Some/ file\"] \n" +
            "Question two::\n" +
            "        Answer two.";

    private static Asciidoctor asciiDoctorWithoutExtension = Asciidoctor.Factory.create();
    private static Asciidoctor asciiDoctorWithExtension = Asciidoctor.Factory.create();

    @BeforeClass
    public static void setUp() {
        JavaExtensionRegistry extensionRegistry = asciiDoctorWithExtension.javaExtensionRegistry();
        extensionRegistry.inlineMacro("action", CheActionButtonProcessor.class);
    }

    @Test
    public void shouldParseContentWithoutProcessor() {
        String outPut = asciiDoctorWithoutExtension.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"sect1\">\n" +
                "<h2 id=\"_try_asciidoc\">Try AsciiDoc</h2>\n" +
                "<div class=\"sectionbody\">\n" +
                "<div class=\"paragraph\">\n" +
                "<p>To open file click: action:open-file[label=\"Some text\", line=33, path=\"/Some/ file\"]  Action .</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseActionInsideParagraph() {
        String content = asciiDoctorWithExtension.convert(ASCII_DOC, new Options());

        String expectedContent = "<div class=\"sect1\">\n" +
                "<h2 id=\"_try_asciidoc\">Try AsciiDoc</h2>\n" +
                "<div class=\"sectionbody\">\n" +
                "<div class=\"paragraph\">\n" +
                "<p>To open file click: <input type=\"button\" id=\"some_id_for_mapping\" class=\"quick-guide-action\" value=\"Some text\"/>  Action .</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }

    @Test
    public void shouldParseActionInsideList() {
        String content = asciiDoctorWithExtension.convert(ASCII_DOC2, new Options());

        String expectedContent = "<div class=\"dlist\">\n" +
                "<dl>\n" +
                "<dt class=\"hdlist1\">In</dt>\n" +
                "<dd>\n" +
                "<p><input type=\"button\" id=\"some_id_for_mapping\" class=\"quick-guide-action\" value=\"Some text\"/>.\n" +
                "Fusce euismod commodo velit.</p>\n" +
                "<div class=\"literalblock\">\n" +
                "<div class=\"content\">\n" +
                "<pre>Fusce euismod commodo velit.</pre>\n" +
                "</div>\n" +
                "</div>\n" +
                "</dd>\n" +
                "<dt class=\"hdlist1\">Ipsum</dt>\n" +
                "<dd>\n" +
                "<p>Vivamus fringilla mi eu lacus.</p>\n" +
                "<div class=\"ulist\">\n" +
                "<ul>\n" +
                "<li>\n" +
                "<p>Vivamus fringilla mi eu lacus.</p>\n" +
                "</li>\n" +
                "<li>\n" +
                "<p>Donec eget arcu bibendum nunc <input type=\"button\" id=\"some_id_for_mapping\" class=\"quick-guide-action\" value=\"Some text\"/> lobortis.</p>\n" +
                "</li>\n" +
                "</ul>\n" +
                "</div>\n" +
                "</dd>\n" +
                "<dt class=\"hdlist1\">Dolor</dt>\n" +
                "<dd>\n" +
                "<p>Donec eget arcu bibendum nunc consequat lobortis.</p>\n" +
                "<div class=\"dlist\">\n" +
                "<dl>\n" +
                "<dt class=\"hdlist1\">Suspendisse</dt>\n" +
                "<dd>\n" +
                "<p>A massa id sem aliquam auctor.</p>\n" +
                "</dd>\n" +
                "<dt class=\"hdlist1\">Morbi</dt>\n" +
                "<dd>\n" +
                "<p>Pretium nulla vel lorem.</p>\n" +
                "</dd>\n" +
                "<dt class=\"hdlist1\">In</dt>\n" +
                "<dd>\n" +
                "<p>fgdgdf</p>\n" +
                "<div class=\"dlist\">\n" +
                "<dl>\n" +
                "<dt class=\"hdlist1\">Vivamus</dt>\n" +
                "<dd>\n" +
                "<p>Fringilla mi eu lacus.</p>\n" +
                "</dd>\n" +
                "<dt class=\"hdlist1\">Donec</dt>\n" +
                "<dd>\n" +
                "<p>Eget arcu bibendum nunc consequat lobortis.</p>\n" +
                "</dd>\n" +
                "</dl>\n" +
                "</div>\n" +
                "</dd>\n" +
                "</dl>\n" +
                "</div>\n" +
                "</dd>\n" +
                "</dl>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }

    @Test
    public void shouldParseActionInsideTable() {
        String content = asciiDoctorWithExtension.convert(ASCII_DOC3, new Options());

        String expectedContent = "<table class=\"tableblock frame-all grid-all\" style=\"width: 15%;\">\n" +
                "<colgroup>\n" +
                "<col style=\"width: 33.3333%;\">\n" +
                "<col style=\"width: 33.3333%;\">\n" +
                "<col style=\"width: 33.3334%;\">\n" +
                "</colgroup>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\">1</p></td>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\">2</p></td>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\"><input type=\"button\" id=\"some_id_for_mapping\" class=\"quick-guide-action\" value=\"Some text\"/></p></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\">3</p></td>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\">4</p></td>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\">B</p></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\">5</p></td>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\">6</p></td>\n" +
                "<td class=\"tableblock halign-left valign-top\"><p class=\"tableblock\">C</p></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>";

        assertEquals(expectedContent, content);
    }

    @Test
    public void shouldBeParseInsideQuestionAnswerList() {
        String content = asciiDoctorWithExtension.convert(ASCII_DOC4, new Options());

        String expectedContent = "<div class=\"qlist qanda\">\n" +
                "<ol>\n" +
                "<li>\n" +
                "<p><em>Question one</em></p>\n" +
                "<p>Answer one. <input type=\"button\" id=\"some_id_for_mapping\" class=\"quick-guide-action\" value=\"Some text\"/></p>\n" +
                "</li>\n" +
                "<li>\n" +
                "<p><em>Question two</em></p>\n" +
                "<p>Answer two.</p>\n" +
                "</li>\n" +
                "</ol>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }
}
