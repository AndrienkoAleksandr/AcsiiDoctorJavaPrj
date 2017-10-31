package com.codenvy.example.java.che.action;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;
import org.eclipse.che.api.factory.shared.dto.IdeActionDto;
import org.eclipse.che.dto.server.DtoFactory;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Custom ASCIIDoc processor. This component uses for parsing IDE action syntax.
 * asciidoctorj parser uses this processor to parse founded action button syntax fragments.
 * Like result this processor returns html action button
 * and save parsed action to accumulate all actions from the document.
 * Action button should has mapping with IDE action by id.
 *
 * Todo: store parsed action to accumulate all parsed actions from asciidoc document.
 * Todo: Define id generation for mapping "html action button" and "IDE action".
 *
 */
public class CheActionButtonProcessor extends InlineMacroProcessor {

    private static final String BUTTON_LABEL_ATTR = "label";

    @Override
    public Object process(ContentNode parent, String target, Map<String, Object> attributes) {
        String actionId = target;

        //get IDE action properties
        Map<String, String> properties = attributes.entrySet()
                                                   .stream()
                                                   .filter(elem -> elem.getValue() instanceof String)
                                                   .collect(toMap(Map.Entry::getKey, elem -> (String)elem.getValue()));

        //create action by parsed meta information.
        // TODO: In the full implementation we should accumulate parsed actions.
        // When parsing will be completed we should send json with parsed content and list parsed IDE actions to the client side.
        IdeActionDto parsedAction = DtoFactory.getInstance().createDto(IdeActionDto.class).withId(actionId).withProperties(properties);

        String buttonText = properties.get(BUTTON_LABEL_ATTR);

        // Generate action button html representation. Todo: in the full implementation we can generate for this button unique Id to mapping with parsed action.
        // When user click action button in the client side we can find IDE action by this Id and launch this action.
        return "<input type=\"button\" id=\"some_id_for_mapping\" class=\"quick-guide-action\" value=\"" + buttonText + "\"/>";
    }
}
