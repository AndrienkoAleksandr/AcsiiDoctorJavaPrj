package com.codenvy.example.java;

import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.BlockProcessor;
import org.asciidoctor.extension.Reader;

import java.util.*;

public class YellBlock extends BlockProcessor {

    public YellBlock(String name, Map<String, Object> config) {
        super(name, config);
    }

    @Override
    public Object process(StructuralNode parent, Reader reader, Map<String, Object> attributes) {
        List<String> lines = reader.readLines();
        StringBuilder upperLines = null;
        for (String line : lines) {
            if (upperLines == null) {
                upperLines = new StringBuilder(line.toUpperCase());
            }
            else {
                upperLines.append("\n").append(line.toUpperCase());
            }
        }

        return createBlock(parent, "paragraph", Collections.singletonList(upperLines.toString()), attributes, new HashMap<>());
    }
}
