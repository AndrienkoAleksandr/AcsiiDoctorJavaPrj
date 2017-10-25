package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class YellBlockTest {
    private static final String ASCII_DOC = "[yell]\n" +
            "The time is now. Get a move on.";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"paragraph\">\n" +
                "<p>The time is now. Get a move on.</p>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {
        JavaExtensionRegistry extensionRegistry = asciidoctor.javaExtensionRegistry();

        extensionRegistry.block("yell", YellBlock.class);

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        String expectedContent = "<div class=\"paragraph\">\n" +
                "<p>THE TIME IS NOW. GET A MOVE ON.</p>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }
}
