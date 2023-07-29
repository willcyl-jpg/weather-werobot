package com.bitanswer.weather.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.bitanswer.weather.middleware.WeatherMessage;
import com.bitanswer.weather.representation.RobotContent;
import com.bitanswer.weather.representation.RobotContentText;

@Service
public class WeatherService {

   Logger LOG = LoggerFactory.getLogger(WeatherService.class);

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                                                                       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

   @Value(value = "${weather-api.host}")
   private String weatherApiHost;
   @Value(value = "${weather-api.daily-uri}")
   private String weatherDailyUri;
   @Value(value = "${weather-api.key}")
   private String weatherApiKey;
   @Value(value = "${weather-api.locationId}")
   private String weatherLocation;
   @Value(value = "${weather-api.lang}")
   private String weatherLang;
   @Value(value = "${wechat-robot.uri}")
   private String robotUri;

   public void sendWeatherToRobot() {
      try {
         JsonNode weatherInfo = getWeatherInfo();
         LOG.debug(weatherInfo.toString());
         WeatherMessage weatherMessage = new WeatherMessage().parse(weatherInfo);
         RobotContentText robotContentText = new RobotContentText();
         robotContentText.setContent(weatherMessage.toMessage());
         RobotContent robotContent = new RobotContent();
         robotContent.setMsgType("text");
         robotContent.setText(robotContentText);
         sendMessage(robotContent);
      } catch (Exception e) {
         LOG.error("Something wrong...", e);
      }
   }

   private JsonNode getWeatherInfo() throws Exception {
      UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(weatherApiHost).path(weatherDailyUri)
                                                        .queryParam("location", weatherLocation)
                                                        .queryParam("key", weatherApiKey)
                                                        .queryParam("lang", weatherLang)
                                                        .build();
      URLConnection connection = uriComponents.toUri().toURL().openConnection();
      InputStream inputStream = connection.getInputStream();
      GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
      InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      try {
         StringBuilder stringBuilder = new StringBuilder();
         bufferedReader.lines().forEach(stringBuilder::append);
         return OBJECT_MAPPER.readTree(stringBuilder.toString());
      } finally {
         inputStream.close();
         gzipInputStream.close();
         inputStreamReader.close();
         bufferedReader.close();
      }
   }

   private void sendMessage(RobotContent content) throws Exception {
      RestTemplate restTemplate = new RestTemplateBuilder().build();
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      String body = OBJECT_MAPPER.writeValueAsString(content);
      HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
      URI uri = URI.create(robotUri);
      ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, entity, String.class);
      LOG.info(responseEntity.toString());
   }
}
