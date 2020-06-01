import RPi.GPIO as GPIO
import SimpleMFRC522

reader = SimpleMFRC522.SimpleMFRC522()

try:
        text= raw_input('Enter New Data to write on Card:')
        print("Now place your tag to write...")
        reader.write(text)
        print("Data Written successfully")
finally:
        GPIO.cleanup()

        
