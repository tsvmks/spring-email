package ru.tsvmks.sgringframework.biemail.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsvmks.sgringframework.biemail.model.Version;

import javax.mail.Message;
import javax.mail.MessagingException;

@Service
public class EmailConverter {
    @Autowired
    private org.springframework.context.ApplicationContext applicationContext;

    public void versionFromEmail(Message message){
        String versionName="";
        try {
            String subject = message.getSubject();
            if (subject.toLowerCase().contains("выпущена версия")) {
                versionName = subject;
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Version version = (Version) applicationContext.getBean("versionSingleton");

        version.setVersion(versionName);
    }
}
