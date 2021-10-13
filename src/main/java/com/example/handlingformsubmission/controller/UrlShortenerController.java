package com.example.handlingformsubmission.controller;

import com.example.handlingformsubmission.entity.Url;
import com.example.handlingformsubmission.service.UrlShortenerService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UrlShortenerController {

    private UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/url-shortener")
    public String getIndex(Model model) {
        model.addAttribute("url", new Url());
        return "index";
    }

    @PostMapping("/url-shortener")
    public String createShortUrl(@ModelAttribute Url url, Model model) {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (urlValidator.isValid(url.getFullUrl())) {
            String shortUrl = urlShortenerService.createShortUrl(url);
            model.addAttribute("shortUrl", shortUrl);
        } else {
            return "badInput400";
        }
        return "shortUrlResponse";
    }

    @GetMapping("/short-url/{alias}")
    public String redirectToFullURL(@PathVariable String alias, Model model) {
        String redirectUrl = urlShortenerService.redirectToFullURL(alias);
        if (redirectUrl == null) {
            return "badInput400";
        }
        System.out.println(redirectUrl);
        model.addAttribute("redirectUrl", redirectUrl);
        return "redirect";
    }

}
