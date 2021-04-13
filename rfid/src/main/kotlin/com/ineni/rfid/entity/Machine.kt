package com.ineni.rfid.entity

import org.springframework.stereotype.Component
import java.lang.Boolean.TRUE

@Component
data class Machine(val id: String = "xxx", var status: Boolean = TRUE) {
}