package ar.lamansys.messages.infrastructure.output.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.lamansys.messages.domain.message.MessageStoredBo;
import ar.lamansys.messages.infrastructure.output.MessageStorage;
import org.springframework.stereotype.Repository;

@Repository
public class MessageStorageListImpl implements MessageStorage {

    private List<MessageStoredBo> messages = new ArrayList<>();
    private String filePath = "messages.txt";

    public MessageStorageListImpl() {
        loadFromFile();
    }

    private void saveToFile() {
        Gson gson = new Gson();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            String json = gson.toJson(messages);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        Gson gson = new Gson();
        if (Files.exists(Paths.get(filePath))) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
                String json = reader.readLine();
                if (json != null) {
                    Type listType = new TypeToken<List<MessageStoredBo>>(){}.getType();
                    List<MessageStoredBo> loadedMessages = gson.fromJson(reader, listType);
                    messages.clear();
                    if( loadedMessages != null)
                        messages.addAll(loadedMessages);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void save(MessageStoredBo message) {
        messages.add(message);
        saveToFile();
    }

    @Override
    public Stream<MessageStoredBo> findByContact(String userId) {
        return messages.stream()
                .filter(ownerIdOrTargetIdIs(userId));
    }

    private static Predicate<MessageStoredBo> ownerIdOrTargetIdIs(String userId) {
        return m -> userId.equals(m.getOwnerId()) || userId.equals(m.getTargetId());
    }

    @Override
    public Stream<MessageStoredBo> findBetween(
        String oneContact,
        String otherContact
    ) {
        return messages.stream()
                .filter(isBetween(oneContact, otherContact));
    }

    @Override
    public void deleteAll() {
        messages.clear();
        saveToFile();
    }

    private static Predicate<MessageStoredBo> isBetween(
            String sessionUserId,
            String contactId
    ) {
        return sentFromTo(sessionUserId, contactId)
                .or(sentFromTo(contactId, sessionUserId));
    }

    private static Predicate<MessageStoredBo> sentFromTo(
            String ownerId,
            String targetId
    ) {
        return message ->
                message.getOwnerId().equals(ownerId)
                && message.getTargetId().equals(targetId);
    }
}
