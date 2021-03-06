/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import tools.Myconn;
/**
 * FXML Controller class
 *
 * @author ramzuss
 */
public class GererProfileController implements Initializable {
Label lab;
  @FXML
    private TextField username;

    @FXML
    private TextField fullname;

    @FXML
    private TextField addresse;
    @FXML
    private Label email;
    @FXML
    private Button logout;
    @FXML
    private Button btnaccueil;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
  public void seTtext(String username,String fullname,String adresse,String email){
    this.username.setText(username);
        this.fullname.setText(fullname);
    this.addresse.setText(adresse);
      this.email.setText(email);

    }

    @FXML
    void modifier(ActionEvent event) {
Connection connection = Myconn.getInstance().getConnection();
        PreparedStatement stmt;

        try {
            stmt = connection.prepareStatement("UPDATE user SET user_name=?, full_name=?, address=? WHERE email=?");
            
            stmt.setString(4, (email.getText()));
            
              
           
            stmt.setString(1, username.getText());
            stmt.setString(2, fullname.getText());
           
              stmt.setString(3, addresse.getText());
            
 

              int i = stmt.executeUpdate();

            System.out.println(i);
            

        }catch (Exception e){
            e.printStackTrace();
        }
       
    
    }


  PreparedStatement pst = null;
     ResultSet rs = null;
    @FXML
     public void Accueil () throws Exception{  
          
    Connection cnx = Myconn.getInstance().getConnection();
    String sql = "Select * from user where id_user = ?  ";
        
    try {
            pst = cnx.prepareStatement(sql);
            pst.setString(1,LoginController.logged+"");
           
            
          
           
            rs = pst.executeQuery();
            
            if(rs.next()){ 
               
               
            String role =rs.getString(7);
                if(role.equals("responsable des stages"))
                {     
                  
             String fullname=rs.getString(3);
             String email=rs.getString(4);
   FXMLLoader loader=new FXMLLoader();
   loader.setLocation(getClass().getResource("accueilResponsable.fxml"));
        try {
            loader.load();
        } catch (Exception e) {
        }
        AccueilResponsableController acc=loader.getController();
        acc.seTtext(fullname,email);
        Parent p=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(p));
        stage.show();
     
      btnaccueil.getScene().getWindow().hide();  //pour fermer interface login

               
            }  
                 if(role.equals("Etudiant"))
                {     
                     String fullname=rs.getString(3);
             String email=rs.getString(4);
   FXMLLoader loader=new FXMLLoader();
   loader.setLocation(getClass().getResource("Accueil_etudiant.fxml"));
        try {
            loader.load();
        } catch (Exception e) {
        }
        Accueil_etudiantController acc=loader.getController();
        acc.seTtext(fullname,email);
        Parent p=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(p));
        stage.show();
     
      btnaccueil.getScene().getWindow().hide();  //pour fermer interface login

                    
            }
            if(role.equals("Encadrant Académique "))
                {     
                     String fullname=rs.getString(3);
             String email=rs.getString(4);
   FXMLLoader loader=new FXMLLoader();
   loader.setLocation(getClass().getResource("Accueil_encadrant.fxml"));
        try {
            loader.load();
        } catch (Exception e) {
        }
        Accueil_encadrantController aea=loader.getController();
        aea.seTtext(fullname,email);
        Parent p=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(p));
        stage.show();
     
      btnaccueil.getScene().getWindow().hide();  //pour fermer interface login

                    
            }
            
            if(role.equals("Encadrant Professionnel"))
                {     
                     String fullname=rs.getString(3);
             String email=rs.getString(4);
   FXMLLoader loader=new FXMLLoader();
   loader.setLocation(getClass().getResource("Acceuil_pro.fxml"));
        try {
            loader.load();
        } catch (Exception e) {
        }
        Acceuil_proController aea=loader.getController();
        aea.seTtext(fullname,email);
        Parent p=loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(p));
        stage.show();
     
      btnaccueil.getScene().getWindow().hide();  //pour fermer interface login

                    
            }
                 
            } 
          
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
  
    }

    
    

     
    @FXML
    public void logout() throws IOException{
    
     logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
  
}
