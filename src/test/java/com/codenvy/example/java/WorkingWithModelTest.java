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

    private static final String ASCII_DOC_HEADER = "= Sample Document\n"+
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

    private static final String ASCII_DOC_WITH_STYLES = "= Sample Document\n" +
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

    private static final String ASCII_DOC_WITH_IMAGES = "[Images]\n" +
            "image::src/some{sp}image{sp}1.JPG[TODO title1,link=\"link1.html\"]\n" +
            "image::src/some{sp}image{sp}2.JPG[TODO title2,link=\"link2.html\"]";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldReadDocumentHeader() {
        DocumentHeader header = asciidoctor.readDocumentHeader(ASCII_DOC_HEADER);

        assertEquals("Sample Document", header.getDocumentTitle().getMain());
        assertEquals("Doc", header.getAuthors().get(0).getFirstName());
        assertEquals("Writer", header.getAuthors().get(0).getLastName());

        assertEquals("2013-05-20", header.getRevisionInfo().getDate());
        assertEquals("1.0", header.getRevisionInfo().getNumber());
        assertEquals("First draft", header.getRevisionInfo().getRemark());
    }

    @Test
    public void shouldReadStructure() {
        StructuredDocument structuredDocument = asciidoctor.readDocumentStructure(ASCII_STRUCTURE, options().asMap());
        
        ContentPart contentPart1 = structuredDocument.getParts().get(0);
        ContentPart contentPart2 = structuredDocument.getParts().get(1);
        
        assertEquals("Section one", contentPart1.getTitle());
        assertEquals("Section two", contentPart2.getTitle());
        
        assertEquals("<div class=\"paragraph\">\n" +
                "<p>This is content of section one</p>\n" +
                "</div>", contentPart1.getContent());
        assertEquals("<div class=\"paragraph\">\n" +
                "<p>And content of section two</p>\n" +
                "</div>", contentPart2.getContent());
    }

    @Test
    public void shouldGetPartsByStyle() {
        StructuredDocument document = asciidoctor.readDocumentStructure(ASCII_DOC_WITH_STYLES, options().asMap());
        ContentPart partStyleOne = document.getPartByStyle("style one");

        assertEquals("This is content of first content part", partStyleOne.getContent());

        assertEquals("<div class=\"paragraph\">\n" +
                "<p>And content of second content part</p>\n" +
                "</div>\n" +
                "<div class=\"paragraph\">\n" +
                "<p>This block can be as long as you want.</p>\n" +
                "</div>",
                document.getPartById("partId").getContent());

        String contentFromSecondPartsOfDoc = "<div class=\"paragraph\">\n" +
                "<p>And content of second content part</p>\n" +
                "</div>\n" +
                "<div class=\"paragraph\">\n" +
                "<p>This block can be as long as you want.</p>\n" +
                "</div>";

        assertEquals(contentFromSecondPartsOfDoc, document.getPartByRole("partRole").getContent());

        assertEquals(contentFromSecondPartsOfDoc, document.getPartByStyle("style two").getContent());
    }

    @Test
    public void shouldParseImage() {
        StructuredDocument document = asciidoctor.readDocumentStructure(ASCII_DOC_WITH_IMAGES, options().asMap());
        ContentPart imagePart = document.getPartsByContext("image").get(0);

        assertEquals("src/some image 1.JPG", imagePart.getAttributes().get("target"));
        assertEquals("TODO title1", imagePart.getAttributes().get("alt"));
        assertEquals("link1.html", imagePart.getAttributes().get("link"));
    }
}
