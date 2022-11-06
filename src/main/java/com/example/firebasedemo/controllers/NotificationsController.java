package com.example.firebasedemo.controllers;

import com.example.firebasedemo.dtos.Note;
import com.example.firebasedemo.services.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationsController {
    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    Logger logger = LoggerFactory.getLogger(NotificationsController.class);

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Note note, @RequestParam String token) {
        try {
            logger.info("Sending notification...");
            String result = firebaseMessagingService.sendNotification(note, token);
            logger.info("Notification sent!!!  ID: " + result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
