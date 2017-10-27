package com.codenvy.example.java.processors;

import com.codenvy.example.java.processors.ChangeAttributeValuePreprocessor;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class ChangeAttributeValuePreprocessorTest {

    private static final String ASCII_DOC = "= Document Title\n" +
            "\n" +
            "sample {content}";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseWithoutPreprocessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"paragraph\">\n" +
                "<p>sample {content}</p>\n" +
                "</div>";
        assertEquals(expectedOutPut, outPut);
    }

    @Test
    public void shouldParseAfterPreprocessor() {
        JavaExtensionRegistry extensionRegistry = asciidoctor.javaExtensionRegistry();

        extensionRegistry.preprocessor(ChangeAttributeValuePreprocessor.class);
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"paragraph\">\n" +
                "<p>sample Alex</p>\n" +
                "</div>";

        assertEquals(expectedOutPut, outPut);
    }
}
