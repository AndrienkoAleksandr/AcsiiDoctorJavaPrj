package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class TerminalCommandTreeprocessorTest {

    private static final String ASCII_DOC = "Hello World\n" +
            "\n" +
            "$ echo \"Hello, World!\"\n" +
            "\n" +
            "$ gem install asciidoctor";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"paragraph\">\n" +
                "<p>Hello World</p>\n" +
                "</div>\n" +
                "<div class=\"paragraph\">\n" +
                "<p>$ echo \"Hello, World!\"</p>\n" +
                "</div>\n" +
                "<div class=\"paragraph\">\n" +
                "<p>$ gem install asciidoctor</p>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);

    }

    @Test
    public void shouldParseContentWithTreeProcessor() {
        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();

        extensionRegistry.treeprocessor(TerminalCommandTreeprocessor.class);

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        String expectedContent = "<div class=\"paragraph\">\n" +
                "<p>Hello World</p>\n" +
                "</div>\n" +
                "<div class=\"listingblock terminal\">\n" +
                "<div class=\"content\">\n" +
                "<pre><span class=\"command\">echo \"Hello, World!\"</span></pre>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div class=\"listingblock terminal\">\n" +
                "<div class=\"content\">\n" +
                "<pre><span class=\"command\">gem install asciidoctor</span></pre>\n" +
                "</div>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }
}
