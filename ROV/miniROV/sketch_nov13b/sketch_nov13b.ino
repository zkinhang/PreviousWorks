#include <Servo.h>
#include <MPU6050.h>
//define some pinsss
//joystick
const int joystick_Lx_Pin = A0; // left joystick, x-direction
const int joystick_Ly_Pin = A1; // left joystick, Y-direction
const int joystick_Rx_Pin = A2; // right joystick, x-direction
const int joystick_Ry_Pin = A3; // right joystick, Y-direction


/*
//MPU6050 sensor
void sensor(){
  int16_t ax, ay, az; // Accelerometer readings
  int16_t gx, gy, gz; // Gyroscope readings
  mpu.getMotion6(&ax, &ay, &az, &gx, &gy, &gz); // Read sensor data
  Serial.print("Acc: ");
  Serial.print(ax);
  Serial.print(", ");
  Serial.print(ay);
  Serial.print(", ");
  Serial.print(az);
  Serial.print(" \t Gyro: ");
  Serial.print(gx);
  Serial.print(", ");
  Serial.print(gy);
  Serial.print(", ");
  Serial.println(gz);
  delay(100);
}
*/
void jokstick(int joystick_x_Pin, int joystick_y_Pin, int &xpointer, int &ypointer){ 
  //joystick
  int x = analogRead(joystick_x_Pin);
  int y = analogRead(joystick_y_Pin);
  // print via serial port
  /*
  Serial.print("(");
  Serial.print(x);
  Serial.print(",");
  Serial.print(y);
  Serial.println(")");
  */
//map the analog value from joystick to the range for servo
  xpointer = map(x, 0, 1023, 500, 2500); 
  ypointer = map(y, 0, 1023, 1000, 2000);
  //xpointer = &temp_xpointer;
  //ypointer = &temp_ypointer;
  //delay(100);
  Serial.print("(");
  Serial.print(xpointer);
  Serial.print(",");
  Serial.print(ypointer);
  Serial.println(")");
}

//led
const int ledPin = 9;
void led(){
  for(int i = 0; i < 255; i++){
    analogWrite(ledPin, i); // main function to send out the PWM value to the pin
    Serial.println(i); // also print the corresponding value
    delay(20);  // delay to slow down the print out
  }
}

//servo
const int Left_servoPin = 10;
const int Right_servoPin = 11;
const int servoMaxUs = 2000;     // max position, unit = 2000us = 2ms
const int servoMiddleUs = 1500;  // middle position,
const int servoMinUs = 1000;     // min position,
Servo LeftServo;
Servo RightServo;

//thruster
const int Left_thrusterPin = 5;
const int Right_thrusterPin = 6;
const int thrusterMaxUs = 2000;     // max position, unit = 2000us = 2ms
const int thrusterMiddleUs = 1500;  // middle position,
const int thrusterMinUs = 1000;     // min position,
Servo Leftthruster;
Servo Rightthruster;

void setup() {
  // put your setup code here, to run once:
  Wire.begin();
  Serial.begin(115200);
  int pointer;
  //servo setup
  LeftServo.attach(Left_servoPin);
  RightServo.attach(Right_servoPin);
  LeftServo.writeMicroseconds(servoMiddleUs); // set servo to mid-point
  RightServo.writeMicroseconds(servoMiddleUs);
  //thruster setup
  Leftthruster.attach(Left_thrusterPin);
  Rightthruster.attach(Right_thrusterPin);
  Leftthruster.writeMicroseconds(thrusterMiddleUs); // set thurster to mid-point
  Rightthruster.writeMicroseconds(thrusterMiddleUs);
  //mup setup
  /*
  mpu.initialize();
  mpu.setDLPFMode(3);
  mpu.setFullScaleGyroRange(3);
  */
}
int xpointer, ypointer;
void loop() {
  // put your main code here, to run repeatedly:
  jokstick(joystick_Lx_Pin, joystick_Ly_Pin, xpointer, ypointer);
  LeftServo.writeMicroseconds(xpointer);
  //Serial.print(xpointer);
  jokstick(joystick_Lx_Pin, joystick_Ly_Pin, xpointer, ypointer);
  Leftthruster.writeMicroseconds(ypointer);
  jokstick(joystick_Rx_Pin, joystick_Ry_Pin, xpointer, ypointer);
  RightServo.writeMicroseconds(xpointer);
  //Serial.print(xpointer);
  jokstick(joystick_Rx_Pin, joystick_Ry_Pin, xpointer, ypointer);
  Rightthruster.writeMicroseconds(ypointer);

  
  
  delay(100);
}