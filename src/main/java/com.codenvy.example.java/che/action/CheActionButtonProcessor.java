package com.codenvy.example.java.che.action;

import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.BlockProcessor;
import org.asciidoctor.extension.Reader;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

public class CheActionButtonProcessor extends BlockProcessor {

    @Override
    public Object process(StructuralNode parent, Reader reader, Map<String, Object> attributes) {
        String content = "<input type=\"button\" class=\"quick-guide-action\" value=\"simple button\"/>";
        if (attributes.containsKey("che-action")) {
            System.out.println("yes");
        }

        return createBlock(parent, "pass", singletonList(content), attributes, new HashMap<>());
    }
}
