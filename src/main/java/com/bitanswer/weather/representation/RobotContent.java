package com.bitanswer.weather.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RobotContent {
   @JsonProperty(value = "msgtype")
   private String           msgType;
   private RobotContentText text;
}
