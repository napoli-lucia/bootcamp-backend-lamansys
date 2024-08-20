package ar.lamansys;

import ar.lamansys.messages.application.user.AddUser;
import ar.lamansys.messages.application.user.AssertUserExists;
import ar.lamansys.messages.application.user.AssertUserNotExists;
import ar.lamansys.messages.application.ClearData;
import ar.lamansys.messages.application.message.FetchUserChat;
import ar.lamansys.messages.application.user.GetUserSession;
import ar.lamansys.messages.application.message.ListContacts;
import ar.lamansys.messages.application.message.SendUserMessage;
import ar.lamansys.messages.application.user.SetUserSession;
import ar.lamansys.messages.infrastructure.output.impl.MessageStorageListImpl;
import ar.lamansys.messages.infrastructure.output.impl.UserSessionStorageImpl;
import ar.lamansys.messages.infrastructure.output.impl.UserStorageSetImpl;

public class MessageAppConfiguration {


    public static TestMessageApp newBean() {
        var messageStorage = new MessageStorageListImpl();
        var userSessionStorage = new UserSessionStorageImpl();
        var userStorage =new UserStorageSetImpl();

        var assertUserExists = new AssertUserExists(userStorage);
        var assertUserNotExists = new AssertUserNotExists(userStorage);

        AddUser addUser = new AddUser(userStorage, assertUserNotExists);
        GetUserSession getUserSession = new GetUserSession(userSessionStorage);
        ClearData clearData = new ClearData(messageStorage, userStorage, userSessionStorage);
        FetchUserChat fetchUserChat = new FetchUserChat(getUserSession, messageStorage, assertUserExists);
        ListContacts listContacts = new ListContacts(getUserSession, messageStorage);
        SendUserMessage sendUserMessage = new SendUserMessage(getUserSession, messageStorage, assertUserExists);
        SetUserSession setUserSession = new SetUserSession(userSessionStorage, assertUserExists);

        return new TestMessageApp(
                addUser,
                clearData,
                fetchUserChat,
                getUserSession,
                listContacts,
                sendUserMessage,
                setUserSession
        );
    }
}

