package com.codenvy.example.java.che.action;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.junit.Assert.assertEquals;

public class CheActionButtonProcessorTest {

    private static final String ASCII_DOC = "Che action processor\n" +
            "-------------------\n" +
            "\n" +
            "[che-action]\n" +
            "id::open-file\n" +
            "label::Open Test.java in the line 30\n" +
            "path::/prj/src/main/java/Test.java\n" +
            "line::30\n";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, new HashMap<>());

        String expectedOutPut = "<div class=\"sect1\">\n" +
                "<h2 id=\"_che_action_processor\">Che action processor</h2>\n" +
                "<div class=\"sectionbody\">\n" +
                "<div class=\"paragraph\">\n" +
                "<p>id::open-file\n" +
                "label::Open Test.java in the line 30\n" +
                "path::/prj/src/main/java/Test.java\n" +
                "line::30</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>";

        assertEquals(outPut, expectedOutPut);
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {
        JavaExtensionRegistry extensionRegistry = this.asciidoctor.javaExtensionRegistry();

        extensionRegistry.block("che-action", CheActionButtonProcessor.class);

        String content = asciidoctor.convert(ASCII_DOC, new Options());

        System.out.println(content);
        String expectedContent = "<div class=\"sect1\">\n" +
                "<h2 id=\"_che_action_processor\">Che action processor</h2>\n" +
                "<div class=\"sectionbody\">\n" +
                "<input type=\"button\" class=\"quick-guide-action\" value=\"simple button\"/>\n" +
                "</div>\n" +
                "</div>";

        assertEquals(expectedContent, content);
    }
}
