import firebase_admin
from gpiozero import Servo
from time import sleep
from firebase_admin import credentials
from firebase_admin import db
import RPi.GPIO as GPIO


myGPIO=17
servo = Servo(myGPIO)

cred = credentials.Certificate("/home/pi/Project/cashierless-shop-ndub-firebase-adminsdk-tkjtb-3dc4ccbda2.json")
firebase_admin.initialize_app(cred, {'databaseURL':'https://cashierless-shop-ndub.firebaseio.com/'})

ref = db.reference('Door').child('Status')
print(ref)

while True:
    ref = db.reference('Door').child('Status')
    print(ref)
    while ref == 'Close':
        #servo.min()
        #GPIO.cleanup()
        print("After check1"+str(ref))
    while ref == 'Open':
        #servo.max()
        #GPIO.cleanup()
        print("After check2"+str(ref))
    sleep(10)
