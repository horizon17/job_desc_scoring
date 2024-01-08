package com.alexxwhite.jdscoring;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class EMLReader {

    public EmailDTO readFileByName(String filePath) throws Exception {
        EmailDTO emailDTO = new EmailDTO();

        // Read and parse the EML file
        Session session = Session.getDefaultInstance(new Properties());
        InputStream is = new FileInputStream(filePath);
        MimeMessage message = new MimeMessage(session, is);

        emailDTO.setFileName(filePath);
        emailDTO.setSubject(message.getSubject());
        if (message.getFrom() == null || message.getFrom().length == 0) {
            System.out.println("error with message.getFrom() " + message);
            System.out.println("---> filePath " + filePath);
            return null;
        }
        emailDTO.setFrom(message.getFrom()[0].toString());
        emailDTO.setTo(message.getHeader("Delivered-To")[0]);
        emailDTO.setWholeBody(getTextFromMessage(message));

        return emailDTO;
    }

    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")) {
            return (String) message.getContent();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
                break; // without break, it will loop through all parts (including attachments)
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append(html);
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }

}
