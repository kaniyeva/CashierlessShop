#!/usr/bin/env python

import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

reader = SimpleMFRC522()

try:
    for i in range(0, 3):
                            text = raw_input('New data:')
                            print("Now place your tag to write")
                            reader.write(text)
                            print("Written")

finally:
        GPIO.cleanup()

