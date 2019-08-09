package com.example.demo.controller;

import com.example.demo.exception.CardNotFoundException;
import com.example.demo.model.Card;
import com.example.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * 获取所有card
     * @return
     */
    @GetMapping("/getallcards")
    public List<Card> getAllCards(){
        return cardService.findAllCard();
    }

    /**
     * 通过id获取card
     * @param cardId
     * @return
     */
    @GetMapping("/getcardbyid/{cardId}")
    public  Card getCardById(@PathVariable("cardId") Long cardId){
        try{
            return cardService.findCardById(cardId);
        }catch (CardNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"not found！！",e);
        }
    }

    /**
     * 添加card
     * @param card
     * @return
     */
    @PostMapping("/addcard")
    public List<Card> addCard(@RequestBody Card card){
        cardService.saveCard(card);
        return cardService.findAllCard();
    }

    /**
     * 修改card
     * @param card
     * @return
     */
    @PutMapping("/updatecard")
    public List<Card> updateCard(@RequestBody Card card){
        cardService.updateCard(card);
        return cardService.findAllCard();
    }

    /**
     * 根据id删除card
     * @param cardId
     */
    @DeleteMapping("/deletecard/{cardId}")
    public List<Card> deleteCard(@PathVariable("cardId") Long cardId){

        cardService.deleteCardById(cardId);
        return cardService.findAllCard();

    }

    @GetMapping("/notfound")
    public String notfoundException(){
        throw new EntityNotFoundException();
    }

    @GetMapping("/run")
    public String runException(){
        throw new RuntimeException();
    }
}
