package com.crud.crud.controller;

import com.crud.crud.model.Body;
import com.crud.crud.model.Provider;
import com.crud.crud.repository.ProviderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(ProviderController.class)
public class ProviderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProviderRepository providerRepository;

    @MockBean
    private Body body;

    @Test
    public void testGetAllProviders() throws Exception{
        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider("Airtel",
                "Optical",
                LocalDateTime.of(2018, 9, 11, 22, 00, 00),
                LocalDateTime.of(2019, 9, 11, 22, 00, 00)
        ));
        Mockito.when(providerRepository.findAll()).thenReturn(providers);
        String url = "/api/v1/provider";
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(providers);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void testGetProviderByProviderName() throws Exception{
        Provider provider = new Provider("Airtel",
                "Optical",
                LocalDateTime.of(2018, 9, 11, 22, 00, 00),
                LocalDateTime.of(2019, 9, 11, 22, 00, 00)
        );
        String providerName = "Airtel";
        String url = "/api/v1/provider/" + providerName;
        Mockito.when(providerRepository.findByProviderName(providerName)).thenReturn(provider);
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(provider);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void testCreateProvider() throws JsonProcessingException, Exception{
        Provider provider = new Provider("Airtel",
                "Optical",
                LocalDateTime.of(2018, 9, 11, 22, 00, 00),
                LocalDateTime.of(2019, 9, 11, 22, 00, 00)
        );
        Body body = new Body(LocalDateTime.of(2018, 9, 11, 22, 00, 00),
                LocalDateTime.of(2019, 9, 11, 22, 00, 00));
        Mockito.when(providerRepository.save(provider)).thenReturn(provider);
        String url = "/api/v1/provider";
        mockMvc.perform(
                post(url)
                        .param("providerName","Airtel")
                        .param("flowName","Optical")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(body))
        ).andExpect(status().isOk());
    }

    @Test
    public void testUpdateProvider() throws Exception{
        Provider oldProvider = new Provider("Airtel",
                "Optical",
                LocalDateTime.of(2018, 9, 11, 22, 00, 00),
                LocalDateTime.of(2019, 9, 11, 22, 00, 00)
        );
        String providerName = "Airtel";
        String url = "/api/v1/provider/" + providerName;
        Provider newProvider = new Provider("Airtel",
                "Classified",
                LocalDateTime.of(2018, 9, 11, 22, 00, 00),
                LocalDateTime.of(2019, 9, 11, 22, 00, 00)
        );
        Mockito.when(providerRepository.findByProviderName(providerName)).thenReturn(newProvider);
        Mockito.when(providerRepository.save(newProvider)).thenReturn(newProvider);
        MvcResult mvcResult = mockMvc.perform(
                put(url)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newProvider))
        ).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(newProvider);
        assertThat(actualJsonResponse).isNotNull();
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    public void testDeleteProviderByProviderName() throws Exception{
        Provider provider = new Provider("Airtel",
                "Optical",
                LocalDateTime.of(2018, 9, 11, 22, 00, 00),
                LocalDateTime.of(2019, 9, 11, 22, 00, 00)
        );
        String providerName = "Airtel";
        String url = "/api/v1/provider/" + providerName;
        Mockito.when(providerRepository.findByProviderName(providerName)).thenReturn(provider);
        Mockito.doNothing().when(providerRepository).delete(provider);
        MvcResult mvcResult = mockMvc.perform(delete(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        Map<String, Boolean> expectedResponse = new HashMap<>();
        expectedResponse.put("deleted", Boolean.TRUE);
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedResponse);
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

}