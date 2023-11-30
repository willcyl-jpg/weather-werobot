package com.bitanswer.weather.service;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.bitanswer.weather.representation.RobotContent;

public class RobotSender {

   private static final Logger LOG = LoggerFactory.getLogger(RobotSender.class);

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                                                                       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

   public static void sendTo(String uri, RobotContent content) {
      try {
         sendMessage(uri, content);
      } catch (Exception e) {
         LOG.error("Send to robot fail -> " + uri, e);
      }
   }

   private static void sendMessage(String url, RobotContent content) throws Exception {
      RestTemplate restTemplate = new RestTemplateBuilder().build();
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      String body = OBJECT_MAPPER.writeValueAsString(content);
      HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
      URI uri = URI.create(url);
      ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, entity, String.class);
      LOG.info(responseEntity.toString());
   }
}
