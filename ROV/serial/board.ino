const int buttonPin = 2;
const int ledPin = 4;
const int joystick_x_Pin = 33; //joystick, x-direction
const int joystick_y_Pin = 32; //oystick, Y-direction

void setup() {
  Serial.begin(115200);
  pinMode(buttonPin, INPUT);
  pinMode(ledPin, OUTPUT);
}

void joystick(int joystick_x_Pin, int joystick_y_Pin, int &xpointer, int &ypointer, bool &lock, int &brightness) {
  int x = analogRead(joystick_x_Pin);
  int y = analogRead(joystick_y_Pin);
  button(lock);
  xpointer = x;
  ypointer = y;
  if (lock == HIGH) {
    if ((abs(x - 500) > 30)) {
      Serial.println("x:" + String(x));
      //led(brightness);
    }
    if (abs(y - 500) > 30) {
      Serial.println("y:" + String(y));
      //led(brightness);
    }
  }else{
    if ((abs(x - 500) > 30)) {
      Serial.println("x:" + String(x));
      led(brightness);
    }
    if (abs(y - 500) > 30) {
      Serial.println("y:" + String(y));
      led(brightness);
    }
  }
  
  
}

void button(bool &lock) {
  int buttonSignal = digitalRead(buttonPin);
  lock = buttonSignal;
  if (lock == HIGH) {
  }
}

void led(int &brightness) {
  int ledValue = (Serial.readString() == "1") ? min((brightness + 100), 254) : max((brightness- 100), 0);
  brightness = ledValue;
  analogWrite(ledPin, brightness);
  Serial.println("led value: " + String(brightness));
}

int xpointer, ypointer, brightness;
bool lock;
void loop() {
  joystick(joystick_x_Pin, joystick_y_Pin, xpointer, ypointer, lock, brightness);
}
