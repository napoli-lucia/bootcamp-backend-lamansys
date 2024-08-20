package ar.lamansys.messages.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import ar.lamansys.messages.application.message.ListContacts;
import ar.lamansys.messages.application.user.GetUserSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ar.lamansys.messages.application.user.exception.UserSessionNotExists;
import ar.lamansys.messages.domain.message.MessageStoredBo;
import ar.lamansys.messages.infrastructure.output.MessageStorage;

class ListContactsTest {

    private final GetUserSession getUserSession =
            Mockito.mock(GetUserSession.class);

    private final MessageStorage messageStorage =
            Mockito.mock(MessageStorage.class);

    private ListContacts listContacts = new ListContacts(
            getUserSession,
            messageStorage
    );

    @Test
    void listContactsWithoutUserSession_throwsException() throws UserSessionNotExists {
        //Arrange
        Mockito.doThrow(new UserSessionNotExists())
                .when(getUserSession).run();

        //Act and Assert
        assertThrows(UserSessionNotExists.class, () -> listContacts.run());
    }

    @Test
    void listContactsWithUserSession_ok() throws UserSessionNotExists {
        //Arrange
        Mockito.when(getUserSession.run()).thenReturn("userId");
        //Act and Assert
        assertDoesNotThrow(() -> listContacts.run());
    }

    @Test
    void listContactsWithUserSession_getContacts() throws UserSessionNotExists {
        //Arrange
        var contacts = List.of(
                "pepito",
                "cantropus"
        );

        var messages = List.of(
                new MessageStoredBo("userId", "pepito", "hola"),
                new MessageStoredBo("pepito", "userId", "hola"),
                new MessageStoredBo("userId", "cantropus", "hola"),
                new MessageStoredBo("cantropus", "userId", "hola")
        ).stream();

        Mockito.when(getUserSession.run()).thenReturn("userId");
        Mockito.when(messageStorage.findByContact("userId"))
                .thenReturn(messages);

        //Act
        var result = listContacts.run();
        //Assert
        assertEquals(contacts, result, "La lista de contactos no son iguales");
    }
}