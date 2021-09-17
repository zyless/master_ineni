package com.ineni.rfid.controller

import com.ineni.rfid.entity.Machine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.io.File
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import java.util.*
import java.util.concurrent.TimeUnit


@RestController
@RequestMapping("/Machine_A")
class RfidController {

    private var error: Boolean = FALSE

    @Autowired
    lateinit var machine: Machine

    @Autowired
    lateinit var restTemplate: RestTemplate

    @GetMapping("/error")
    fun returnText(): String {
        var response: String? = null

        Runtime.getRuntime().exec("python3 Reader.py")
        TimeUnit.SECONDS.sleep(5)

        val myFile = File("rfid.txt")
        val myScanner = Scanner(myFile)
        machine.errormsg = myScanner.nextLine()

        if (error == FALSE) {
            error = TRUE
            response = "The Machine is offline"
            var result = restTemplate.getForObject("https://xxxx.net/" + machine.id + "/error", String::class.java)
            print(result)
            return response
        } else {
            error = FALSE
            response = "The Machine ist online"
            var result = restTemplate.getForObject("https://xxxx.net/" + machine.id + "/resolved", String::class.java)
            print(result)
            return response
        }



    }


}
