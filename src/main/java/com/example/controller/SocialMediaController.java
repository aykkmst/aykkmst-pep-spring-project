package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.service.*;
import com.example.entity.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }


    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Account newAccount = accountService.addAccount(account);
        if(newAccount == null)
            return ResponseEntity.status(409).build();
        else
            return ResponseEntity.status(200).body(account);

    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account confAccount = accountService.loginAccount(account);
        if(confAccount == null)
            return ResponseEntity.status(401).build();
        else
            return ResponseEntity.status(200).body(confAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        Message confMessage = messageService.addMessage(message);
        if(confMessage == null)
            return ResponseEntity.status(400).build();
        else
            return ResponseEntity.status(200).body(confMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Message message = messageService.getMessageById(messageId);
        if(message == null)
            return ResponseEntity.status(200).build();
        else
            return ResponseEntity.status(200).body(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        int status = messageService.deleteMessageById(messageId);
        if(status == 0)
            return ResponseEntity.status(200).build();
        else
            return ResponseEntity.status(200).body(status);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable int messageId, @RequestBody Message message){
        int status = messageService.patchMessageById(messageId, message);
        if(status == 0)
            return ResponseEntity.status(400).build();
        else
            return ResponseEntity.status(200).body(status);
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable int accountId){
        List<Message> messages = messageService.getAllMessagesByUser(accountId);
        return ResponseEntity.status(200).body(messages);
    }

}
