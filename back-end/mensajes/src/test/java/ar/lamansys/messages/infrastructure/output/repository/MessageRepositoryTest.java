package ar.lamansys.messages.infrastructure.output.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ar.lamansys.messages.domain.message.MessageStoredBo;
import ar.lamansys.messages.infrastructure.output.entity.Message;

@DataJpaTest(showSql = false)
@EnableJpaRepositories(basePackages = {"ar.lamansys.messages.infrastructure.output"})
@EntityScan(basePackages = {"ar.lamansys.messages.infrastructure.output"})
class MessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    // Formato methodName_state_expectedBehavior: Este formato ayuda a entender qué método
    // se está probando, en qué estado y cuál es el comportamiento esperado.
    @Test
    void findAllBetweenTwoContacts_empty_shouldBeEmpty() {
        // AssertJ una librería que nos permitirá realizar los assert de una forma mucho
        // más integrada con el código y soportando sintaxis fluida e intellisense por
        // parte del IDE de desarrollo
        assertThat(
                messageRepository.findAllBetweenTwoContacts("a", "b")
        ).size().isEqualTo(0);
    }

    @Test
    void findAllBetweenTwoContacts_withTwoMessages_shouldFindOne() {
        messageRepository.save(newMessage("a", "b"));
        messageRepository.save(newMessage("a", "c"));
        assertThat(
                messageRepository.findAllBetweenTwoContacts("a", "b")
        ).size().isEqualTo(1);
        assertThat(
                messageRepository.findAllBetweenTwoContacts("b", "a")
        ).size().isEqualTo(1);

        assertThat(
                messageRepository.findAllBetweenTwoContacts("a", "c")
        ).size().isEqualTo(1);
        assertThat(
                messageRepository.findAllBetweenTwoContacts("c", "a")
        ).size().isEqualTo(1);

        assertThat(
                messageRepository.findAllBetweenTwoContacts("b", "c")
        ).size().isEqualTo(0);
        assertThat(
                messageRepository.findAllBetweenTwoContacts("c", "b")
        ).size().isEqualTo(0);
    }

    private static Message newMessage(String ownerId, String targetId) {
        return new Message(new MessageStoredBo(
                ownerId,
                targetId,
                "hola"
        ));
    }
}