package com.poputar.krc2prc.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;


public class ViewTool {

    public static Parent loadView(String viewName){
        try {
            return FXMLLoader.load(ViewTool.class.getResource(viewName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("View resource not found for name "+viewName);
        }
    }
}
