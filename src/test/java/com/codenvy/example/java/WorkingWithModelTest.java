package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.ContentPart;
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

        assertEquals("Sample Document", header.getDocumentTitle().getMain());
        assertEquals("Doc", header.getAuthors().get(0).getFirstName());
        assertEquals("Writer", header.getAuthors().get(0).getLastName());

        assertEquals("2013-05-20", header.getRevisionInfo().getDate());
        assertEquals("1.0", header.getRevisionInfo().getNumber());
        assertEquals("First draft", header.getRevisionInfo().getRemark());
    }

    @Test
    public void readStructure() {
        StructuredDocument structuredDocument = asciidoctor.readDocumentStructure(ASCII_STRUCTURE, options().asMap());
        
        ContentPart contentPart1 = structuredDocument.getParts().get(0);
        ContentPart contentPart2 = structuredDocument.getParts().get(1);
        
        assertEquals("Section one", contentPart1.getTitle());
        assertEquals("Section two", contentPart2.getTitle());
        
//        assertEquals("<div class=\"paragraph\"><p>This is content of section one</p></div>", contentPart1.getContent());
//        assertEquals("And content of section two\n", contentPart2.getContent());
    }
}
