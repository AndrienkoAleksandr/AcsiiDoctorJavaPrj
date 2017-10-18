package com.codenvy.example.java;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import static org.asciidoctor.Asciidoctor.Factory.create;
import static org.asciidoctor.OptionsBuilder.options;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AsciiDoctorBaseUsageTest {

    private static final String docContent = "Welcome to AsciiDocLIVE!\n" +
            "------------------------\n" +
            "\n" +
            "AsciiDocLIVE is a *free online http://www.methods.co.nz/asciidoc/[AsciiDoc^]\n" +
            "editor*.\n" +
            "\n" +
            "* Just type AsciiDoc source text into the *left* pane,\n" +
            "* ...and the live preview appears in the *right* pane!";

    private Asciidoctor asciidoctor = create();

    @Test
    public void getAsciiDoctorVersion() {
        assertEquals(asciidoctor.asciidoctorVersion(), "1.5.6.1");
    }

    @Test
    public void shouldConvertDocFromFile() throws Exception {
        String expectedResult = "<div class=\"paragraph\">\n" +
                "<p>Writing AsciiDoc is <em>easy</em>!</p>\n" +
                "</div>";

        URL resUrl = AsciiDoctorBaseUsageTest.class.getClassLoader().getResource("sample.adoc");

        if (resUrl == null) {
            fail("failed to get file with test content");
        }

        File contentFile = new File(resUrl.getPath());
        String outfile = asciidoctor.convertFile(contentFile, OptionsBuilder.options().toFile(false).inPlace(false));

        assertEquals(expectedResult, outfile);
    }

    @Test
    public void shouldConvertWithHelpFileReader() throws Exception {
        String expectedResult = "<div class=\"paragraph\">\n" +
                "<p>Writing AsciiDoc is <em>easy</em>!</p>\n" +
                "</div>";

        URL resUrl = AsciiDoctorBaseUsageTest.class.getClassLoader().getResource("sample.adoc");

        if (resUrl == null) {
            fail("failed to get file with test content");
        }

        File contentFile = new File(resUrl.getPath());

        FileReader reader = new FileReader(contentFile);
        StringWriter writer = new StringWriter();

        asciidoctor.convert(reader, writer, options().asMap());
        StringBuffer htmlBuffer = writer.getBuffer();

        assertEquals(expectedResult, htmlBuffer.toString());
    }

    @Test
    public void shouldConvertDocFromString() {
        String content = "Writing AsciiDoc is _easy_!";
        String expectedResult = "<div class=\"paragraph\">\n" +
                "<p>Writing AsciiDoc is <em>easy</em>!</p>\n" +
                "</div>";

        String html = asciidoctor.convert(content, new HashMap<>());

        assertEquals(expectedResult, html);
    }
}
