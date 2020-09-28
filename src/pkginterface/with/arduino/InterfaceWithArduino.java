package pkginterface.with.arduino;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.sintef.jarduino.AnalogPin;
import org.sintef.jarduino.DigitalPin;
import org.sintef.jarduino.DigitalState;
import org.sintef.jarduino.PinMode;
import org.sintef.jarduino.comm.Serial4JArduino;
public class InterfaceWithArduino extends Application {
    static String str1; // create string to save the selected pin
    static String str2; // create string to save the selected pin
    static Button bOn = new Button("ON/Off"); //button to switch on/off
    static Button bIn = new Button("I/O");//button to select pin input or output
    static Button bRead = new Button("Read");//button to read data
    static Button bReset = new Button("Reset");//button to reset
    static ChoiceBox<String> choiceDigital = new ChoiceBox();//choice box to chose  digital pins
    static  ChoiceBox<String> choiceAnalog = new ChoiceBox();//choice box to chose  analog pins
    //create Radiobuttons
    static RadioButton r1= new RadioButton("Input");
    static RadioButton r2= new RadioButton("Output");
    static ToggleGroup group2 = new ToggleGroup();
    static  Alert alert = new Alert(AlertType.INFORMATION);
    static boolean flag = true ;
    @Override
    public void start(Stage primaryStage) {
     //creat Vbox
     VBox vbox1= new VBox(10);
     VBox vbox3= new VBox(10); 
     HBox Hbox = new HBox(10);
      //create Labels
     Label L1 = new Label("Digital pins");
     Label L2 = new Label("Analog pins");
     Label L3 = new Label("I/O");
     //adjust Label's text color
     L1.setTextFill(Color.WHITE);
     L2.setTextFill(Color.WHITE);
     L3.setTextFill(Color.WHITE);
     //adjust button's width 
     bOn.setPrefWidth(70);
     bIn.setPrefWidth(70);
     bRead.setPrefWidth(70);
     bReset.setPrefWidth(70);
     
     
     Hbox.getChildren().addAll(bOn,bReset);
     Hbox.setAlignment(Pos.BOTTOM_RIGHT);
     // add buutons on Vbox 
     vbox3.getChildren().addAll(bIn,bRead);
     r1.setToggleGroup(group2);
     r2.setToggleGroup(group2);
     //adjust colors of radiobutton
     r1.setTextFill(Color.WHITE);
     r2.setTextFill(Color.WHITE);
   
     vbox1.getChildren().addAll(r1,r2);
     choiceDigital.getItems().addAll("PIN_0","PIN_1","PIN_2","PIN_3","PIN_4","PIN_5","PIN_6","PIN_7");
     choiceDigital.getItems().addAll("PIN_8","PIN_9","PIN_10","PIN_11","PIN_12","PIN_13");
     choiceAnalog.getItems().addAll("A_0","A_1","A_2","A_3","A_4","A_5");
     GridPane grid = new GridPane();
     grid.setHgap(60);
     grid.setVgap(10);
     grid.setPadding(new Insets(25,0,0,0));
     grid.add(L1, 0, 0);
     grid.add(L2, 1, 0);
     grid.add(L3, 2, 0);
     grid.add(choiceDigital, 0, 1);
     grid.add(choiceAnalog, 1, 1);
     grid.add(vbox1, 2, 1);
     grid.add(vbox3, 3, 1);
     grid.setAlignment(Pos.TOP_LEFT);
     alert.setTitle("The voltage value ");
     alert.setHeaderText("The voltage value");
     
     Image image = new Image("https://i.pinimg.com/originals/ba/1d/cc/ba1dcc1df24872ab5fd175f8d9b4cc31.jpg");
     ImageView iv = new ImageView(image);
     iv.setImage(image);
     iv.setFitWidth(550);
     iv.setFitHeight(400);
     vbox3.setAlignment(Pos.TOP_RIGHT);
     BorderPane pane = new BorderPane();
     pane.getChildren().add(iv);
     pane.setTop(grid);

//     vbox2.setLayoutY(450);
//     pane.setLeft(vbox2);
     pane.setBottom(Hbox);
     Scene s = new Scene(pane,500,380);
     primaryStage.setResizable(false);
     primaryStage.setScene(s);
     primaryStage.setTitle("Computer to Arduino");
     primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      try {
      String serialPort;
      if (args.length == 1) {
            serialPort = args[0];
      } else {
            serialPort = Serial4JArduino.selectSerialPort();
      }
      arduino ard = new arduino(serialPort);
      ard.runArduinoProcess();
      turnOn(ard);
      reset ();
      read (ard);
      inputAndOutput(ard);
      launch(args);
      }catch (Exception e ){
      launch(args);
      }
    }
    static String getDigitalChoice(ChoiceBox<String> choiceDigital){
        String digital;
        digital= choiceDigital.getValue();
        return digital;
    }
     static String getAnalogChoice(ChoiceBox<String> choiceAnalog){
        String Analog;
        Analog= choiceAnalog.getValue();
        return Analog;
    }
     static void turnOn (arduino ard){
      str1=null;
      str2=null;
      bOn.setOnAction(e->{
     str1=getDigitalChoice(choiceDigital);
     str2=getAnalogChoice(choiceAnalog);
     if ((str1!=null )&&(str2==null)){
     if (flag==true)
     {
         ard.SwitchOnbutton(str1);
         flag = !flag;
     }else {
         ard.SwitchOffbutton(str1);
         flag = !flag;
     } 
     }
     else if((str1==null )&&(str2!=null))
     {
          if (flag==true){
         ard.SwitchOnbutton(str2);
          flag = !flag;
     }else {
              ard.SwitchOffbutton(str2);
              flag = !flag;
          }
     }
         else if((str1!=null )&&(str2!=null)){
             if (flag==true){ 
            ard.SwitchOnbutton(str1);
            ard.SwitchOnbutton(str2);
              flag = !flag;
             }else{
                 ard.SwitchOffbutton(str1);
                 ard.SwitchOffbutton(str2);
                 flag = !flag;
          }  
     }
     });
     }
     static void reset (){
       bReset.setOnAction(e->{
       choiceAnalog.setValue(null);
       choiceDigital.setValue(null);
       r1.setSelected(false);
       r2.setSelected(false); 
        }); 
     }
      static void read (arduino ard){
         str1=null;
         str2=null;
         bRead.setOnAction(e->{
         str1=getDigitalChoice(choiceDigital);
         str2=getAnalogChoice(choiceAnalog);
         DigitalState s ;      
         String s1;
          if ((str1!=null )&&(str2==null)){
          s=ard.DigitalRead(str1);
          alert.setContentText(str1+" is "+s);
          alert.showAndWait();
          }if ((str1==null )&&(str2!=null)){
          s1=ard.analogread(str2);
          alert.setContentText("The voltage at pin "+str2+" is "+s1+" volt");
          alert.showAndWait();
          } if((str1!=null )&&(str2!=null)) {
          s=ard.DigitalRead(str1);
          s1=ard.analogread(str2);
          alert.setContentText(str1+" is "+s+"\n"+"The voltage at pin "+str2+" is "+s1+" volt");
          alert.showAndWait();
          }
          });
          
      }
      static void inputAndOutput (arduino ard){
      str1=null;
      str2=null;
      bIn.setOnAction(e->{
      str1=getDigitalChoice(choiceDigital);
      str2=getAnalogChoice(choiceAnalog);
      if(r1.isSelected()&&(str1!=null )){
      ard.pinMode(DigitalPin.valueOf(str1), PinMode.INPUT);
      }else if (r1.isSelected()&&(str1==null )&&(str2!=null)){
      ard.pinMode(DigitalPin.valueOf(str2), PinMode.INPUT);
      }else if (r1.isSelected()&&(str1!=null )&&(str2!=null)){
      ard.pinMode(DigitalPin.valueOf(str1), PinMode.INPUT);
      ard.pinMode(DigitalPin.valueOf(str2), PinMode.INPUT);
      }
      if(r2.isSelected()&&(str1!=null )){
      ard.pinMode(DigitalPin.valueOf(str1), PinMode.OUTPUT);
      }else if (r2.isSelected()&&(str1==null )&&(str2!=null)){
      ard.pinMode(DigitalPin.valueOf(str2), PinMode.OUTPUT);
      }else if (r2.isSelected()&&(str1!=null )&&(str2!=null)){
      ard.pinMode(DigitalPin.valueOf(str1), PinMode.OUTPUT);
      ard.pinMode(DigitalPin.valueOf(str2), PinMode.OUTPUT);
      }
      });
      
      }
}
    

    
   
