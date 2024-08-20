package ar.lamansys.messages.application.message;

import java.util.List;
import java.util.stream.Collectors;

import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.GetUserSession;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.message.ChatMessageBo;
import ar.lamansys.messages.domain.message.MessageMapper;
import ar.lamansys.messages.infrastructure.output.LogUtil;
import ar.lamansys.messages.infrastructure.output.MessageStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FetchUserChat {
    private final GetUserSession getUserSession;
    private final MessageStorage messageStorage;
    private final AssertUserExists assertUserExists;

    public List<ChatMessageBo> run(String contactId) throws UserSessionNotExists, UserNotExistsException {
        String sessionUserId = getUserSession.run();

        assertUserExists.run(contactId);

        List<ChatMessageBo> chat = messageStorage.findBetween(
                    sessionUserId, contactId
                )
                .map(
                    m -> MessageMapper.buildChatMessageBo(sessionUserId, m)
                )
                .collect(Collectors.toList());

        LogUtil.logResult(sessionUserId, contactId, chat);
        return chat;
    }
}
