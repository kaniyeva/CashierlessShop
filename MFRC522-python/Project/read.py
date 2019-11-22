#!/usr/bin/env python
from qrtools import QR
import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
import time
import os
import pyqrcode
from pyqrcode import QRCode

reader = SimpleMFRC522()
myString = ""
items = []
items_split = None
string = None
price_sum = 0

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
    print(price_sum)
    
    merge = ";".join(items)

    file1 = open("store.txt","w")
    file1.write(merge)
    file1.close()
    print(merge)
    
    url = pyqrcode.create(merge)
    url.png("Purchase.png", scale = 8)
    
finally:
            GPIO.cleanup()