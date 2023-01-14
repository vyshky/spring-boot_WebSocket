package com.example.application.server.controllers;

import com.example.application.server.models.Greeting;
import com.example.application.server.models.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    @MessageMapping("/send/hello/name")   // @MessageMapping мапится с config.setApplicationDestinationPrefixes("/prefix"); при этом в таком виде prefix/send/hello/name
    @SendTo("/server/get/hello") // server мапиться с config.enableSimpleBroker("/server"); при этом он может смапится как /server или /server/get или /server/get/hello
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/send/hello/admin")   // @MessageMapping мапится с config.setApplicationDestinationPrefixes("/prefix"); при этом в таком виде prefix/send/hello/admin
    @SendTo("/server/get/admin") // server мапиться с config.enableSimpleBroker("/server");
    public Greeting greeting2(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, Admin!");
    }
}
