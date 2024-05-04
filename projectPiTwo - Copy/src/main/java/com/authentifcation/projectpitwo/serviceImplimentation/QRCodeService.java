package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Offer;
import com.authentifcation.projectpitwo.entities.Participation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
@Service
public class QRCodeService {
    public byte[] generateQRCodeForEvent(Offer offer) {
        // Convertir les données de participation en une chaîne JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String participationData = "";
        try {
            participationData = objectMapper.writeValueAsString(offer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Définir les dimensions du QR code
        int width = 300;
        int height = 300;

        try {
            // Utiliser la bibliothèque ZXing pour générer le QR code
            BitMatrix bitMatrix = new MultiFormatWriter().encode(participationData, BarcodeFormat.QR_CODE, width, height);

            // Convertir le BitMatrix en une image PNG
            BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            // Créer un flux de sortie pour écrire l'image PNG
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Écrire l'image PNG dans le flux de sortie
            ImageIO.write(qrCodeImage, "png", outputStream);

            // Renvoyer les octets du flux de sortie contenant l'image PNG du QR code
            return outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
