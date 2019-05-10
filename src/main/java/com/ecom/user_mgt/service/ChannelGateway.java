package com.ecom.user_mgt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ChannelGateway {

    @Autowired
    private Source outputPipe;

    public void publishToService(Map msg) {
        log.info("Publishing Msg..");
        outputPipe.output().send(MessageBuilder.withPayload(msg).build());
    }
}
