/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bdhotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 *
 * @author agseb
 */
public class RegistroReservas {
    //definir las variables de la tabla clientes
    int dias, reservaId, cliId, cliCc, cliCel;
    String tipoHabi, cliNombre, cliApellido, cliDireccion;

    //getters y setters de la tabla hotel

    //codigo del cliente
    public int getCliId() {
        return cliId;
    }

    public void setCliId(int EstId) {
        this.cliId = EstId;
    }

    public int getReservaId() {
        return cliId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getDias() {
        return cliId;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    //Cc del cliente
    public int getCliCC() {
        return cliCc;
    }

    public void setCliCC(int cliCC) {
        this.cliCc = cliCC;
    }

    //nombre del CLiente
    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String EstNombre) {
        this.cliNombre = EstNombre;
    }

    public String getTipoHabi() {
        return cliNombre;
    }

    public void setTipoHabi(String tipoHabi) {
        this.tipoHabi = tipoHabi;
    }
    //apellido del Cliente
    public String getCliApellido() {
        return cliApellido;
    }

    public void setCliApellido(String EstApellido) {
        this.cliApellido = EstApellido;
    }

    //Celular del Cliente
    public int getCliCelular() {
        return cliCel;
    }

    public void setCliCelular(int cliCel) {
        this.cliCel = cliCel;
    }

    // Direccion Consultar estudiantes
    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliCel) {
        this.cliDireccion = cliCel;
    }
    public void MostrarReservas(JTable paramTablaTotalReservas){



         Conexion objetoConexion = new Conexion();
         DefaultTableModel modelo = new DefaultTableModel();


         String sql="";

         modelo.addColumn("ID_Reservas");
         modelo.addColumn("Tipo_Habitacion");
         modelo.addColumn("Nombre");
         modelo.addColumn("Documento");
         modelo.addColumn("Fecha Reserva");
         modelo.addColumn("Dias Hospedaje");
         modelo.addColumn("Total");


         paramTablaTotalReservas.setModel(modelo);

    //consulta select

        sql = "SELECT r.CODIGO_RESERVS, h.TIPO_HABITACION, c.NOMBRE AS nombre_cliente, c.NUM_IDENTIFICACION, r.FECHA_RESERVA, r.DIAS_HOSPEDAJE, r.TOTAL " +
                "FROM RESERVA r " +
                "JOIN HABITACIONES h ON r.CODIGO_HABITACION = h.CODIGO_HABITACION " +
                "JOIN CLIENTES c ON r.CODIGO_CLIENTE = c.CODIGO_CLIENTE " +
                "ORDER BY r.CODIGO_RESERVS";

         String [] datos = new String[7];

         Statement st;


        try {

            st= objetoConexion.establecerConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0]= rs.getString(1);
                datos[1]= rs.getString(2);
                datos[2]= rs.getString(3);
                datos[3]= rs.getString(4);
                datos[4]= rs.getString(5);
                datos[5]= rs.getString(6);
                datos[6]= rs.getString(7);


                modelo.addRow(datos);

            }

