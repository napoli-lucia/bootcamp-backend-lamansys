package ar.lamansys.messages.domain.message;

public class MessageMapper {
    private MessageMapper() {}
    public static MessageStoredBo buildMessage(
            String ownerId,
            NewMessageBo newMessage
    ) {
        return new MessageStoredBo(
                ownerId,
                newMessage.getTargetId(),
                newMessage.getText()
        );
    }

    public static ChatMessageBo buildChatMessageBo(
            String sessionUserId,
            MessageStoredBo message
    ) {
        return new ChatMessageBo(
                sessionUserId.equals(message.getOwnerId()),
                message.getText()
        );
    }
}
