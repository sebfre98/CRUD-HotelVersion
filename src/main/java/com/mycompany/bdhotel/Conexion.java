/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bdhotel;


import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class Conexion {
    
    
    Connection conectar = null;
    
    String usuario = "root";
    String contrasenia = "";
  
    
    
    String url = "jdbc:mysql://localhost:3306/hotel";
    
    
    public Connection establecerConexion(){
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conectar= DriverManager.getConnection(url,usuario,contrasenia);
            
           // JOptionPane.showMessageDialog(null,"Se conectó correctamente a la base de datos");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ERROR:"+ e.toString());
        }
        
        return conectar;
    
    }
    
}
    