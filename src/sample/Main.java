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
        Resize.resize(stg,width,height);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
