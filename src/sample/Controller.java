package sample;


import javafx.application.Platform;
import javafx.fxml.*;
import java.net.URL;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

public class Controller implements Initializable {
    Random rnd = new Random();
    int smt = 0;
    int prv = 0;
    int size;
    int posX = 200;
    int posY = 200;
    int psy = 0;
    int psx = 0;
    int status = 0;
    int speed;
    int baitX;
    int baitY;
    int turn = 10000;
    boolean frt = true;
    ArrayList<Rectangle> worm = new ArrayList();
    HashSet<Integer> points = new HashSet();
    Circle bait = new Circle();
    @FXML
    private ToggleButton easy,med,hard;
    @FXML
    private AnchorPane anc;

    @FXML
    private void start(){
        if (easy.isSelected()){
            size = 800;
            speed = 100;
            startGame();
        }
        else if (med.isSelected()){
            Main.res(600,600);
            size = 600;
            speed = 500;
            startGame();
        }
        else if (hard.isSelected()){
            Main.res(400,400);
            size = 400;
            speed = 200;
            startGame();
        }
        else{

        }
    }

    private void startGame(){
        if(!anc.getChildren().isEmpty()) anc.getChildren().clear();
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
            }else{
                if (smt < 3){
                    prv = smt;
                    turn = 1;
                    smt++;
                    frt = true;
                    psy = posY;
                    psx = posX;
                }
                else{
                    turn = 1;
                    prv = smt;
                    smt = 0;
                    frt = true;
                    psx = posX;
                    psy = posY;
                }
            }
        });
    }

    private void genBait(){
        baitX = rnd.nextInt(size/10-3)*10;
        baitY = rnd.nextInt(size/10-3)*10;
        bait.relocate(baitX,baitY);
        System.out.printf("%d , %d",baitX*10,baitY*10);
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
            worm.add(rc);
            anc.getChildren().add(rc);
            posY -= 10;
            genBait();
        }
    }
    private class End extends Thread{
        @Override
        public void run(){
            anc.getChildren().clear();
            anc.setStyle("-fx-background-color: firebrick");
        }
    }

    private class Move extends Thread{
        int t = 0;
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
                    switch(smt){
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
                }else{
                    if (smt == 0){
                        posY -= 10;
                        for (int i = 0 ; i < worm.size() ; i++ ){
                            if (i < turn){
                                worm.get(i).relocate(posX,posY+i*10);
                            }else{
                                if(frt){
                                    //psy = posY;
                                    t = i;
                                    frt = false;
                                }
                                switch (prv){
                                    case 1 :
                                        worm.get(i).relocate(posX-(i-t)*10,psy);
                                        break;
                                    case 3 :
                                        worm.get(i).relocate(posX+(i-t)*10,psy);
                                        break;
                                }
                            }
                        }
                    }
                    else if (smt == 1){
                        posX += 10;
                        for (int i = 0 ; i < worm.size() ; i++ ){
                            if (i < turn){
                                worm.get(i).relocate(posX-i*10,posY);
                            }else{
                                if(frt){
                                    //psx = posX;
                                    t = i;
                                    frt = false;
                                }
                                switch (prv){
                                    case 0 :
                                        worm.get(i).relocate(psx,posY+(i-t)*10);
                                        break;
                                    case 2 :
                                        worm.get(i).relocate(psx,posY-(i-t)*10);
                                        break;
                                }
                            }
                        }
                    }
                    else if (smt == 2){
                        posY += 10;
                        for (int i = 0 ; i < worm.size() ; i++ ){
                            if (i < turn){
                                worm.get(i).relocate(posX,posY-i*10);
                            }else{
                                if(frt){
                                    //psy = posY;
                                    t = i;
                                    frt = false;
                                }
                                switch (prv){
                                    case 1 :
                                        worm.get(i).relocate(posX-(i-t)*10,psy);
                                        break;
                                    case 3 :
                                        worm.get(i).relocate(posX+(i-t)*10,psy);
                                        break;
                                }
                            }
                        }
                    }
                    else {
                        posX -= 10;
                        for (int i = 0 ; i < worm.size() ; i++ ){
                            if (i < turn){
                                worm.get(i).relocate(posX+i*10,posY);
                            }else{
                                if(frt){
                                    //psx = posX;
                                    t = i;
                                    frt = false;
                                }
                                switch (prv){
                                    case 0 :
                                        worm.get(i).relocate(psx,posY+(i-t)*10);
                                        break;
                                    case 2 :
                                        worm.get(i).relocate(psx,posY-(i-t)*10);
                                        break;
                                }
                            }
                        }
                    }
                    turn++;
                    frt = true;
                    try{
                        Thread.sleep(speed);
                    }catch(Exception ex){
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        easy.setSelected(true);
    }
}
