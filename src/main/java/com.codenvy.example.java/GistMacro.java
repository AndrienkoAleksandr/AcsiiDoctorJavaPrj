package com.codenvy.example.java;

import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.BlockMacroProcessor;

import java.util.Arrays;
import java.util.Map;

public class GistMacro extends BlockMacroProcessor {

    public GistMacro(String macroName, Map<String, Object> config) {
        super(macroName, config);
    }

    @Override
    public Object process(StructuralNode parent, String target, Map<String, Object> attributes) {
        String content = "<div class=\"content\">\n" +
                "<script src=\"https://gist.github.com/"+target+".js\"></script>\n" +
                "</div>";

        return createBlock(parent, "pass", Arrays.asList(content), attributes);
    }
}
