package com.practice.springcloud.config.server.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luo Bao Ding on 2018/5/7
 */
@RestController
@RefreshScope
public class MessageRestController {
    @Value("${my.message:Hello default}")
    private String message;


    private final Environment environment;

    public MessageRestController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping("/my/message")
    public String getMessage() {
        return this.message;
    }

    @RequestMapping(path={"/my/env/{key}","/my/env"})
    public Object myEnv(@PathVariable(name = "key", required = false) String key) {
        Map<String, Object> map = new HashMap<>();
        if (key == null) {
            key = "eureka.instance.prefer-ip-address";
            map.put(key, environment.getProperty("eureka.instance.prefer-ip-address"));
        }
        /*else if ("all".equals(key)) {
            return "forward:/actuator/env";//no use
        }*/
        else {
            map.put(key, environment.getProperty(key));
        }
        return map;
    }

}
