package com.will.weather.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.will.weather.representation.RobotContent;

@Service
public class CallbackService {

   Logger LOG = LoggerFactory.getLogger(CallbackService.class);

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                                                                       .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

   @Value(value = "${callback-robot-uri}")
   private String callbackRobotUri;

   public void sendToRobot(Map<String, String> headers, @RequestBody Map<String, Object> bodies) {
      try {
         String header = OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(headers);
         String body = OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(bodies);
         LOG.info(header);
         LOG.info(body);
         RobotSender.sendTo(callbackRobotUri, new RobotContent("Callback response: Headers: \n" + header + "\n" + "Body: \n" + body));
      } catch (Exception e) {
         LOG.error("Serialize callback fail", e);
      }
   }
}
