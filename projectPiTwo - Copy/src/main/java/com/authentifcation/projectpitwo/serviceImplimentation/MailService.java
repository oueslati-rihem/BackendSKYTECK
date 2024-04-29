package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Participation;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.service.UserService;
import com.authentifcation.projectpitwo.serviceInterface.Mailing;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service

public class MailService implements Mailing {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    public void EnvoyerEmail(Participation participation) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            // Définir le sujet du mail
            mimeMessageHelper.setSubject("Confirmation de participation ");

            // Définir l'expéditeur
            mimeMessageHelper.setFrom("contact@dsms.world");

            // Définir le destinataire (utilisateur associé à l'offre)
            mimeMessageHelper.setTo("selma.kacem@istic.ucar.tn"); // Assurez-vous que vous avez une méthode pour récupérer l'e-mail de l'utilisateur associé à l'offre

            // Générer le contenu du mail avec les détails de l'offre
            String content = "Bonjour " +  ",<br><br>"
                    + "Nous sommes heureux de vous informer que votre demande de participation à l'offre intitulée : <strong>"+" "+ participation.getTitreoffer()+"" + " " + "par le tuteur :" + participation.getOffer().getTuteur().getUserFirstName() +""+ participation.getOffer().getTuteur().getUserLastName()+  "</strong> a été acceptée.<br>"
                    + "Vous trouverez ci-joint un PDF contenant la description de l'offre.<br><br>"
                    + "Cordialement,<br>L'équipe de la plateforme";

            // Définir le contenu du mail
            mimeMessageHelper.setText(content, true);

            // Générer le PDF à partir de la description de l'offre
            byte[] pdfBytes = genererPDF(participation.getDescriptionoffer());

            // Ajouter le PDF en pièce jointe
            ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes);
            mimeMessageHelper.addAttachment("Description_Offre.pdf", pdfResource);


            // Envoyer le mail
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour générer un PDF à partir du contenu de la description
    private byte[] genererPDF(String description) {
        String imagePath = "C:/Users/21650/Downloads/test/offre.jpg";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter writer =  PdfWriter.getInstance(document, outputStream);




            // Ajouter un événement de page pour gérer le pied de page
            writer.setPageEvent(new PdfFooterEvent());
            document.open();
            // Créer un paragraphe vide pour créer un espace vertical
            Paragraph emptySpace = new Paragraph("\n");
            document.add(emptySpace);
            Image image = Image.getInstance(imagePath);
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleAbsolute(200, 200);
            // Ajouter l'image au document PDF
            document.add(image);
            // Ajouter un autre espace vertical pour séparer l'image du texte
            document.add(emptySpace);
            document.add(new Paragraph("Bienvenue sur Skytech!" ));

            document.add(new Paragraph(description));

            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    static class PdfFooterEvent extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // Créer un objet PdfContentByte pour écrire le pied de page
            PdfContentByte cb = writer.getDirectContent();

            // Définir la position du pied de page
            float x = (document.left() + document.right()) / 2;
            float y = document.bottom() - 20;

            try {
                // Charger une police
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

                // Définir la police et la taille du texte
                cb.setFontAndSize(bf, 10);

                // Écrire le pied de page
                cb.beginText();
                cb.showTextAligned(Element.ALIGN_CENTER, "Contactez-nous : contact@skytech.com | Téléphone : +123456789", x, y, 0);
                cb.endText();
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}





