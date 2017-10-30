package com.codenvy.example.java.che.action;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class CheActionButtonProcessor2Test {

    private static final String ASCII_DOC = "Try AsciiDoc\n" +
            "------------\n" +
            "\n" +
            "[action type=openFile path=/path/to/file scroll=23 highlight=23:25]\n";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"sect1\">\n" +
                "<h2 id=\"_try_asciidoc\">Try AsciiDoc</h2>\n" +
                "<div class=\"sectionbody\">\n" +
                "\n" +
                "</div>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {

//        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();
//
//        extensionRegistry.block("action", CheActionButtonProcessor2.class);
//
//        String content = asciidoctor.convert(ASCII_DOC, new Options());
//
//        System.out.println(content);
//        String expectedContent = "";
//
//        assertEquals(expectedContent, content);
    }
}
