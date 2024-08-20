package ar.lamansys.messages.domain.message;

import lombok.Value;

@Value
public class NewMessageBo {
    String targetId;
    String text;
}
