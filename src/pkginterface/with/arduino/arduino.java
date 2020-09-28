
package pkginterface.with.arduino;

import org.sintef.jarduino.AnalogPin;
import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.JArduino;
import org.sintef.jarduino.PinMode;


public class arduino extends JArduino{
    public arduino(String port) {
        super(port);
    }
    @Override
    protected void setup() {
         
    }

    @Override
    protected void loop() {
   
    }
    public void SwitchOnbutton (String digitalpin ){
        try {
        pinMode(DigitalPin.valueOf(digitalpin), PinMode.OUTPUT);
        digitalWrite(DigitalPin.valueOf(digitalpin), DigitalState.HIGH);
        } catch(NullPointerException e) {
          
        }
    }
    public void SwitchOffbutton (String digitalpin ){
        try {
        pinMode(DigitalPin.valueOf(digitalpin), PinMode.OUTPUT);
        digitalWrite(DigitalPin.valueOf(digitalpin), DigitalState.LOW);
        } catch(NullPointerException e) {
          
        }
    }
    public DigitalState DigitalRead (String digitalpin ){
    String s;
    pinMode(DigitalPin.valueOf(digitalpin), PinMode.INPUT);
    DigitalState digit  = digitalRead(DigitalPin.valueOf(digitalpin));
    delay(1000);
    return digit;

    }
    public String analogread(String sr ){
     String s;
    short analogRead = analogRead(AnalogPin.valueOf(sr));
    analogRead=(short) ((analogRead*5)/1024);
    return  String.valueOf(analogRead);
    }
}
   
