package com.codenvy.example.java.app;

import org.asciidoctor.Asciidoctor;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import static org.asciidoctor.Asciidoctor.Factory.create;

public class Application {

    private static final Asciidoctor asciidoctor = create();

    public static void main(String[] args) {
        URL adocUrl = Application.class.getClassLoader().getResource("sample.adoc");

        if (adocUrl != null) {
            File adocFile = new File(adocUrl.getFile());
            if (adocFile.exists()) {
                String outPut = asciidoctor.convertFile(adocFile, new HashMap<>());
            }

        }
    }

}
