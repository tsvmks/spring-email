package ru.tsvmks.sgringframework.biemail.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.integration.mail.support.DefaultMailHeaderMapper;
import org.springframework.integration.mapping.HeaderMapper;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;
import ru.tsvmks.sgringframework.biemail.model.Version;
import ru.tsvmks.sgringframework.biemail.services.EmailConverter;

import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@EnableIntegration
@Configuration
public class ListeningExample {

    @Autowired
    private EmailConverter converter;

    private String email_mail = "****@mail.ru";
    private String password_mail = "*****";

    @Bean
    public HeaderMapper<MimeMessage> mailHeaderMapper() {
        return new DefaultMailHeaderMapper();
    }

    @Bean
    public IntegrationFlow imapMailFlow() throws UnsupportedEncodingException {
        String url = "imaps://"
                + URLEncoder.encode(this.email_mail, StandardCharsets.UTF_8.toString())
                + ":"
                + URLEncoder.encode(this.password_mail, StandardCharsets.UTF_8.toString())
                + "@imap.mail.ru:993/INBOX";
        IntegrationFlow flow = IntegrationFlows
                .from(Mail.imapInboundAdapter(url)
                                .userFlag("testSIUserFlag")
                                .javaMailProperties(p -> p.put("mail.debug", "false")
                                        .put("mail.imaps.ssl.trust", "*"))
                        ,
                        e -> e.autoStartup(true)
                                .poller(p -> p.fixedDelay(10000)))
                .log(LoggingHandler.Level.INFO, "test.category", m -> m.getHeaders().getId())
                .handle(converter, "versionFromEmail")
                .channel(MessageChannels.queue("imapChannel1"))
                .get();
        return flow;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(1000));
        return pollerMetadata;
    }

    @Bean
    @Scope("singleton")
    public Version versionSingleton() {
        return new Version();
    }
}
