package com.codenvy.example.java.che.action;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class CheActionButtonProcessor4Test {
    private static final String ASCII_DOC = "Try AsciiDoc\n" +
            "------------\n" +
            "\n" +
            "[action, id=\"open-file\", label=\"Some text\", line=33, path=\"/Some/ file\"]\n";

    private Asciidoctor asciidoctor = create();

//    @Test
//    public void shouldParseContentWithoutTreeProcessor() {
//        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());
//
//        String expectedOutPut = "";
//
//        assertEquals(outPut, expectedOutPut);
//    }

    @Test
    public void shouldParseContentWithTreeProcessor() {

        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();

        extensionRegistry.inlineMacro("action", CheActionButtonProcessor4.class);

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        System.out.println(content);
        String expectedContent = "";

        assertEquals(expectedContent, content);
    }
}
