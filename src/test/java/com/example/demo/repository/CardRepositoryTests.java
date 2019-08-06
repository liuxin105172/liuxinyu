package com.example.demo.repository;

import com.example.demo.model.Card;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CardRepositoryTests {

    @Autowired
    private CardRepository cardRepository;

    private Card befCard,befCard2;

    @Before
    public void testASaveCard() {
        befCard = new Card();
        befCard.setTitle("myTitle1");
        befCard.setContext("myContext1");
        cardRepository.save(befCard);

        befCard2 = new Card();
        befCard2.setTitle("myTitle2");
        befCard2.setContext("myContext2");
        cardRepository.save(befCard2);
        Assert.assertEquals("myContext1", cardRepository.findByTitle("myTitle1").getContext());
    }

    @Test
    public void testBFindCardById() {
        Card renCard = cardRepository.getOne(befCard.getId());
        Assert.assertEquals(befCard.getTitle(), renCard.getTitle());
    }

    @Test
    public void testCFindAllCard() {
        List<Card> list = cardRepository.findAll();
        int count = list.size();
        // Assert.isTrue(count == 2,"error");
        Assert.assertEquals(2, count);
    }

    @Test
    public void testDUpdateCard() {
        befCard2.setTitle("myTitle1");
        befCard2.setContext("myNewContext");
        Card renCard = cardRepository.save(befCard2);
        Assert.assertEquals(befCard2.getContext(), renCard.getContext());
    }

    @Test
    public void testEDeleteCardById() {
        cardRepository.deleteById(befCard.getId());
        List<Card> cardList1 = cardRepository.findAll();
        Assert.assertEquals(1, cardList1.size());
        cardRepository.deleteAll();
        List<Card> cardList2 = cardRepository.findAll();
        Assert.assertEquals(0, cardList2.size());
    }


}
