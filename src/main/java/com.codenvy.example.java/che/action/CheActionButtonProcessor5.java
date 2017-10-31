package com.codenvy.example.java.che.action;

import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;

import java.util.Map;

public class CheActionButtonProcessor5 extends IncludeProcessor {

    @Override
    public boolean handles(String target) {
        return target.startsWith("[action");
    }

    @Override
    public void process(Document document, PreprocessorReader reader, String target, Map<String, Object> attributes) {
        String content = target;
//        reader.push_include(content.toString(), target, target, 1, attributes);
    }
}
