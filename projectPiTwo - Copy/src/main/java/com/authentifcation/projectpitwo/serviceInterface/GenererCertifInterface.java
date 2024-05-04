package com.authentifcation.projectpitwo.serviceInterface;

import com.authentifcation.projectpitwo.entities.Offer;

public interface GenererCertifInterface {
    byte[] generateCertificationImage(Offer offer);
}
