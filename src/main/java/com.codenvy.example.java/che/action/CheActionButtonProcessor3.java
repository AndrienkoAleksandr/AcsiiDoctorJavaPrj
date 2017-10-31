package com.codenvy.example.java.che.action;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

public class CheActionButtonProcessor3 extends InlineMacroProcessor {

    @Override
    public Object process(ContentNode parent, String target, Map<String, Object> attributes) {
        return "<input type=\"button\" class=\"quick-guide-action\" value=\"simple button\"/>";
    }
}
