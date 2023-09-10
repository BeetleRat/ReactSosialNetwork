package ru.beetlerat.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.beetlerat.socialnetwork.dto.ResponseToFront;
import ru.beetlerat.socialnetwork.dto.message.MessageDTO;
import ru.beetlerat.socialnetwork.dto.message.MessageResponse;

import java.util.List;
@RestController
public class MessageController {
    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/send")
    public ResponseEntity<ResponseToFront> sendMessage(@RequestBody MessageDTO messageDTO) {
        template.convertAndSend("/topic/message", messageDTO);
        return  ResponseEntity.ok(ResponseToFront.Ok());
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload MessageDTO textMessageDTO) {
        System.out.println(textMessageDTO.getMessage());
        // receive message from client
    }


    @SendTo("/topic/message")
    public MessageDTO broadcastMessage(@Payload MessageDTO textMessageDTO) {
        return textMessageDTO;
    }
}
