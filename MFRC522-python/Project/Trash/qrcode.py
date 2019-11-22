import pyqrcode
from pyqrcode import QRCode

string = 'Baal'

url = pyqrcode.create(string)

url.png("myqrcode.png", scale = 8)