package com.codenvy.example.java.che.action;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class CheActionButtonProcessor3Test {
    private static final String ASCII_DOC = "In::\n" +
            "action:open-file[label=\"Some text\", line=33, path=\"/Some/ file\"]::\n" +
            "  Fusce euismod commodo velit.\n" +
            "\n" +
            "  Fusce euismod commodo velit.\n" +
            "\n" +
            "Ipsum:: Vivamus fringilla mi eu lacus.\n" +
            "  * Vivamus fringilla mi eu lacus.\n" +
            "  * Donec eget arcu bibendum nunc consequat lobortis.\n" +
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
//    private static final String ASCII_DOC = "Try AsciiDoc\n" +
//            "------------\n" +
//            "\n" +
//            "* action:open-file[label=\"Some text\", line=33, path=\"/Some/ file\"]  erer\n";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {

        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();

        extensionRegistry.inlineMacro("action", CheActionButtonProcessor3.class);

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        System.out.println(content);
        String expectedContent = "";

        assertEquals(expectedContent, content);
    }
}
