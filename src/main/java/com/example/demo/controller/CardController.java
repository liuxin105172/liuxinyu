package com.example.demo.controller;

import com.example.demo.model.Card;
import com.example.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return cardService.findCardById(cardId);
    }

    /**
     * 添加card
     * @param card
     * @return
     */
    @PostMapping("/addcard")
    public Card addCard(@RequestBody Card card){
        return cardService.saveCard(card);
    }

    /**
     * 修改card
     * @param card
     * @return
     */
    @PutMapping("/updatecard")
    public Card updateCard(@RequestBody Card card){
        return cardService.updateCard(card);
    }

    /**
     * 根据id删除card
     * @param cardId
     */
    @DeleteMapping("/deletecard/{cardId}")
    public void deleteCard(@PathVariable("cardId") Long cardId){
        cardService.deleteCardById(cardId);
    }
}