            paramTablaTotalReservas.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error:"+ e.toString());

        }

    }
    //Método insercion de registros

    public void InsertarReservasPrin(JTextField paramestIdRes,JTextField paramestNcc,JTextField paramestTipHab,JTextField paramestDias){
        String idRes = paramestIdRes.getText();
        String Ncc = paramestNcc.getText();
        String tipHab = paramestTipHab.getText();
        String dias = paramestDias.getText();

        System.out.println("este es el Id Reserva: " + idRes);
        System.out.println("Numero de documento: "+Ncc);
        System.out.println("Habitacion: "+tipHab);
        System.out.println("Dias: " + dias);
    }

    public Float TotalPrecio(Float precioHab, Integer dias){
        Float precioTotal = precioHab * dias;
        return precioTotal;
    }
    public void InsertarReservas(JTextField paramestIdRes,JTextField paramestNcc,JTextField paramestTipHab,JTextField paramestDias){

        int reservaId = Integer.parseInt(paramestIdRes.getText());
        int numIdentificacion = Integer.parseInt(paramestNcc.getText());
        String tipoHabitacion = paramestTipHab.getText();
        int diasHospedaje = Integer.parseInt(paramestDias.getText());

        setReservaId(reservaId);
        setCliCC(numIdentificacion);
        setTipoHabi(tipoHabitacion);
        setDias(diasHospedaje);

        Conexion objetoConexion = new Conexion();

        String consultaCodigoCliente = "SELECT CODIGO_CLIENTE FROM CLIENTES WHERE NUM_IDENTIFICACION = ?;";

        String consultaCodigoHabitacion = "SELECT CODIGO_HABITACION FROM HABITACIONES WHERE TIPO_HABITACION = ?;";

        String consultaPredioHabitacion = "SELECT PRECIO FROM HABITACIONES WHERE TIPO_HABITACION = ?;";

        String consulta = "INSERT INTO RESERVA (CODIGO_HABITACION, CODIGO_CLIENTE, FECHA_RESERVA, DIAS_HOSPEDAJE, TOTAL) " + "VALUES (?, ?, CURDATE(), ?, ?);";

        try {



            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);

            // Obtener el código del cliente
            int codigoCliente = -1;

            try  {
                CallableStatement cscliente = objetoConexion.establecerConexion().prepareCall(consultaCodigoCliente);
                cscliente.setInt(1, numIdentificacion);
                try (ResultSet rs = cscliente.executeQuery()) {
                    if (rs.next()) {
                        codigoCliente = rs.getInt("CODIGO_CLIENTE");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Obtener el código de la habitación
            int codigoHabitacion = -1;
            try  {
                CallableStatement csChabita = objetoConexion.establecerConexion().prepareCall(consultaCodigoHabitacion );
                csChabita.setString(1, tipoHabitacion);
                try (ResultSet rs = csChabita.executeQuery()) {
                    if (rs.next()) {
                        codigoHabitacion = rs.getInt("CODIGO_HABITACION");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Obtener el precio de la habitación
            float precio = 0;
            try  {
                CallableStatement csPrecio = objetoConexion.establecerConexion().prepareCall(consultaPredioHabitacion);
                csPrecio.setString(1, tipoHabitacion);
                try (ResultSet rs = csPrecio.executeQuery()) {
                    if (rs.next()) {
                        precio = rs.getInt("PRECIO");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Verificar si se obtuvieron los códigos necesarios
            if (codigoCliente != -1 && codigoHabitacion != -1) {
                // Insertar la reserva
                try  {
                    cs.setInt(1, codigoHabitacion);
                    cs.setInt(2, codigoCliente);
                    cs.setInt(3, diasHospedaje);
                    cs.setDouble(4, TotalPrecio(precio, diasHospedaje));

                    cs.execute();

                    JOptionPane.showMessageDialog(null, "Se insertó correctamente");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                } catch (HeadlessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: No se encontró el cliente o la habitación");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }

    // Suponiendo que tienes un método para calcular el total
    private double calcularTotal(int codigoHabitacion, int diasHospedaje) {
        // Implementa la lógica para calcular el total basado en el código de la habitación y los días de hospedaje
        return diasHospedaje * 100; // Ejemplo: 100 es el precio por día
    }


     public void SeleccionarReserva(JTable paramTablaTotalReservas, JTextField paramestIdRes,JTextField paramestNcc,JTextField paramestTipHab,JTextField paramestDias){

        try {

            int fila=paramTablaTotalReservas.getSelectedRow();

            if (fila>=0) {
                paramestIdRes.setText(paramTablaTotalReservas.getValueAt(fila, 0).toString());
                paramestNcc.setText(paramTablaTotalReservas.getValueAt(fila, 3).toString());
                paramestTipHab.setText(paramTablaTotalReservas.getValueAt(fila, 1).toString());
                paramestDias.setText(paramTablaTotalReservas.getValueAt(fila, 5).toString());



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
      public void ModificarReserva(JTextField paramestIdRes, JTextField paramestNcc, JTextField paramestTipHab, JTextField paramestDias){

          int reservaId = Integer.parseInt(paramestIdRes.getText());
          int numIdentificacion = Integer.parseInt(paramestNcc.getText());
          String tipoHabitacion = paramestTipHab.getText();
          int diasHospedaje = Integer.parseInt(paramestDias.getText());

          setReservaId(reservaId);
          setCliCC(numIdentificacion);
          setTipoHabi(tipoHabitacion);
          setDias(diasHospedaje);

        Conexion objetoConexion = new Conexion();

        String consultaCodigoCliente = "SELECT CODIGO_CLIENTE FROM CLIENTES WHERE NUM_IDENTIFICACION = ?;";

        String consultaCodigoHabitacion = "SELECT CODIGO_HABITACION FROM HABITACIONES WHERE TIPO_HABITACION = ?;";

        String consultaPredioHabitacion = "SELECT PRECIO FROM HABITACIONES WHERE TIPO_HABITACION = ?;";

        String consulta = "UPDATE hotel.reserva SET CODIGO_CLIENTE =?, CODIGO_HABITACION=?, DIAS_HOSPEDAJE=?, TOTAL=? WHERE reserva.CODIGO_RESERVS =?;";

          // Obtener el código del cliente
          int codigoCliente = -1;

          try  {
              CallableStatement cscliente = objetoConexion.establecerConexion().prepareCall(consultaCodigoCliente);
              cscliente.setInt(1, numIdentificacion);
              try (ResultSet rs = cscliente.executeQuery()) {
                  if (rs.next()) {
                      codigoCliente = rs.getInt("CODIGO_CLIENTE");
                  }
              }
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }

          // Obtener el código de la habitación
          int codigoHabitacion = -1;
          try  {
              CallableStatement csChabita = objetoConexion.establecerConexion().prepareCall(consultaCodigoHabitacion );
              csChabita.setString(1, tipoHabitacion);
              try (ResultSet rs = csChabita.executeQuery()) {
                  if (rs.next()) {
                      codigoHabitacion = rs.getInt("CODIGO_HABITACION");
                  }
              }
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }

          // Obtener el precio de la habitación
          float precio = 0;
          try  {
              CallableStatement csPrecio = objetoConexion.establecerConexion().prepareCall(consultaPredioHabitacion);
              csPrecio.setString(1, tipoHabitacion);
              try (ResultSet rs = csPrecio.executeQuery()) {
                  if (rs.next()) {
                      precio = rs.getInt("PRECIO");
                  }
              }
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
          // Verificar si se obtuvieron los códigos necesarios
          if (codigoCliente != -1 && codigoHabitacion != -1) {
            try {

                CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
                //establecer por orden de la consulta para no tener errores
                cs.setInt(5, reservaId);
                cs.setInt(1, codigoCliente);
                cs.setInt(2, codigoHabitacion);
                cs.setInt(3, diasHospedaje);
                cs.setDouble(4, TotalPrecio(precio, diasHospedaje));

                cs.execute();

                JOptionPane.showMessageDialog(null,"Se modificó correctamente");


            } catch (Exception e) {
                 JOptionPane.showMessageDialog(null,"Error:"+ e.toString());
            }

        }
    }

    //Método EliminarEstudiante
    public void EliminarReserva( JTextField paramestId){


        setCliId(Integer.parseInt(paramestId.getText()));


        Conexion objetoConexion = new Conexion();


        String consulta = "DELETE FROM hotel.reserva WHERE reserva.CODIGO_RESERVS=?";


        try {


            int i = JOptionPane.showConfirmDialog(null,"Desea eliminar? si se elimina no podra reservar");

            if (i ==0 ){
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, getCliId());

            cs.execute();

            JOptionPane.showMessageDialog(null,"Se Eliminó correctamente");

            } else {

               JOptionPane.showMessageDialog(null,"No se elimino el cliente");
            }


        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Error:"+ e.toString());
        }


    }

   //Método BuscarReserva
    public void BuscarReserva(JTable paramTablaTotalClientes, JTextField paramestNcc){

        String cedulaTexto = paramestNcc.getText();

        int cedula = Integer.parseInt(cedulaTexto);

        Conexion objetoConexion = new Conexion();


         DefaultTableModel modelo = new DefaultTableModel();


        modelo.addColumn("ID_Reservas");
        modelo.addColumn("Tipo_Habitacion");
        modelo.addColumn("Nombre");
        modelo.addColumn("Documento");
        modelo.addColumn("Fecha Reserva");
        modelo.addColumn("Dias Hospedaje");
        modelo.addColumn("Total");

         paramTablaTotalClientes.setModel(modelo);

        String sql = "SELECT r.CODIGO_RESERVS, h.TIPO_HABITACION, c.NOMBRE AS nombre_cliente, c.NUM_IDENTIFICACION, r.FECHA_RESERVA, r.DIAS_HOSPEDAJE, r.TOTAL " +
                "FROM RESERVA r " +
                "JOIN HABITACIONES h ON r.CODIGO_HABITACION = h.CODIGO_HABITACION " +
                "JOIN CLIENTES c ON r.CODIGO_CLIENTE = c.CODIGO_CLIENTE " +
                "WHERE c.NUM_IDENTIFICACION = ?;";

        String [] datos = new String[7];


        try {

           PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(sql);

           ps.setInt(1,cedula);

           ResultSet rs = ps.executeQuery();

           modelo.setColumnIdentifiers(new Object[]{"ID_Reservas","Tipo_Habitacion", "Nombre", "Documento", "Fecha Reserva", "Dias Hospedaje","Total"});

            while (rs.next()) {


                datos[0]= rs.getString(1);
                datos[1]= rs.getString(2);
                datos[2]= rs.getString(3);
                datos[3]= rs.getString(4);
                datos[4]= rs.getString(5);
                datos[5]= rs.getString(6);
                datos[6]= rs.getString(7);

                modelo.addRow(datos);

            }

            paramTablaTotalClientes.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:"+ e.toString());

        }
    }

}
