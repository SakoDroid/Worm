package sample;

/*
** +-----------------------------------------------+
** | Directions :                                  |
** |                                               |
** |     0                                         |
** |  3     1                                      |
** |     2                                         |
** +-----------------------------------------------+
** | Speed increase : 10 px/s by every bait eaten. |
** +-----------------------------------------------+
** | Written by : SAKO.                            |
** +-----------------------------------------------+
 */

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Controller implements Initializable {
    Random rnd = new Random();
    private static int len = 2;
    private int smt;
    private int size;
    private int posX;
    private int posY;
    private int status = 0;
    private int speed;
    private int baitX;
    private int baitY;
    private static Hardness drj;
    private ArrayList<Rectangle> worm = new ArrayList();
    private ArrayList<Point> points = new ArrayList();
    private Circle bait = new Circle();
    private Label scr = new Label();
    @FXML
    private ToggleButton easy,med,hard;
    @FXML
    private AnchorPane anc;
    @FXML
    private Label score;
    @FXML
    private void start(){
        if (easy.isSelected()){
            drj = Hardness.Easy;
            size = 800;
            config();
            startGame();
        }
        else if (med.isSelected()){
            drj = Hardness.Medium;
            Main.res(600,600);
            size = 600;
            config();
            startGame();
        }
        else if (hard.isSelected()){
            drj = Hardness.Hard;
            Main.res(400,400);
            size = 400;
            config();
            startGame();
        }
        else{

        }
    }
    private void config(){
        worm.clear();
        points.clear();
        posX = 200;
        posY = 200;
        len = 2;
        smt = 0;
        scr.setText("Score : 0");
        switch (drj){
            case Easy:
                speed = 300;
                break;
            case Medium:
                speed = 400;
            default :
                speed = 200;
        }
    }
    private void startGame(){
        config();
        if(!anc.getChildren().isEmpty()) anc.getChildren().clear();
        points.add(new Point(posX,posY));
        anc.setStyle("-fx-background-color: yellow;");
        this.genBait();
        this.createWorm();
        this.showWorm();
        Label lbl = new Label("Click to start!");
        lbl.relocate(30,30);
        lbl.setFont(new Font(30));
        this.anc.getChildren().add(lbl);
        this.anc.setOnMouseClicked((MouseEvent evt)->{
            if (status == 0){
                lbl.setVisible(false);
                this.move();
                status = 1;
                scr.relocate(size-80,2);
                anc.getChildren().add(scr);
            }else{
                if (smt < 3)smt++;
                else smt = 0;
            }
        });
    }

    private void genBait(){
        baitX = rnd.nextInt(size/10-3)*10;
        baitY = rnd.nextInt(size/10-3)*10;
        bait.relocate(baitX,baitY);
    }

    private void createWorm(){
        for (int i = 0 ; i < 2 ; i++){
            Rectangle rc = new Rectangle();
            rc.setWidth(10);
            rc.setHeight(10);
            rc.relocate(posX,posY+i*10);
            rc.setFill(Color.DARKGREY);
            worm.add(rc);

        }
    }
    private void showWorm(){
        for (Rectangle i : worm){
            anc.getChildren().add(i);
        }
        anc.getChildren().add(bait);
        bait.setRadius(5);
        bait.setFill(Color.RED);
    }
    private void move(){
        Thread mv = new Move();
        mv.start();
    }
    private class add extends Thread{
        @Override
        public void run(){
            Rectangle rc = new Rectangle();
            rc.setWidth(10);
            rc.setHeight(10);
            rc.setFill(Color.DARKGREY);
            rc.setVisible(false);
            worm.add(rc);
            anc.getChildren().add(rc);
            genBait();
            scr.setText("Score : " + (len-2));
            speed -= 10;
        }
    }
    private class End extends Thread{
        @Override
        public void run(){
            try{
                if(!anc.getChildren().isEmpty()){
                    anc.getChildren().clear();
                    anc.setStyle("-fx-background-color: firebrick");
                    Label los = new Label("YOU LOST!!");
                    los.setFont(new Font(size/10));
                    los.relocate(size/4,Math.round(size/3)-30);
                    Label score = new Label("Your score : " + (len-2));
                    score.relocate(size-120,3);
                    Button btn1 = new Button("Play again");
                    btn1.setPrefWidth(100);
                    btn1.setOnAction((ActionEvent evt)->{
                        startGame();
                    });
                    Button btn2 = new Button("Main menu");
                    btn2.setPrefWidth(100);
                    btn2.setOnAction((ActionEvent evt)->{
                        try{
                            Main.restart(new Scene(FXMLLoader.load(getClass().getResource("sample.fxml"))));
                        }catch(Exception ex){
                            System.out.println(ex);
                        }
                    });
                    if ( size == 800){
                        btn1.relocate(size/2 - 50,los.getLayoutY() + 115);
                        btn2.relocate(size/2 - 50,los.getLayoutY() + 155);
                    }
                    else {
                        btn1.relocate(size/2 - 50,los.getLayoutY() + 95);
                        btn2.relocate(size/2 - 50,los.getLayoutY() + 135);
                    }
                    anc.getChildren().addAll(los,score,btn1,btn2);
                }
            }catch(Exception ex){
                //System.out.println(ex);
                ex.printStackTrace();
            }
        }
    }

    private class Move extends Thread{
        @Override
        public void run(){
            Platform.setImplicitExit(false);
            while(status == 1){
                if (posX <= -3 || posX >= size+3 || posY <= -3 || posY >= size+3){
                    status = 0;
                    Thread end = new End();
                    Platform.runLater(end);
                }else if(posX == baitX && posY == baitY) {
                    Thread k = new add();
                    Platform.runLater(k);
                    len++;
                }else{
                    switch (smt){
                        case 0 :
                            posY -= 10;
                            break;
                        case 1 :
                            posX += 10;
                            break;
                        case 2 :
                            posY += 10;
                            break;
                        default :
                            posX -= 10;
                    }
                    points.add(new Point(posX,posY));
                    for (int i = points.size()-1, j = 0 ; j < len ; i--,j++){
                        worm.get(j).relocate(points.get(i).x,points.get(i).y);
                        worm.get(j).setVisible(true);
                    }
                    for (int i = points.size()-2, j = 0; j < len && i >= 0 ; i--,j++){
                        if (posX == points.get(i).x && posY == points.get(i).y){
                            status = 0;
                            Thread end = new End();
                            Platform.runLater(end);
                        }
                    }
                }
                try{
                    Thread.sleep(speed);
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        easy.setSelected(true);
    }
}
