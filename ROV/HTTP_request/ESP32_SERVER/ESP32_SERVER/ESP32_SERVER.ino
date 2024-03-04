#include <WiFi.h>
#include <WebServer.h>
#include <ArduinoJson.h>

const int joystick_x_Pin = 33; //joystick, x-direction
const int joystick_y_Pin = 32; //oystick, Y-direction

// Replace with your network credentials
const char* ssid = "Ich weiss nicht";
const char* password = "12345678";
const char* MyHostName = "checkingthehostname";

IPAddress staticIP(192, 168, 181, 113);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);
IPAddress dns(192, 168, 181, 2);

WebServer server(80);

String joystick(int joystick_x_Pin, int joystick_y_Pin) {
  int x = analogRead(joystick_x_Pin);
  int y = analogRead(joystick_y_Pin);
  StaticJsonDocument<128> command;
  command["x"] = String(x);
  command["y"] = String(y);
  String sendCommand;
  serializeJson(command, sendCommand);
  return sendCommand;
}
  
  
// GET request handler
void handle_get() {
  String message = joystick(joystick_x_Pin, joystick_y_Pin);
  server.send(200, "application/json", message);
}


// POST request handler
void handle_post() {
  if (server.hasArg("data")) {
    String data = server.arg("data");
    Serial.println("Received POST data: " + data);
    // Process the received data here
  }
  server.send(200, "text/plain", "Data received");
}

void setup() {
  Serial.begin(115200);
  if (WiFi.config(staticIP, gateway, subnet, dns, dns) == false) {
   Serial.println("Configuration failed.");
  }
  WiFi.setHostname(MyHostName);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");

  server.on("/", handle_get);
  server.on("/post_data", HTTP_POST, handle_post);

  server.begin();
  Serial.println("Server started");
  // Print ESP32's IP & HostName
  Serial.println("\nConnected to the WiFi network");
  Serial.print("Local ESP32 IP: ");
  Serial.println(WiFi.localIP());
  Serial.print("ESP32 HostName: ");
  Serial.println(WiFi.getHostname());
}

void loop() {
  server.handleClient();
}
