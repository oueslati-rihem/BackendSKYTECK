package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.JwtRequest;
import com.authentifcation.projectpitwo.entities.JwtResponse;
import com.authentifcation.projectpitwo.entities.User;
import com.authentifcation.projectpitwo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;

  /* @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }*/
  @PostMapping({"/authenticate"})
  public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest jwtRequest) {
      try {
          JwtResponse jwtResponse = jwtService.createJwtToken(jwtRequest);
          return ResponseEntity.ok(jwtResponse);
      } catch (Exception e) {
          String errorMessage;
          if (e.getMessage().equals("USER_DISABLED")) {
              return new ResponseEntity<>("Unauthorized",HttpStatus.BAD_REQUEST);
          } else if (e.getMessage().equals("INVALID_CREDENTIALS")) {
              return new ResponseEntity<>("INVALID_CREDENTIALS",HttpStatus.UNAUTHORIZED);
          } else {
              errorMessage = "Internal server error";
          }
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"" + errorMessage + "\"}");
      }
  }

    @PostMapping("/unban/{userName}")
    public ResponseEntity<?> unbanUserAndResetAttempts(@PathVariable String userName) {
        try {
            jwtService.unbanUserAndResetAttempts(userName);
            return ResponseEntity.ok("User unbanned and login attempts reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to unban user: " + e.getMessage());
        }
    }

}
