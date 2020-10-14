package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import Anim.*;

public class Main extends Application {

    static Stage stg = new Stage();
    @Override
    public void start(Stage kj) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stg.setTitle("Worm by Reza!");
        stg.setScene(new Scene(root));
        stg.setResizable(false);
        stg.show();
    }

    public static void res(int width,int height){
        Resize.resize(stg,width,height,3);

    }
    public static void restart(Scene scene){
        stg.setWidth(800);
        stg.setHeight(800);
        stg.setScene(scene);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
