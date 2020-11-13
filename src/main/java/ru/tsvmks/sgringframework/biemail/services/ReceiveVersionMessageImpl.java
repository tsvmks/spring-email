package ru.tsvmks.sgringframework.biemail.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.Date;

@Component
public class ReceiveVersionMessageImpl {

    private String message;

    @Bean
    @ServiceActivator(inputChannel = "imapChannel")
    public MessageHandler processNewEmail() {
        System.out.println("...check messages");
        MessageHandler messageHandler = message -> {
            try {
                Message receivedMessage = (Message) message.getPayload();

                setEmail(receivedMessage);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        };
        return messageHandler;
    }

    private void setEmail(Message receivedMessage) throws Exception {
        String subject=receivedMessage.getSubject();
        String from= ((InternetAddress) receivedMessage.getFrom()[0]).getAddress();
        Date receivedDate= receivedMessage.getReceivedDate();

        message = subject;
        System.out.println(message);

    }
}
