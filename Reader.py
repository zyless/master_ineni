#!/usr/bin/env python

import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

reader = SimpleMFRC522()

try:
        id, text = reader.read()
        print(id)
        print(text)
        file = open("machine.txt", "w")
        file.write(str(text))
        file.close()
finally:
        GPIO.cleanup()

