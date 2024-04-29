package com.authentifcation.projectpitwo.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailUtil {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private JavaMailSenderImpl mailSender;

    private final SpringTemplateEngine templateEngine;

    public void SendSimpleMessage(String to , String subject , String text , List<String> list){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("oueslati.rihem@esprit.tn");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list != null && list.size() > 0)
            message.setCc(getCcArray(list));
        emailSender.send(message);
    }

    public String[] getCcArray(List<String> cclist){
        String[] cc = new String[cclist.size()];
        for (int i = 0 ; i < cclist.size(); i++){
            cc[i] = cclist.get(i);
        }
        return cc;
    }
    public void forgetMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("orihem47@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMSG = "<p><b>Your Login details </b></p><b>Email:</b>" + to + "<br><b>Password: </b>" + password + "<br><a href=\"http://localhost:4200/changepassword\">Click here to change your password</a></p>";
        message.setContent(htmlMSG, "text/html");
        emailSender.send(message);
    }
    public void sendVerificationMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("orihem47@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // Construct activation link with recipient's email
        String activationLink = "<a href=\"http://localhost:8089/activate/" + to + "\">Click here to Activate your account</a>";

        // Construct HTML message with activation link
        String htmlMSG = "<p><b>Activate Your account</b></p>"
                + "<b>Email:</b> " + to + "<br>"
                + activationLink;

        message.setContent(htmlMSG, "text/html");

        emailSender.send(message);
    }

    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("orihem47@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

       helper.setText(template, true);

        mailSender.send(mimeMessage);
    }



}