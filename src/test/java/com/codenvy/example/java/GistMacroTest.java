package com.codenvy.example.java;

import com.codenvy.example.java.processors.GistMacro;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class GistMacroTest {
    private static final String ASCII_DOC = ".My Gist\n" +
            "gist::123456[]";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"paragraph\">\n" +
                "<div class=\"title\">My Gist</div>\n" +
                "<p>gist::123456[]</p>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {
        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();

        extensionRegistry.blockMacro("gist", GistMacro.class);

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        String expectedContent = "<div class=\"content\">\n" +
                "<script src=\"https://gist.github.com/123456.js\"></script>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }
}
