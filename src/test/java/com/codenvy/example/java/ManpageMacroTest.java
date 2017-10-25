package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class ManpageMacroTest {
    private static final String ASCII_DOC = "See man:gittutorial[7] to get started.";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"paragraph\">\n" +
                "<p>See man:gittutorial[7] to get started.</p>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {
        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();

        extensionRegistry.inlineMacro("man", ManpageMacro.class);

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        String expectedContent = "<div class=\"paragraph\">\n" +
                "<p>See <a href=\"gittutorial.html\">gittutorial</a> to get started.</p>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }
}
