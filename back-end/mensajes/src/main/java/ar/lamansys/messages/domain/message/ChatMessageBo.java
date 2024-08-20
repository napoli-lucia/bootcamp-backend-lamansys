package ar.lamansys.messages.domain.message;

import lombok.Value;

@Value
public class ChatMessageBo {
    boolean isSent;
    String text;
}
