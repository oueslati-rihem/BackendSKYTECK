package com.authentifcation.projectpitwo.serviceImplimentation;

import com.authentifcation.projectpitwo.entities.Offer;
import com.authentifcation.projectpitwo.serviceInterface.GenererCertifInterface;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
@AllArgsConstructor
public class GenererCertif implements GenererCertifInterface {


    @Override
    public byte[] generateCertificationImage(Offer offer) {
        try {
            // Création de l'image
            BufferedImage image = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // Fond blanc
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, 1000, 600);

            // Dessin du cadre autour de toute l'image du certificat
            g2d.setStroke(new BasicStroke(8)); // Épaisseur de la ligne du cadre
            g2d.setColor(Color.BLACK); // Couleur du cadre
            int cornerRadius = 20; // Rayon des coins arrondis
            int borderWidth = 20; // Largeur du cadre
            int borderX = borderWidth / 2; // Position en X pour le cadre
            int borderY = borderWidth / 2; // Position en Y pour le cadre
            int borderW = 1000 - borderWidth; // Largeur du cadre
            int borderH = 600 - borderWidth; // Hauteur du cadre
            g2d.drawRoundRect(borderX, borderY, borderW, borderH, cornerRadius, cornerRadius); // Dessiner le cadre avec des coins arrondis

            // Logo de l'organisation centré en haut
            ImageIcon logo = new ImageIcon("C://Users/21650/OneDrive/Bureau/projetpi/projetpi/src/assets/backOffice/img/logo1.png");
            int logoWidth = logo.getIconWidth();
            int logoHeight = logo.getIconHeight();
            int logoX = (1000 - logoWidth) / 2; // Position en X pour le logo centré horizontalement
            int logoY = 50; // Position en Y pour le logo en haut
            g2d.drawImage(logo.getImage(), logoX, logoY, null);

            // En-tête avec le titre du certificat
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.setColor(Color.BLACK);
            g2d.drawString("CERTIFICAT DE RÉUSSITE", 200, logoY + logoHeight + 50);

            // Informations sur l'offre
            g2d.setFont(new Font("Arial", Font.PLAIN, 18));
            g2d.drawString("Nom de l'étudiant: " + offer.getTuteur().getUserFirstName(), 50, logoY + logoHeight + 100);
            g2d.drawString("Félicitations pour avoir obtenu ce certificat!", 50, logoY + logoHeight + 130); // Phrase de félicitations
            g2d.drawString("Titre de l'offre: " + offer.getTitre(), 50, logoY + logoHeight + 160);
            g2d.drawString("Description de l'offre: " + offer.getDescription(), 50, logoY + logoHeight + 190);

            // Signature du responsable
            g2d.setFont(new Font("Arial", Font.ITALIC, 16));
            g2d.drawString("Signature du responsable", 500, logoY + logoHeight + 250);

            // Chargement et dessin de l'image de signature
            BufferedImage signature = ImageIO.read(new File("C://Users/21650/Downloads/R.png"));
            int desiredSignatureWidth = 200;
            int desiredSignatureHeight = 100;
            Image scaledSignature = signature.getScaledInstance(desiredSignatureWidth, desiredSignatureHeight, Image.SCALE_SMOOTH);

            int textY = logoY + logoHeight + 250;
            int spaceBetweenTextAndSignature = 20;
            int signatureY = textY + spaceBetweenTextAndSignature;
            int signatureX = 750;
            int signatureWidth = scaledSignature.getWidth(null);
            g2d.drawImage(scaledSignature, signatureX - signatureWidth, signatureY, null);

            g2d.dispose();

            // Convertir l'image en byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

