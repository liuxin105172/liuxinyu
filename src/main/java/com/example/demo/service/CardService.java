package com.example.demo.service;

import com.example.demo.model.Card;

import java.util.List;

public interface CardService {

    public Card saveCard(Card card);

    public Card findCardById(Long id);

    public List<Card> findAllCard();

    public Card updateCard(Card card);

    public void deleteCardById(Long id);
}
