package com.ecom.user_mgt.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

public class Utility {

    public static <T> HttpEntity<T> getHttpEntity(T clazz) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        if(clazz != null)
            return new HttpEntity<>(clazz, requestHeaders);
        return new HttpEntity<>(requestHeaders);
    }

    public static String createURLWithPort(String uri, Integer port) {
        return String.format("http://localhost:%s/ecom-user/%s", port, uri);
    }
}
