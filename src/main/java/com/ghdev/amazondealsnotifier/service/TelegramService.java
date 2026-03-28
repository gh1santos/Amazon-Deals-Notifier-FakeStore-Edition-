package com.ghdev.amazondealsnotifier.service;

import com.ghdev.amazondealsnotifier.config.TelegramConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramService {

    private final TelegramConfig telegramConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    public TelegramService(TelegramConfig telegramConfig) {
        this.telegramConfig = telegramConfig;
    }

    public void sendMessage(String message) {

        String url = "https://api.telegram.org/bot"
                + telegramConfig.getBotToken()
                + "/sendMessage";

        Map<String, String> body = new HashMap<>();
        body.put("chat_id", telegramConfig.getChatId());
        body.put("text", message);

        restTemplate.postForObject(url, body, String.class);
    }

    public void sendPhoto(String photoUrl, String caption) {

        String url = "https://api.telegram.org/bot"
                + telegramConfig.getBotToken()
                + "/sendPhoto";

        Map<String, String> body = new HashMap<>();
        body.put("chat_id", telegramConfig.getChatId());
        body.put("photo", photoUrl);
        body.put("caption", caption);

        restTemplate.postForObject(url, body, String.class);
    }
}