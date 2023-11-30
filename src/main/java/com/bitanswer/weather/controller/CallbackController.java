package com.bitanswer.weather.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bitanswer.weather.service.CallbackService;

@RestController
public class CallbackController {

   @Autowired
   private CallbackService callbackService;

   @PostMapping(value = "/callback")
   public String callback(@RequestHeader Map<String, String> headers, @RequestBody Map<String, Object> body) {
      callbackService.sendToRobot(headers, body);
      return "success";
   }
}
