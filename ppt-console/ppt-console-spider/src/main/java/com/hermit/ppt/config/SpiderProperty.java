package com.hermit.ppt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties("ppt.site")
public class SpiderProperty {

    private Map<String, WebSite> sites;


    @Data
    public static class WebSite {

        private String home;

        private String domain;

        private String schema;

        private String login;

        private String user;

        private String password;

        private String start;

        private Map<String,String> cookies;

        private Map<String,String> headers;


    }
}
