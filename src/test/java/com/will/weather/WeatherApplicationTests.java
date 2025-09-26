package com.will.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.will.weather.middleware.WeatherMessage;
import com.will.weather.service.WeatherService;

@SpringBootTest
class WeatherApplicationTests {

   @Autowired
   private WeatherService service;

   @Test
   void contextLoads() {
      service.sendWeatherToRobot();
   }

   @Test
   void testParse() throws JsonProcessingException {
      String json = "{\"code\":\"200\",\"updateTime\":\"2023-07-29T09:35+08:00\",\"fxLink\":\"https://www.qweather.com/weather/haidian-101010200.html\",\"daily\":[{\"fxDate\":\"2023-07-29\",\"sunrise\":\"05:09\",\"sunset\":\"19:35\",\"moonrise\":\"16:46\",\"moonset\":\"00:53\",\"moonPhase\":\"盈凸月\",\"moonPhaseIcon\":\"803\",\"tempMax\":\"30\",\"tempMin\":\"24\",\"iconDay\":\"305\",\"textDay\":\"小雨\",\"iconNight\":\"307\",\"textNight\":\"大雨\",\"wind360Day\":\"135\",\"windDirDay\":\"东南风\",\"windScaleDay\":\"1-2\",\"windSpeedDay\":\"3\",\"wind360Night\":\"45\",\"windDirNight\":\"东北风\",\"windScaleNight\":\"1-2\",\"windSpeedNight\":\"3\",\"humidity\":\"98\",\"precip\":\"2.5\",\"pressure\":\"1000\",\"vis\":\"2\",\"cloud\":\"64\",\"uvIndex\":\"4\"},{\"fxDate\":\"2023-07-30\",\"sunrise\":\"05:10\",\"sunset\":\"19:34\",\"moonrise\":\"17:52\",\"moonset\":\"01:43\",\"moonPhase\":\"盈凸月\",\"moonPhaseIcon\":\"803\",\"tempMax\":\"27\",\"tempMin\":\"24\",\"iconDay\":\"310\",\"textDay\":\"暴雨\",\"iconNight\":\"307\",\"textNight\":\"大雨\",\"wind360Day\":\"90\",\"windDirDay\":\"东风\",\"windScaleDay\":\"1-2\",\"windSpeedDay\":\"3\",\"wind360Night\":\"90\",\"windDirNight\":\"东风\",\"windScaleNight\":\"3-4\",\"windSpeedNight\":\"16\",\"humidity\":\"96\",\"precip\":\"44.7\",\"pressure\":\"1002\",\"vis\":\"11\",\"cloud\":\"84\",\"uvIndex\":\"2\"},{\"fxDate\":\"2023-07-31\",\"sunrise\":\"05:11\",\"sunset\":\"19:33\",\"moonrise\":\"18:49\",\"moonset\":\"02:45\",\"moonPhase\":\"盈凸月\",\"moonPhaseIcon\":\"803\",\"tempMax\":\"28\",\"tempMin\":\"24\",\"iconDay\":\"310\",\"textDay\":\"暴雨\",\"iconNight\":\"307\",\"textNight\":\"大雨\",\"wind360Day\":\"90\",\"windDirDay\":\"东风\",\"windScaleDay\":\"3-4\",\"windSpeedDay\":\"16\",\"wind360Night\":\"135\",\"windDirNight\":\"东南风\",\"windScaleNight\":\"1-2\",\"windSpeedNight\":\"3\",\"humidity\":\"94\",\"precip\":\"76.0\",\"pressure\":\"1000\",\"vis\":\"19\",\"cloud\":\"100\",\"uvIndex\":\"2\"}],\"refer\":{\"sources\":[\"QWeather\",\"NMC\",\"ECMWF\"],\"license\":[\"CC BY-SA 4.0\"]}}";
      ObjectMapper objectMapper = new ObjectMapper();
      WeatherMessage parse = new WeatherMessage().parse(objectMapper.readTree(json));
      System.out.println(parse.toMessage());
   }

}
