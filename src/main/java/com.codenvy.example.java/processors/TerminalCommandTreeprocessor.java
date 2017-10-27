package com.codenvy.example.java.processors;

import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.Treeprocessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerminalCommandTreeprocessor extends Treeprocessor {

    private Document document;

    public TerminalCommandTreeprocessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public Document process(Document document) {

        this.document = document;

        final List<StructuralNode> blocks = this.document.getBlocks();

        for (int i = 0; i < blocks.size(); i++) {
            final StructuralNode currentBlock = blocks.get(i);
            if(currentBlock instanceof Block) {
                Block block = (Block)currentBlock;
                List<String> lines = block.getLines();
                if (lines.size() > 0 && lines.get(0).startsWith("$")) {
                    blocks.set(i, convertToTerminalListing(block));
                }
            }
        }

        return this.document;
    }

    public Block convertToTerminalListing(Block block) {

        Map<String, Object> attributes = block.getAttributes();
        attributes.put("role", "terminal");
        StringBuilder resultLines = new StringBuilder();

        List<String> lines = block.getLines();

        for (String line : lines) {
            if (line.startsWith("$")) {
                resultLines.append("<span class=\"command\">")
                        .append(line.substring(2, line.length()))
                        .append("</span>");
            }
            else {
                resultLines.append(line);
            }
        }

        return createBlock(this.document, "listing", Arrays.asList(resultLines.toString()), attributes, new HashMap<>());
    }

}
