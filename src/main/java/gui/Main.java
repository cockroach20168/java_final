package gui;

import beings.*;
import formations.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import other.CalabashCompare;
import other.CalabashGroup;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

import java.io.File;
import java.net.URL;
import javafx.scene.*;

public class Main extends Application{
    private Controller controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            //Main.initWord();
            System.out.println(getClass().getResource("/sample.fxml"));
            //assert (false);
            URL fxmlLocation = getClass().getResource("/sample.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(fxmlLocation);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.myInit();
            //Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
            Scene scene = new Scene(root);
            scene.setOnKeyTyped(event -> {
                System.out.println(event.getCode()+" pressed");
                System.out.println("    event.getCharacter()=" + event.getCharacter()+"+");
                if(controller.getIsEnd()){
                    String temp = event.getCharacter();
                    String temp1 = " ";
                    if(temp.equals(" ")){
                        System.out.println("start game");
                        //System.exit(-1);
                        controller.startGame();
                    }
                    else if(temp.equals("l")){
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Open Resource File");
                        File recordFile = fileChooser.showOpenDialog(primaryStage);
                        controller.loadRecord(recordFile);
                    }
                }
                else{
                    System.out.println("the game now is not end");
                }
            });
            primaryStage.setTitle("My Application");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Main "+Thread.currentThread().getName());
            //controller.startGame();
            //controller.testImageViewMove();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
