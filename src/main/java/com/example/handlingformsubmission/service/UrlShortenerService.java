package com.example.handlingformsubmission.service;

import com.example.handlingformsubmission.entity.Url;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class UrlShortenerService {

    private static Map<String, String> urlDB;

    static {
        urlDB = new HashMap<>();
    }


    public String createShortUrl(Url url) {
        if (url.getAlias().equals("")) {
            String alias = Hashing.murmur3_32_fixed().hashString(url.getFullUrl(), StandardCharsets.UTF_8).toString();
            url.setAlias(alias);
        }
        urlDB.put(url.getAlias(), url.getFullUrl());
        return "http://localhost:8083/short-url/" + url.getAlias();

    }

    public String redirectToFullURL(String alias) {
        if (urlDB.containsKey(alias)) {
            return urlDB.get(alias);
        }
        return null;
    }
}
