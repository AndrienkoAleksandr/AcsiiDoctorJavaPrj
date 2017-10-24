package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class JRubyWrapperTest {

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
    public void shouldGetDocTitle() {
        Document document = asciidoctor.load(ASCII_DOC_HEADER, new HashMap<>());
        assertEquals(document.getDoctitle(), "Sample Document");
    }

    @Test
    public void shouldGetSection() {
        Document document = asciidoctor.load(ASCII_DOC_HEADER, new HashMap<>());
        StructuralNode node = document.blocks().get(0);

        assertEquals(node.getContent(), "This is content of first content part");
    }
}
