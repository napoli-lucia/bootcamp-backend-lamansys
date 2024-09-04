package ar.lamansys.messages.infrastructure.output.impl;

import ar.lamansys.messages.domain.user.AppUserBo;
import ar.lamansys.messages.infrastructure.output.UserStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Repository
public class UserStorageSetImpl implements UserStorage {

    private Set<String> users = new HashSet<>();
    private String filePath = "users.txt";

    public UserStorageSetImpl() {
        loadFromFile();
    }
    private void saveToFile() {
        Gson gson = new Gson();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            String json = gson.toJson(users);
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
                    Type setType = new TypeToken<Set<String>>(){}.getType();
                    Set<String> loadedUsers = gson.fromJson(json, setType);
                    users.clear();
                    if( loadedUsers != null)
                        users.addAll(loadedUsers);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(AppUserBo user) {
        users.add(user.getId());
        saveToFile();
    }

    @Override
    public boolean exists(String user) {
        return users.contains(user);
    }

    @Override
    public void deleteAll() {
        users.clear();
        saveToFile();
    }
}
