package Anim;
import javafx.stage.*;

public class Resize {

    private static class Smaller extends Thread{

        int width;
        int height;
        Stage stg;
        int cw;
        int ch;

        public Smaller(Stage stg,int width,int height){
            this.width = width;
            this.height = height;
            this.cw = (int)stg.getWidth();
            this.ch = (int)stg.getHeight();
            this.stg = stg;
        }

        @Override
        public void run(){
            for (double i = cw ; i > width ; i--){
                stg.setWidth(i);
                try{
                    Thread.sleep(3);
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
            for (double i = ch ; i > height ; i--){
                stg.setHeight(i);
                try{
                    Thread.sleep(5);
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
        }
    }
    public static void resize(Stage stg,int width,int height){
        Thread rs = new Smaller(stg,width,height);
        rs.start();
    }
}
