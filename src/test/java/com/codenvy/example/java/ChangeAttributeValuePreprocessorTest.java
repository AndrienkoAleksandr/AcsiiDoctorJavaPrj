package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class ChangeAttributeValuePreprocessorTest {

    private static final String ASCII_DOC_HEADER = "= Sample Document\n" +
            "\n" +
            "[style one]\n" +
            "This is content of first content part\n" +
            "\n" +
            "[[partId]]\n" +
            "[style two,role=partRole]\n" +
            "--\n" +
            "And content of second content part\n" +
            "\n" +
            "This block can be as long as you want.\n" +
            "--";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseWithoutPreprocessor() {
        String outPut = asciidoctor.convert(ASCII_DOC_HEADER, new HashMap<>());

        String expectedOutPut = "<div class=\"paragraph\">\n" +
                "<p>This is content of first content part</p>\n" +
                "</div>\n" +
                "<div id=\"partId\" class=\"openblock style two partRole\">\n" +
                "<div class=\"content\">\n" +
                "<div class=\"paragraph\">\n" +
                "<p>And content of second content part</p>\n" +
                "</div>\n" +
                "<div class=\"paragraph\">\n" +
                "<p>This block can be as long as you want.</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>";
        assertEquals(expectedOutPut, outPut);
    }

    @Test
    public void shouldParseAfterPreprocessor() {
        JavaExtensionRegistry extensionRegistry = asciidoctor.javaExtensionRegistry();

        extensionRegistry.preprocessor(ChangeAttributeValuePreprocessor.class);
        String outPut = asciidoctor.convert(":homepage: http://asciidoctor.org\n" +
                ":docslink: http://asciidoctor.org/docs[Asciidoctor's Docs]\n" +
                ":desc: Asciidoctor is a mature, plain-text document format for +\n" +
                "       writing notes, articles, documentation, books, and more. +\n" +
                "       It's also a text processor & toolchain for translating +\n" +
                "       documents into various output formats (i.e., backends), +\n" +
                "       including HTML, DocBook, PDF and ePub.\n" +
                ":checkedbox: pass:normal[`[&#10004;]`]\n" +
                "\n" +
                "Check out {homepage}[Asciidoctor]!\n" +
                "\n" +
                "{desc}\n" +
                "\n" +
                "Check out {docslink} too!\n" +
                "\n" +
                "{checkedbox} That's done!", new HashMap<>());
        System.out.println(outPut);
    }
}
