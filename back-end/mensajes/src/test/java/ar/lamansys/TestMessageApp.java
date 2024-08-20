package ar.lamansys;

import java.util.List;

import ar.lamansys.messages.application.user.AddUser;
import ar.lamansys.messages.application.ClearData;
import ar.lamansys.messages.application.message.FetchUserChat;
import ar.lamansys.messages.application.user.GetUserSession;
import ar.lamansys.messages.application.message.ListContacts;
import ar.lamansys.messages.application.message.SendUserMessage;
import ar.lamansys.messages.application.user.SetUserSession;
import ar.lamansys.messages.application.user.exception.UserExistsException;
import ar.lamansys.messages.application.user.exception.UserNotExistsException;
import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.message.ChatMessageBo;
import ar.lamansys.messages.domain.message.NewMessageBo;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class TestMessageApp {
    private final AddUser addUser;
    private final ClearData clearData;
    private final FetchUserChat fetchUserChat;
    private final GetUserSession getUserSession;
    private final ListContacts listContacts;
    private final SendUserMessage sendUserMessage;
    private final SetUserSession setUserSession;

    public void sendUserMessage(NewMessageBo newMessage) throws UserNotExistsException, UserSessionNotExists {
        sendUserMessage.run(newMessage);
    }

    public List<ChatMessageBo> fetchUserChat(String contactId) throws UserSessionNotExists, UserNotExistsException {
        return fetchUserChat.run(contactId);
    }

    public List<String> listContacts() throws UserSessionNotExists {
        return listContacts.run();
    }

    public void addUser(String userId) throws UserExistsException {
        addUser.run(userId);
    }

    public void clearData() {
        clearData.run();
    }

    public String getUserSession() throws UserSessionNotExists {
        return getUserSession.run();
    }

    public void setUserSession(String userId) throws UserNotExistsException {
        setUserSession.run(userId);
    }
}
