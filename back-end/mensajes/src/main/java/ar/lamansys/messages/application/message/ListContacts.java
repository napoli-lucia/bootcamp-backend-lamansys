package ar.lamansys.messages.application.message;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import ar.lamansys.messages.application.user.GetUserSession;
import ar.lamansys.messages.domain.message.MessageStoredBo;
import ar.lamansys.messages.infrastructure.output.LogUtil;
import ar.lamansys.messages.infrastructure.output.MessageStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ListContacts {
    private final GetUserSession getUserSession;
    private final MessageStorage messageStorage;

    public List<String> run() throws UserSessionNotExists {
        String sessionUserId = getUserSession.run();
        List<String> contacts = messageStorage.findByContact(sessionUserId)
                .map(extractContact(sessionUserId))
                .distinct()
                .collect(Collectors.toList());

        LogUtil.logResult(sessionUserId, contacts);
        return contacts;
    }

    private static Function<MessageStoredBo, String> extractContact(
            String sessionUserId
    ) {
        return m -> sessionUserId.equals(m.getOwnerId()) ?
                m.getTargetId() : m.getOwnerId();
    }
}
