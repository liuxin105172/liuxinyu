package com.example.demo.service;

import com.example.demo.model.Card;
import com.example.demo.repository.CardRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class CardServiceTests {

    @TestConfiguration
    static class CardServiceImplTestContextConfiguration{

        @Bean
        public CardService cardService(){
            return new CardServiceImpl();
        }
    }

    @Autowired
    private CardService cardService;
    @MockBean
    private CardRepository cardRepository;

    private Card card1,card2,updcard,retCard1,retCard2;

    @Before
    public void setUp(){
        card1 = new Card();
        card1.setTitle("title1");
        card1.setContext("context1");

        retCard1 = card1;
        retCard1.setId(1L);
        retCard1.setTitle("title1");
        retCard1.setContext("context1");

        card2 = new Card();
        card2.setTitle("title2");
        card2.setContext("context2");

        retCard2 = card2;
        retCard2.setId(2L);
        retCard2.setTitle("title2");
        retCard2.setContext("context2");

        updcard = retCard1;
        updcard.setId(1L);
        updcard.setTitle("title1");
        updcard.setContext("myNewContext");

        List<Card> cardList = Arrays.asList(card1,card2);
        Mockito.when(cardRepository.getOne(card1.getId())).thenReturn(card1);
        Mockito.when(cardRepository.getOne(card2.getId())).thenReturn(card2);
        Mockito.when(cardRepository.getOne(0L)).thenReturn(null);
        Mockito.when(cardRepository.findAll()).thenReturn(cardList);
        Mockito.when(cardRepository.save(card1)).thenReturn(retCard1);
        Mockito.when(cardRepository.save(card2)).thenReturn(retCard2);
        Mockito.when(cardRepository.save(retCard1)).thenReturn(updcard);

    }

    @Test
    public void given2Cards_when_findCardById_thenReturnCard(){
        //given
        Long card1Id = 1L;
        Long card2Id = 2L;
        Long card3Id = 0L;

        //when
        Card retcard1 = cardService.findCardById(card1Id);
        Card retcard2 = cardService.findCardById(card2Id);
        Card retcard3 = cardService.findCardById(card3Id);

        //then
        Assert.assertEquals("title1",retcard1.getTitle());
        Assert.assertEquals("title2",retcard2.getTitle());
        Assert.assertTrue(null == retcard3);
    }

    @Test
    public void givenNull_when_findAllCards_thenReturn2Records(){

        //when
        List<Card> cardList = cardService.findAllCard();

        //then
        int count = cardList.size();
        Assert.assertEquals(2,count);
    }

    @Test
    public void given1Card_when_updateCard_thenReturn1Card(){
        //given
       Card card = updcard;

        //when
        Card retCard = cardService.updateCard(card);

        //then
        Assert.assertEquals(card.getContext(),retCard.getContext());
    }

    @Test
    public void given1CardId_when_deleteCard_thenNoReturn(){
        //given
        Long card1Id = retCard1.getId();

        //when
        cardService.deleteCardById(card1Id);

    }
}
