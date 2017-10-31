package com.codenvy.example.java.app;

import org.asciidoctor.Asciidoctor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;

public class Application {

    private static final Asciidoctor asciidoctor = create();

    public static void main(String[] args) throws IOException {
        URL adocUrl = Application.class.getClassLoader().getResource("sample.adoc");

        if (adocUrl != null) {
            File adocFile = new File(adocUrl.getFile());
            if (adocFile.exists()) {
                String outPut = asciidoctor.convertFile(adocFile, new HashMap<>());

                String content = new String(Files.readAllBytes(adocFile.toPath()));

                long begin = System.currentTimeMillis();
                asciidoctor.convert(content, new HashMap<>());
                long timeDiff = System.currentTimeMillis() - begin;
                System.out.println(timeDiff);
            }

        }
    }

}
