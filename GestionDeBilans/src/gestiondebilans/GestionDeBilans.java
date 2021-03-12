/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondebilans;

import GestionBilan.services.QuestionService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Islem Oueslati
 */
public class GestionDeBilans extends Application {

@Override
    public void start(Stage primaryStage) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("../views/BilansPeriodiques.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Bilans periodiques de stage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
