package ru.tsvmks.sgringframework.biemail.services;

import org.springframework.messaging.MessageHandler;

/**
 * @author tsvetkov
 */
public interface ReceiveVersionMessage {

    String processNewEmail();
}
