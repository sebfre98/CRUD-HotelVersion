/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bdhotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author agseb
 */
public class RegistroHabitaciones {
    //definir las variables de la tabla hotel
    int habId;

    float habPrecio;
    String tipoHab, habDescr;

    //getters y setters de la tabla habitaciones

    //codigo del habitacion
    public int getHabId() {
        return habId;
    }

    public void setHabId(int habId) {
        this.habId = habId;
    }

    //Tipo de habitacion
    public String getTipoHab() {
        return tipoHab;
    }

    public void setTipoHab(String tipoHab) {
        this.tipoHab = tipoHab;
    }

    //Descripcion de Habitacion
    public String getHabDescr() {
        return habDescr;
    }

    public void setHabDescr(String habDescr) {
        this.habDescr = habDescr;
    }

    //Precio de habitacion
    public float gethabPreci() {
        return habPrecio;
    }

    public void setHabPreci(float habPrecio) {
        this.habPrecio = habPrecio;
    }


    public void MostrarHabitaciones(JTable paramTablaTotalClientes){


         Conexion objetoConexion = new Conexion();

         DefaultTableModel modelo = new DefaultTableModel();

         String sql="";

         modelo.addColumn("ID_Habitacion");
         modelo.addColumn("Tipo_Habitacion");
         modelo.addColumn("Descripción_Habitacion");
         modelo.addColumn("Precio_Habitacion");

         paramTablaTotalClientes.setModel(modelo);

    //consulta select
         sql ="SELECT * FROM hotel.habitaciones";


         String [] datos = new String[4];

         Statement st;


        try {

            st= objetoConexion.establecerConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0]= rs.getString(1);
                datos[1]= rs.getString(2);
                datos[2]= rs.getString(3);
                datos[3]= rs.getString(4);


                modelo.addRow(datos);

            }

            paramTablaTotalClientes.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error:"+ e.toString());

        }

    }
    //Método insercion de registros
    public void insertarHabitaciones(JTextField paramestIdHab, JTextField paramestTipHab, JTextField paramestDesHab, JTextField paramestPreHab){

        setHabId(Integer.parseInt(paramestIdHab.getText()));
        setTipoHab(paramestTipHab.getText());
        setHabDescr(paramestDesHab.getText());
        setHabPreci(Float.parseFloat(paramestPreHab.getText()));



        Conexion objetoConexion = new Conexion();

        String consulta = "INSERT INTO hotel.habitaciones (CODIGO_HABITACION, TIPO_HABITACION, DESCRIPCION, PRECIO) VALUES (?, ?, ?, ?);";



        try {

            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1,getHabId());
            cs.setString(2, getTipoHab());
            cs.setString(3, getHabDescr());
            cs.setFloat(4, gethabPreci());

            cs.execute();

            JOptionPane.showMessageDialog(null,"Se insertó correctamente");


        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Error:"+ e.toString());
        }

    }
     public void SeleccionarHabitaciones(JTable paramTablaHabitaciones, JTextField paramestIdHab, JTextField paramestTipHab, JTextField paramestDesHab,JTextField paramestPreHab){



        try {

            int fila=paramTablaHabitaciones.getSelectedRow();

            if (fila>=0) {

                paramestIdHab.setText(paramTablaHabitaciones.getValueAt(fila, 0).toString());
                paramestTipHab.setText(paramTablaHabitaciones.getValueAt(fila, 1).toString());
                paramestDesHab.setText(paramTablaHabitaciones.getValueAt(fila, 2).toString());
                paramestPreHab.setText(paramTablaHabitaciones.getValueAt(fila, 3).toString());



            }

            else
            {
                JOptionPane.showMessageDialog(null,"Fila no seleccionada");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,"Error: "+ e.toString());
        }

    }
     //Método ModificarEstudiante
      public void ModificarHabitaciones(JTextField paramestIdHab, JTextField paramestTipHab, JTextField paramestDesHab, JTextField paramestPreHab){


          setHabId(Integer.parseInt(paramestIdHab.getText()));
          setTipoHab(paramestTipHab.getText());
          setHabDescr(paramestDesHab.getText());
          setHabPreci(Float.parseFloat(paramestPreHab.getText()));


        Conexion objetoConexion = new Conexion();


        String consulta = "UPDATE hotel.habitaciones SET TIPO_HABITACION =?, DESCRIPCION =?, PRECIO =? WHERE habitaciones.CODIGO_HABITACION =?;";

        try {

            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            //establecer por orden de la consulta para no tener errores
            cs.setInt(4, getHabId());
            cs.setString(1, getTipoHab());
            cs.setString(2, getHabDescr());
            cs.setFloat(3, gethabPreci());


            cs.execute();

            JOptionPane.showMessageDialog(null,"Se modificó correctamente");


        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Error:"+ e.toString());
        }

    }

    //Método EliminarEstudiante
    public void EliminarHabitacion( JTextField paramestId){


        setHabId(Integer.parseInt(paramestId.getText()));


        Conexion objetoConexion = new Conexion();


        String consulta = "DELETE FROM hotel.habitaciones WHERE habitaciones.CODIGO_HABITACION =?";

        try {


            int i = JOptionPane.showConfirmDialog(null,"Desea eliminar? si se elimina no podra reservar");

            if (i ==0 ){
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, getHabId());

            cs.execute();

            JOptionPane.showMessageDialog(null,"Se Eliminó correctamente");

            } else {

               JOptionPane.showMessageDialog(null,"No se elimino el cliente");
            }


        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Error:"+ e.toString());
        }


    }

   //Método BuscarHabitacion
    public void BuscarHabitacion(JTable paramTablaTotalHabitaciones, JTextField paramestTipHab){

        String tipoHabiTexto = paramestTipHab.getText();

        String habitacion = tipoHabiTexto;

        if (tipoHabiTexto.isEmpty()) {
            throw new IllegalArgumentException("El campo Tipo de Habitación está vacío.");
        }

        Conexion objetoConexion = new Conexion();


         DefaultTableModel modelo = new DefaultTableModel();


        modelo.addColumn("ID_Habitacion");
        modelo.addColumn("Tipo_Habitacion");
        modelo.addColumn("Descripción_Habitacion");
        modelo.addColumn("Precio_Habitacion");

         paramTablaTotalHabitaciones.setModel(modelo);

        String sql = "SELECT * FROM hotel.habitaciones WHERE TIPO_HABITACION =?";

         String [] datos = new String[4];


        try {

           PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(sql);

           ps.setString(1,habitacion);

           ResultSet rs = ps.executeQuery();

           modelo.setColumnIdentifiers(new Object[]{"ID_Habitacion","Tipo_Habitacion", "Descripción_Habitacion", "Precio_Habitacion"});

            while (rs.next()) {


                datos[0]= rs.getString(1);
                datos[1]= rs.getString(2);
                datos[2]= rs.getString(3);
                datos[3]= rs.getString(4);


                modelo.addRow(datos);

            }

            paramTablaTotalHabitaciones.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:"+ e.toString());

        }
    }

}

