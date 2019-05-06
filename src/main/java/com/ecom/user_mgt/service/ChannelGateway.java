package com.ecom.user_mgt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChannelGateway {

    @Autowired
    private Source outputPipe;

    public void publishToService(String msg) {
        outputPipe.output().send(MessageBuilder.withPayload(msg).build());
    }
}
