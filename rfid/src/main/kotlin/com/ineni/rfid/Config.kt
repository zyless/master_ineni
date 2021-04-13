package com.ineni.rfid

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate




@Configuration
class Config {

    @Bean
    fun restTemplate(): RestTemplate? {
        return RestTemplate()
    }

}