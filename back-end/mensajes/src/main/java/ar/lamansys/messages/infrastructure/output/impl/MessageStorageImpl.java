package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.message.MessageStoredBo;
import ar.lamansys.messages.infrastructure.output.repository.MessageRepository;
import ar.lamansys.messages.infrastructure.output.MessageStorage;
import ar.lamansys.messages.infrastructure.output.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Primary
@RequiredArgsConstructor
@Service
public class MessageStorageImpl implements MessageStorage {

    private final MessageRepository messageRepository;

    @Override
    public void save(MessageStoredBo message) {
        messageRepository.save(new Message(message));
    }

    @Override
    public Stream<MessageStoredBo> findBetween(String oneContact, String otherContact) {
        return messageRepository.findAllBetweenTwoContacts(oneContact, otherContact);
    }

    @Override
    public Stream<MessageStoredBo> findByContact(String userId) {
        return messageRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteAll() {
        messageRepository.deleteAll();
    }

}
