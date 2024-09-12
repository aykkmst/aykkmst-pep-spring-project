package com.example.service;

import org.springframework.stereotype.Service;
import com.example.entity.*;
import com.example.repository.*;

import java.util.Optional;
import java.util.List;
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;    
    
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){               
        this.messageRepository = messageRepository; 
        this.accountRepository = accountRepository;
    }
    
    public Message addMessage(Message message){        
        String text = message.getMessageText();
        int length = text.length();
        Optional<Account> account = accountRepository.findById((message.getPostedBy()));        
        if(length == 0 || length > 255)
            return null;
        else if(!account.isPresent())
            return null;
        else
            return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();

    }

    public Message getMessageById(int messageId){
        Optional<Message> optMessage = messageRepository.findById(messageId);
        if(optMessage.isPresent())
            return optMessage.get();
        else
            return null;        
    }

    public int deleteMessageById(int messageId){        
        Optional<Message> optMessage = messageRepository.findById(messageId);
        if(optMessage.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        else
            return 0;
    }

    public int patchMessageById(int messageId, Message message){
        Optional<Message> optMessage = messageRepository.findById(messageId);
        if(optMessage.isPresent()){
            Message newMessage = optMessage.get();
            String text = message.getMessageText();
            int length = text.length();
            if(length == 0 || length > 255)
                return 0;
            else{
                if(message.getPostedBy() != null)
                    newMessage.setPostedBy(message.getPostedBy());

                if(message.getTimePostedEpoch() != null)
                    newMessage.setTimePostedEpoch(message.getTimePostedEpoch());
                              
                newMessage.setMessageText(text);
                messageRepository.save(newMessage);
                return 1;
            }
        }
        else
            return 0;
    }

    public List<Message> getAllMessagesByUser(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
