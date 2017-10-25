package com.codenvy.example.java;

import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.DocinfoProcessor;

import java.util.Map;

public class MetaRobotsDocinfoProcessor extends DocinfoProcessor {

    public MetaRobotsDocinfoProcessor() {
        super();
    }

    public MetaRobotsDocinfoProcessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public String process(Document document) {
        return "<meta name=\"robots\" content=\"index,follow\">";
    }
}
