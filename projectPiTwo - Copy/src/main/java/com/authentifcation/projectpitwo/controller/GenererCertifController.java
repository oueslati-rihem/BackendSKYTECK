package com.authentifcation.projectpitwo.controller;

import com.authentifcation.projectpitwo.entities.Offer;
import com.authentifcation.projectpitwo.repository.OfferRepository;
import com.authentifcation.projectpitwo.serviceImplimentation.GenererCertif;
import com.authentifcation.projectpitwo.serviceImplimentation.OfferService;
import com.authentifcation.projectpitwo.serviceImplimentation.QRCodeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/GenererCertif")
@CrossOrigin()
public class GenererCertifController {
    @Autowired
    GenererCertif certificationService;
@Autowired
    QRCodeService qrCodeService;
    @Autowired
    OfferRepository offerRepository;
    @GetMapping("/generatecertifpng/{offerId}")
    public ResponseEntity<byte[]> generateCertificationQRCode(@PathVariable Long offerId) {
        // Supposons que vous ayez un service pour récupérer l'offre à partir de son ID
        Offer offer = offerRepository.findById(offerId).orElse(null);

        // Génération du QR code de la certification à partir de l'offre
        byte[] qrCodeBytes = certificationService.generateCertificationImage(offer);

        // Configuration de l'en-tête de la réponse
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDispositionFormData("filename", "certification.png");

        // Retour de la réponse avec le QR code de la certification
        return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
    }

}


