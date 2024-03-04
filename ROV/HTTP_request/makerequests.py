import requests
import time
import json

url = "http://192.168.181.113"  # Replace with your desired URL
posturl = "http://192.168.181.113/post_data"

while True:
    lastTime = int(input("read for: (s)"))
    start_time = time.time()
    while not((time.time() - start_time) >= lastTime):
        boardResponse = requests.get(url)
        print("Response:", boardResponse.text)
        data = {
            "response": boardResponse.text
        }
        
        headers = {'Content-Type': 'application/json'}
        rovResponse = requests.post(posturl, data=json.dumps(data), headers=headers)
        print("Response:", rovResponse.text)   