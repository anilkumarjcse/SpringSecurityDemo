package com.aj.SpringSecurityDemo.controller;

import com.aj.SpringSecurityDemo.config.SecurityConfig;
import com.aj.SpringSecurityDemo.service.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({DemoController.class, TokenController.class})
@Import({SecurityConfig.class, TokenService.class})
class DemoControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    void unAuthenticatedTest() throws Exception {
        mockMvc.perform(get("/msg")).andExpect(status().isUnauthorized());
    }

    @Test

    void authenticatedTest() throws Exception {
        MvcResult result  = mockMvc.perform(post("/token")
                .with(httpBasic("aj","aj")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();
        mockMvc.perform(get("/msg")
                .header("Authorization", "Bearer "+token))
                .andExpect(content().string("Welcome to Spring Security Demo"));


    }


    @Test
    @WithMockUser
    void mockUserTest() throws Exception{
        mockMvc.perform(get("/msg")).andExpect(status().isOk());
    }

}