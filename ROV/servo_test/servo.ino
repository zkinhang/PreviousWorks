#include <Servo.h>
#include <Wire.h>


const int buttonPin = 2;
const int servoPin = 10;

Servo Servo;

void button(int buttonPin){
  bool value = digitalRead(buttonPin);

}

void setup() {
  // put your setup code here, to run once:
  Wire.begin();
  Serial.begin(115200);
  Servo.attach(servoPin);
}

bool buttonState;
int servoValue = 0;
void loop() {
  buttonState = digitalRead(buttonPin);

  if (buttonState == HIGH){
    servoValue = min((servoValue + 1), 1750);//inside
    Serial.println(servoValue);
    Servo.writeMicroseconds(servoValue);
  }
  else{
    servoValue = max((servoValue - 1), 800); //outside
    Servo.writeMicroseconds(servoValue);
  }

  // put your main code here, to run repeatedly:

}
