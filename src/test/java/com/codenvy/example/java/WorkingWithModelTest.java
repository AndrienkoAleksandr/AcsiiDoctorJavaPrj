package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.DocumentHeader;
import org.asciidoctor.ast.StructuredDocument;
import org.junit.Test;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.asciidoctor.OptionsBuilder.options;
import static org.junit.Assert.assertEquals;

public class WorkingWithModelTest {
    private static final String ASCIIDOC_HEADER = "= Sample Document\n"+
            "Doc Writer <doc.writer@asciidoc.org>; John Smith <john.smith@asciidoc.org>\n"+
            "v1.0, 2013-05-20: First draft\n"+
            ":title: Sample Document\n"+
            ":tags: [document, example]\n"+
            "\n"+
            "Preamble...";

    private static final String ASCII_STRUCTURE = "= Sample Document\n" +
            "\n" +
            "== Section one\n" +
            "This is content of section one\n" +
            "\n" +
            "== Section two\n" +
            "And content of section two\n";


    private Asciidoctor asciidoctor = create();

    @Test
    public void readHeader() {
        DocumentHeader header = asciidoctor.readDocumentHeader(ASCIIDOC_HEADER);

        assertEquals(header.getDocumentTitle().getMain(), "Sample Document");
        assertEquals(header.getAuthors().get(0).getFirstName(), "Doc");
        assertEquals(header.getAuthors().get(0).getLastName(), "Writer");

        assertEquals(header.getRevisionInfo().getDate(), "2013-05-20");
        assertEquals(header.getRevisionInfo().getNumber(), "1.0");
        assertEquals(header.getRevisionInfo().getRemark(), "First draft");
    }

//    @Test
//    public void readStructure() {
//        StructuredDocument structuredDocument = asciidoctor.readDocumentStructure(ASCII_STRUCTURE, options().asMap());
//        structuredDocument.
//    }
}
