package com.interfazsv.cat.detail;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hardel
 */
public class DetailLoader extends Application {
    private double _x, _y;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Detail.fxml"));
        Scene scene = new Scene(root);
        
        scene.setOnMousePressed(event -> {
            _x = event.getSceneX();
            _y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - _x);
            primaryStage.setY(event.getScreenY() - _y);
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
