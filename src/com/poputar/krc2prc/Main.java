package com.poputar.krc2prc;

import com.poputar.krc2prc.view.ViewTool;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = ViewTool.loadView("KRC2PRCTool.fxml");
        primaryStage.setTitle("酷狗歌词转换工具");
        primaryStage.setScene(new Scene(root, 550, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}