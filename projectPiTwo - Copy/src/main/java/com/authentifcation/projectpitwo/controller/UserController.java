package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.dao.RoleDao;
import com.authentifcation.projectpitwo.dao.UserDao;
import com.authentifcation.projectpitwo.entities.Role;
import com.authentifcation.projectpitwo.entities.User;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.service.CloudinaryService;
import com.authentifcation.projectpitwo.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;

//@CrossOrigin(allowedHeaders = "*", origins = "*")

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }



    @PostMapping(value = {"/register"})
    public User registerUser(@RequestBody User user ) {
        return userService.save(user);
    }
    @GetMapping("/bannedUserStatistics")
    public ResponseEntity<?> getBannedUserStatistics() {
        long bannedUserCount = userRepository.countByBanned(true);

        Map<String, Long> statistics = new HashMap<>();
        statistics.put("bannedUserCount", bannedUserCount);

        return ResponseEntity.ok(statistics);
    }

 /*  @PostMapping(value = {"/registerNewUser"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public User registerNewUser(
            @RequestPart("userData") Map<String, String> userData,
            @RequestPart("cvFile") MultipartFile cvFile
           // @RequestPart("imageFile") MultipartFile imageFile
    ) {
        String roleName = userData.get("role"); // Assuming "role" is the key for role information
        User user = new User();
        user.setUserName(userData.get("userName"));
        user.setUserFirstName(userData.get("userFirstName"));
        user.setUserLastName(userData.get("userLastName"));
        user.setUserPassword(userData.get("userPassword"));
        user.setContactNumber(userData.get("contactNumber"));

        // Assuming the CV and image paths are provided in the userData map
         String cvPath = userData.get("cvPath");
        // String imagePath = userData.get("imagePath");
         user.setCv(cvPath);
        // user.setImage(imagePath);

        // Set CV and image from uploaded files
        user.setCv(cvFile.getOriginalFilename());
     //   user.setImage(imageFile.getOriginalFilename());

        // Save CV and image files to storage
        // cvFile.transferTo(new File("path/to/save/cv"));
        // imageFile.transferTo(new File("path/to/save/image"));

        return userService.registerNewUser(user, roleName);
    }
*/





    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }

    @GetMapping({"/forTutor"})
    @PreAuthorize("hasRole('Tutor')")
    public String forTutor(){
        return "This URL is only accessible to the tutor";
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<User>> getAllUser(){return userService.getAllUser();}

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap) {
        return userService.update(requestMap);
    }

    @GetMapping(path = "/checkToken")
    public ResponseEntity<String> checkToken() {
        return userService.checkToken();
    }

    @PostMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap) {
        return userService.changePassword(requestMap);
    }
    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser( @RequestBody User updatedUser) {
        // Call the updateUser method of UserService
     return userService.updateUser( updatedUser);
    }
    @PutMapping("/updateStudents")
    public User updateStudent(@RequestBody User student) {
        return  userService.updateStudent(student);
    }

    @GetMapping("/userDetail")

    public ResponseEntity<User> getUserById() {
        try {
            User user = userService.getUserById();
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userService.getUsers();
    }
    @PostMapping( "/forgotPassword")
    public ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> requestMap) {
        return userService.forgetPassword(requestMap);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("newPassword");

        try {
            userService.updatePassword(username, newPassword);
            return ResponseEntity.ok().body("Password updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password");
        }
    }

    @PostMapping("/activate/{email}")
    public ResponseEntity<String> activateUserAccount(@PathVariable String email) {
        ResponseEntity<String> activationResponse = userService.activateUserAccount(email);
        return activationResponse;
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        userService.activateAccount(token);
    }
    @GetMapping("/userRoleStatistics")
    public ResponseEntity<?> getUserRoleStatistics() {
        Role tutorRole = roleDao.findByroleName("Tutor"); // Assuming you have a method to find a Role by name
        long tutorCount = userDao.countByRole(tutorRole);

        Role userRole = roleDao.findByroleName("User"); // Assuming you have a method to find a Role by name
        long userCount = userDao.countByRole(userRole);

        Map<String, Long> statistics = new HashMap<>();
        statistics.put("tutor", tutorCount);
        statistics.put("user", userCount); // Changed "User" to "user" to match the key case with "tutor"

        return ResponseEntity.ok(statistics);
    }
    @PostMapping(value = "/registerNewUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerNewUser(@RequestParam("userDataJson") String userDataJson,
                                             @RequestParam("cv") MultipartFile cv,
                                             @RequestParam("image") MultipartFile image) {

        try {
            // Parse the JSON string to a Map<String, String>
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> userData = objectMapper.readValue(userDataJson, new TypeReference<Map<String, String>>() {});

            // Validate CV file type
            if (!isValidCVFileType(cv)) {
                throw new IllegalArgumentException("Invalid CV file type. Only PDF files are allowed.");
            }

            // Check if the content of the CV mentions Spring Boot or Angular
            if (!containsSpringBootOrAngularKeywords(cv)) {
                throw new IllegalArgumentException("The content of the CV does not mention the required components.");
            }

            BufferedImage bi = ImageIO.read(image.getInputStream());
            if (bi == null) {
                throw new IllegalArgumentException("Image non valide!");
            }

            Map result = cloudinaryService.upload(image);
            byte[] cvBytes = cv.getBytes();

            // Build the User object using userData and cv content
            User user = User.builder()
                    .userName(userData.get("userName"))
                    .userFirstName(userData.get("userFirstName"))
                    .userLastName(userData.get("userLastName"))
                    .userPassword(userData.get("userPassword"))
                    .contactNumber(userData.get("contactNumber"))
                    .image((String) result.get("url"))
                    .cv(cvBytes)
                    .build();


            String roleName = userData.get("role");
            return userService.registerNewUser(user, roleName);
        } catch (IOException e) {
            // Handle JSON parsing or file reading errors
            throw new RuntimeException("Failed to parse user data or read CV file.", e);
        } catch (IllegalArgumentException e) {
            // Handle validation errors (e.g., invalid file type or missing keywords)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Failed to register new user.", e);
        }
    }

    private boolean isValidCVFileType(MultipartFile cv) {
        return cv.getOriginalFilename() != null && cv.getOriginalFilename().toLowerCase().endsWith(".pdf");
    }

    private boolean containsSpringBootOrAngularKeywords(MultipartFile cv) {
        try (InputStream inputStream = cv.getInputStream()) {
            try (PDDocument document = PDDocument.load(inputStream)) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                // Check if text contains Spring Boot or Angular keywords
                return text.contains("Formations") || text.contains("Projet Academique") || text.contains("Compétences Technique");
            }
        } catch (IOException e) {
            // Handle PDF processing errors
            throw new RuntimeException("Failed to process CV file.", e);
        }
    }
}






