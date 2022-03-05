package org.storage.metadata.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.storage.metadata.service.UserAuthService;

import java.io.File;
import java.io.IOException;

@Service
public class ResponseUtil {
    public JsonNode readJsonFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(new File("src/main/resources/static/messages.json"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResponseMessage(UserAuthService.USER_AUTH_ACTION action) {
        JsonNode rootNode = readJsonFile();
        if(rootNode != null) {
            return rootNode.path(String.valueOf(action).toLowerCase()).get("message").asText();
        }
        return "";
    }
}
