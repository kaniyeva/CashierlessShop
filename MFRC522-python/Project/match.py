from mfrc522 import SimpleMFRC522
import os
import RPi.GPIO as GPIO
import time

reader = SimpleMFRC522()
price_sum = 0
items = []

file1 = open("store.txt","r+")

match = file1.read()

try:
    for i in range(0, 3):
                        id, text = reader.read()
                        string = text.replace(" ","")
                        items.append(string)
                        print(string)
                        print(items)
                        time.sleep(1)
     
    items.sort()
    
    for x in items:
        price_sum += int(x.split(',')[1])
    total = "Total,"+str(price_sum)
    items.append(total)
    
    merge = ";".join(items)

    if merge == match:
	print("Welcome, Come Again");
    else:
	print("You might have mistakenly take something with you")

finally:
            GPIO.cleanup()
