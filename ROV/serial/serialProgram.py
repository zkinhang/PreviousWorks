import serial
import makerequests

ser = serial.Serial('COM5', 115200)

while True:
    if ser.in_waiting > 0:
        line = ser.readline().decode('utf-8').rstrip()
        params = {line[0], line[2:]}
        response = makerequests.get("http://192.168.181.241", params=line)
        
        print(line)