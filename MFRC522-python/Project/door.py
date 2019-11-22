#!/usr/bin/env python
from gpiozero import Servo
import RPi.GPIO as GPIO
from time import sleep
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

myGPIO=17
servo = Servo(myGPIO)

cred = credentials.Certificate("/home/pi/Project/cashierless-shop-ndub-firebase-adminsdk-tkjtb-3dc4ccbda2.json")
firebase_admin.initialize_app(cred, {'databaseURL':'https://cashierless-shop-ndub.firebaseio.com/'})

ref = db.reference('Door').child('Status')
#print(ref.get())

while True:
    if ref.get() == 'Close':
        servo.min()
        print(ref.get())
    if ref.get() == 'Open':
        servo.max()
        print(ref.get())
    sleep(5)