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
            var result = restTemplate.postForObject("http://123414132rr3r.432z65hrtgerfw.3243t5ztgrerf.324t5rfw:2345terfw/" + machine.id + "/error", machine, String::class.java)
            print(result)
        } else {
            error = FALSE
            response = "The Machine ist online"
            var result = restTemplate.postForObject("http://123414132rr3r.432z65hrtgerfw.3243t5ztgrerf.324t5rfw:2345terfw/" + machine.id + "/okay", machine, String::class.java)
            print(result)
        }



        val entity = createHttpEntity(machine)

        val responseFromAzure: ResponseEntity<String> = restTemplate.exchange<String>("https://iotplattform.azure-devices.net/devices/rfid/messages/events?api-version=2018-06-30", HttpMethod.POST,
                entity, String::class.java)

        return response

    }

    fun createHttpEntity(machine: Machine): HttpEntity<*>? {
        val headers = HttpHeaders()
        headers.add("Authorization", "SharedAccessSignature sr=iotplattform.azure-devices.net%2Fdevices%2Frfid&sig=QF1dsazoxXMejEdSVfvLaerE57BxnPdgWpth0uVeGII%3D&se=1618830901")
        return HttpEntity<Any?>(machine, headers)
    }

}