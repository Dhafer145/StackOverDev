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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import entities.Etudiant;
import entities.affectation;
import entities.user;
import javax.swing.JOptionPane;

import tools.Myconn;

/**
 * FXML Controller class
 *
 * @author ramzuss
 */
public class AffecterEncadrantController implements Initializable {

  @FXML
    private ComboBox cbetudiant;

    @FXML
    private ComboBox cbencadrant;
    private TextField tfpro;

  @FXML
    private Button btajouter;

    @FXML
    private Button btmodifier;

    @FXML
    private Button btsupprimer;
    private Label laffecter;
    private Label changepassword;
    @FXML
    private TableView<affectation> tvaffectation;
    @FXML
    private TableColumn<affectation, String> colacademique;
    @FXML
    private TableColumn<affectation, String> coletudiant;
    @FXML
    private TableColumn<affectation, String> colpro;
    private Label lgereruser;
    @FXML
    private ComboBox cbpro;
    
    
    private int id1;
    private int id2;
    private int id3;
    @FXML
    private Button btnaccueil;
    @FXML
    private Button logout;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showEtudiant();
        showEncadrant();
       showaffectation();
       showPro();
    }    
    
    
     public void gererUser() throws IOException{
    
     lgereruser.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("GererUser.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
     
     public void affecterEncadrant() throws IOException{
    
     laffecter.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("AffecterEncadrant.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
    public void changepassword() throws IOException{
    
     changepassword.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("changePassword.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
    
    
    
    
    
    
   public ObservableList<user> getEtudiantList(){
        ObservableList<user> userList = FXCollections.observableArrayList();
       
        PreparedStatement pst = null;
     ResultSet rs = null;
       
     Connection cnx = Myconn.getInstance().getConnection();
        
       String req = "SELECT full_name FROM user WHERE user.role=? EXCEPT SELECT nom_etudiant FROM affectation ";
        
        try{
           pst = cnx.prepareStatement(req);
            pst.setString(1, "etudiant");
            rs = pst.executeQuery();
             user usr;  
            while(rs.next()){
                usr = new user(rs.getString("full_name")); // depuis bd //
                userList.add(usr);   
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return userList;
        
    }
    
    
     public void showEtudiant(){
           
 ObservableList<user> etudiant = getEtudiantList();      
cbetudiant.setItems(etudiant);
        
     }

     public ObservableList<user> getEncadrantList(){
        ObservableList<user> userList = FXCollections.observableArrayList();
       
        PreparedStatement pst = null;
     ResultSet rs = null;
       
     Connection cnx = Myconn.getInstance().getConnection();
        
       String req = "SELECT full_name FROM user WHERE user.role=? except SELECT nom_encadrant_academique FROM affectation  GROUP by nom_encadrant_academique  HAVING count(nom_encadrant_academique) >=3";
        
        try{
           pst = cnx.prepareStatement(req);
            pst.setString(1, "Encadrant Académique ");
            rs = pst.executeQuery();
             user usr;  
            while(rs.next()){
                usr = new user(rs.getString("full_name")); // depuis bd //
                userList.add(usr);   
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return userList;
        
    }
    
    
     public void showEncadrant(){
           
 ObservableList<user> encadrant = getEncadrantList();      
cbencadrant.setItems(encadrant);
        
     }

    @FXML
     public void affecter(){
        try{
            Connection connection =Myconn.getInstance().getConnection();
            PreparedStatement stm;
            String e = cbetudiant.getSelectionModel().getSelectedItem().toString();
            String a = cbencadrant.getSelectionModel().getSelectedItem().toString();
            String p = cbpro.getSelectionModel().getSelectedItem().toString();
            
            String e1 ="\""+e+"\"";
            String a1 ="\""+a+"\"";
            String p1 ="\""+p+"\"";
            
            ResultSet rs1 = connection.createStatement().executeQuery("select id_user from user where full_name="+e1+"");
            ResultSet rs2 = connection.createStatement().executeQuery("select id_user from user where full_name="+a1+"");
            ResultSet rs3 = connection.createStatement().executeQuery("select id_user from user where full_name="+p1+"");
            while(rs1.next()){id1=rs1.getInt("id_user");}
            while(rs2.next()){id2=rs2.getInt("id_user");}
            while(rs3.next()){id3=rs3.getInt("id_user");}
            
            
            try{
                
                stm = connection.prepareStatement("insert into affectation(nom_etudiant,nom_encadrant_academique,nom_encadrant_entreprise,id_etudiant,id_encadrant_academique,id_encadrant_entreprise) values(?,?,?,?,?,?)");
                stm.setString(1,e);
                stm.setString(2,a);
                stm.setString(3,p);
                stm.setInt(4,id1);
                stm.setInt(5,id2);
                stm.setInt(6,id3);
                
                int i = stm.executeUpdate();
                System.out.println(i);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            showaffectation();
            showEncadrant();
            showEtudiant();
            cbetudiant.getSelectionModel().getSelectedItem();
        }
        catch (SQLException ex){
            Logger.getLogger(AffecterEncadrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
 
    
       public ObservableList<affectation> getaffectationList(){
        ObservableList<affectation> affectationList = FXCollections.observableArrayList();
        Connection cnx = Myconn.getInstance().getConnection();
        String req = "select * from affectation";
        Statement st;
        ResultSet rs;
        
        try{
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            affectation affect;
           
            while(rs.next()){
                affect = new affectation(rs.getString(2),rs.getString(3),rs.getString(4)); // depuis bd //
                
                affectationList.add(affect);
                
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return affectationList;
        
    }
       
       
       
       public ObservableList<user> getProList(){
        ObservableList<user> userList = FXCollections.observableArrayList();
       
        PreparedStatement pst = null;
     ResultSet rs = null;
       
     Connection cnx = Myconn.getInstance().getConnection();
        
       String req = "SELECT full_name FROM user WHERE user.role=?";
        
        try{
           pst = cnx.prepareStatement(req);
            pst.setString(1, "Encadrant Professionnel");
            rs = pst.executeQuery();
             user usr;  
            while(rs.next()){
                usr = new user(rs.getString("full_name")); // depuis bd //
                userList.add(usr);   
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return userList;
        
    }
    
    
     public void showPro(){
           
 ObservableList<user> pro = getProList();      
cbpro.setItems(pro);
        
     }
       
       
       
    
     public void showaffectation(){
        ObservableList<affectation> list = getaffectationList();
 
colacademique.setCellValueFactory(new PropertyValueFactory<affectation,String>("nomEtudiant"));
coletudiant.setCellValueFactory(new PropertyValueFactory<affectation, String>("nomAcademique"));
colpro.setCellValueFactory(new PropertyValueFactory<affectation, String>("nompro"));       
    //classe user.java

        tvaffectation.setItems(list);
         }
    
    @FXML
     public void modifier() {
        Connection connection = Myconn.getInstance().getConnection();
        PreparedStatement stmt;

        try {
            
            stmt = connection.prepareStatement("UPDATE affectation SET nom_encadrant_academique=?, nom_encadrant_entreprise=? WHERE nom_etudiant=?");
            
            stmt.setString(3,cbetudiant.getSelectionModel().getSelectedItem().toString());
            
              
           
            
           
            stmt.setString(1,cbencadrant.getSelectionModel().getSelectedItem().toString());
            stmt.setString(2,(cbpro.getSelectionModel().getSelectedItem().toString())); 


              int i = stmt.executeUpdate();

            System.out.println(i);
            

        }catch (Exception e){
            e.printStackTrace();
        }
        showaffectation();
    }

    @FXML
     public void supprimer() {
        Connection connection = Myconn.getInstance().getConnection();
        PreparedStatement stmt;

       try {
           stmt=connection.prepareStatement("delete from affectation where nom_etudiant=?");
           stmt.setString(1,cbetudiant.getSelectionModel().getSelectedItem().toString());

           int i=stmt.executeUpdate();
           
       }catch (Exception e){
           e.printStackTrace();
       }
       showaffectation();
       showEncadrant();
       showEtudiant();
    }
     
    @FXML
    public void Action(MouseEvent event) {
    affectation aff=tvaffectation.getSelectionModel().getSelectedItem();
     cbencadrant.getSelectionModel().select(""+aff.getNomAcademique());
     cbetudiant.getSelectionModel().select(""+aff.getNomEtudiant());
    cbpro.getSelectionModel().select(""+aff.getNompro());
   
   
   
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
                    System.out.println("hahaha");
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
     