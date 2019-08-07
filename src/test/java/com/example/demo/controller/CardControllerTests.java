package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Card;
import com.example.demo.service.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc

public class CardControllerTests {

    @Autowired
    private MockMvc mvc;
    //@MockBean
    @Autowired
    private CardService cardService;

    private Card card1,card2,retcard1,retcard2,updcard;

    @Test
    @Transactional
    public void addCard()throws Exception{
        card1 = new Card();
        card1.setTitle("myTitle1");
        card1.setContext("myContext1");
        JSONObject object = new JSONObject();
        object.put("title",card1.getTitle());
        object.put("context",card1.getContext());

        MvcResult result = mvc.perform(post("/api/addcard")
                .content(object.toJSONString().getBytes())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
               // .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String test = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(test);
        //查看title
        assertThat(jsonObject.getString("title")).isEqualTo("myTitle1");
    }

    /**
     * 测试查询所有card
     * @throws Exception
     */
    @Test
    @Transactional
    public void givenCards_whenGetCards_thenReturnJsonArray() throws Exception{

        List<Card> allCards = Arrays.asList(retcard1);

        //given(cardService.findAllCard()).willReturn(allCards);

       mvc.perform(MockMvcRequestBuilders.get("/api/getallcards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("myTitle1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].context").value("myContext1"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试根据id查询card
     * @throws Exception
     */
    @Test
    @Transactional
    public void given1Card_when_findCardById_thenReturnCard() throws Exception{
        card1 = new Card();
        card1.setTitle("myTitle1");
        card1.setContext("myContext1");
        JSONObject object = new JSONObject();
        object.put("title",card1.getTitle());
        object.put("context",card1.getContext());

        MvcResult result = mvc.perform(post("/api/addcard")
                .content(object.toJSONString().getBytes())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                // .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String test = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(test);
        Long id =jsonObject.getLong("id");
        mvc.perform(MockMvcRequestBuilders.get("/api/getcardbyid/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("myTitle1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.context").value("myContext1"))
                .andDo(MockMvcResultHandlers.print());

        assertThat(jsonObject.getString("title")).isEqualTo("myTitle1");

    }

    /**
     * 测试修改Card
     * @throws Exception
     */
    @Test
    @Transactional
    public void given1Card_when_updateCard_thenReturn1Card()throws Exception{
        card1 = new Card();
        card1.setTitle("myTitle1");
        card1.setContext("myContext1");
        JSONObject object = new JSONObject();
        object.put("title",card1.getTitle());
        object.put("context",card1.getContext());

        MvcResult result = mvc.perform(post("/api/addcard")
                .content(object.toJSONString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andReturn();
        String test = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(test);
        retcard1 = new Card();
        retcard1.setId(jsonObject.getLong("id"));
        retcard1.setTitle("myNewTitle");
        retcard1.setContext("myNewContext");
        MvcResult mvcResult = mvc.perform(put("/api/updatecard/").
                content(JSONObject.toJSONString(retcard1)).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andReturn();
        JSONObject res =JSONObject.parseObject( mvcResult.getResponse().getContentAsString());
        assertThat(res.getString("title")).isEqualTo("myNewTitle");

    }

    /**
     * 测试删除方法
     * @throws Exception
     */
    @Test
    @Transactional
    public void given1CardId_when_deleteCard_thenNoReturn()throws Exception{
        card1 = new Card();
        card1.setTitle("myTitle1");
        card1.setContext("myContext1");
        JSONObject object = new JSONObject();
        object.put("title",card1.getTitle());
        object.put("context",card1.getContext());

        MvcResult result = mvc.perform(post("/api/addcard")
                .content(object.toJSONString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                // .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String test = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(test);
        Long id =jsonObject.getLong("id");

        MvcResult newResult = mvc.perform(MockMvcRequestBuilders.delete("/api/deletecard/"+id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(object.toJSONString())//传json参数
                )
                .andExpect(status().isOk()).andReturn();

    }

}
