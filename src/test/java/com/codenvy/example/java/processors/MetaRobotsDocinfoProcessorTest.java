package com.codenvy.example.java.processors;

import com.codenvy.example.java.processors.MetaRobotsDocinfoProcessor;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.junit.Test;

import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.asciidoctor.OptionsBuilder.options;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MetaRobotsDocinfoProcessorTest {
    private static final String ASCII_DOC = "Writing Documentation using AsciiDoc\n" +
            "====================================\n" +
            "Joe Bloggs <jbloggs@mymail.com>\n" +
            "v2.0, February 2003:\n" +
            "Rewritten for version 2 release.";

    private Asciidoctor asciidoctor = create();

    @Test
    public void shouldParseContentWithoutTreeProcessor() {
        String outPut = asciidoctor.convert(ASCII_DOC, options().headerFooter(true).get());

        String expectedOutPut = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<!--[if IE]><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><![endif]-->\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<meta name=\"generator\" content=\"Asciidoctor 1.5.6.1\">\n" +
                "<meta name=\"author\" content=\"Joe Bloggs\">\n" +
                "<title>Writing Documentation using AsciiDoc</title>\n" +
                "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,400italic,600,600italic%7CNoto+Serif:400,400italic,700,700italic%7CDroid+Sans+Mono:400,700\">\n" +
                "<link rel=\"stylesheet\" href=\"./asciidoctor.css\">\n" +
                "</head>\n" +
                "<body class=\"article\">\n" +
                "<div id=\"header\">\n" +
                "<h1>Writing Documentation using AsciiDoc</h1>\n" +
                "<div class=\"details\">\n" +
                "<span id=\"author\" class=\"author\">Joe Bloggs</span><br>\n" +
                "<span id=\"email\" class=\"email\"><a href=\"mailto:jbloggs@mymail.com\">jbloggs@mymail.com</a></span><br>\n" +
                "<span id=\"revnumber\">version 2.0,</span>\n" +
                "<span id=\"revdate\">February 2003</span>\n" +
                "<br><span id=\"revremark\"></span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div id=\"content\">\n" +
                "<div class=\"paragraph\">\n" +
                "<p>Rewritten for version 2 release.</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div id=\"footer\">\n" +
                "<div id=\"footer-text\">\n" +
                "Version 2.0<br>\n";

        assertTrue(outPut.contains(expectedOutPut));
    }

    @Test
    public void shouldParseContentWithTreeProcessor() {
        JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();

        javaExtensionRegistry.docinfoProcessor(MetaRobotsDocinfoProcessor.class);

        String content = asciidoctor.convert(ASCII_DOC, options().headerFooter(true).get());

        String expectedContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<!--[if IE]><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><![endif]-->\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<meta name=\"generator\" content=\"Asciidoctor 1.5.6.1\">\n" +
                "<meta name=\"author\" content=\"Joe Bloggs\">\n" +
                "<title>Writing Documentation using AsciiDoc</title>\n" +
                "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,400italic,600,600italic%7CNoto+Serif:400,400italic,700,700italic%7CDroid+Sans+Mono:400,700\">\n" +
                "<link rel=\"stylesheet\" href=\"./asciidoctor.css\">\n" +
                "</head>\n" +
                "<body class=\"article\">\n" +
                "<div id=\"header\">\n" +
                "<h1>Writing Documentation using AsciiDoc</h1>\n" +
                "<div class=\"details\">\n" +
                "<span id=\"author\" class=\"author\">Joe Bloggs</span><br>\n" +
                "<span id=\"email\" class=\"email\"><a href=\"mailto:jbloggs@mymail.com\">jbloggs@mymail.com</a></span><br>\n" +
                "<span id=\"revnumber\">version 2.0,</span>\n" +
                "<span id=\"revdate\">February 2003</span>\n" +
                "<br><span id=\"revremark\"></span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div id=\"content\">\n" +
                "<div class=\"paragraph\">\n" +
                "<p>Rewritten for version 2 release.</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div id=\"footer\">\n" +
                "<div id=\"footer-text\">\n" +
                "Version 2.0<br>\n";

        assertTrue(content.contains(expectedContent));
    }
}
