package com.ecom.user_mgt.service;

import com.ecom.user_mgt.utils.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.TestSupportBinder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ChannelGatewayTest {

    @Autowired
    private ChannelGateway channelGateway;

    @Autowired
    private BinderFactory binderFactory;

    @Autowired
    private Source source;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void publishToService() throws JsonProcessingException {
        Map<String, String> message = new HashMap<String, String>() {{
            put("message", AppConstants.USER_CREATED);
        }};

        channelGateway.publishToService(message);

        Message<String> received = (Message<String>) ((TestSupportBinder) this.binderFactory
                .getBinder(null, MessageChannel.class)).messageCollector()
                .forChannel(this.source.output()).poll();


        assert received != null;
        assertEquals(Objects.requireNonNull(received.getHeaders().get(MessageHeaders.CONTENT_TYPE)).toString(), "application/json");
        assertEquals(objectMapper.writeValueAsString(message), received.getPayload());
    }

}
