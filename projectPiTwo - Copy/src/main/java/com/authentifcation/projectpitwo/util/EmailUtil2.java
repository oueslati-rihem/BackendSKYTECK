package com.authentifcation.projectpitwo.util;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailUtil2 {
    @Autowired
    private JavaMailSender emailSender;



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
    public void sendEmail(String to, String subject, String content, String eventLink) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("hadil.labidi@esprit.tn");
        helper.setTo(to);
        helper.setSubject(subject);

        // URL of the image for congratulations
        String congratsImageUrl = "https://th.bing.com/th/id/R.baee7d789ce0a5724025683d7eb99ce1?rik=KDUpnCuWrEb2wg&riu=http%3a%2f%2fwww.desicomments.com%2fwp-content%2fuploads%2f2017%2f07%2fCongratulations-Pic.png&ehk=PyrdWR%2bK%2fpkaCbUDB95EzsrETvwgY3aH2iXrQnxDf0k%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1"; // Replace with the actual URL of the image

        // Create the HTML content with embedded image and event link
        String htmlContent = "<p><b>Your Participation Acceptance</b></p>"
                + "<p><img src=\"" + congratsImageUrl + "\" alt=\"Congratulations\" style=\"width: 200px;\"></p>"
                + "<p><b>Email:</b> " + to + "</p>"
                + "<p><b>Content:</b> " + content + "</p>"
                + "<p><a href=\"" + eventLink + "\">Click here to join us</a></p>";

        // Set the HTML content of the message
        message.setContent(htmlContent, "text/html; charset=utf-8");

        // Embedding an image requires setting the message type to HTML
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }

}