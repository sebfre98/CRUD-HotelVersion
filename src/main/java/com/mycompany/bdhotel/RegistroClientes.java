/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bdhotel;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author agseb
 */
public class RegistroClientes {
    //definir las variables de la tabla clientes
    int cliId, cliCc, cliCel;
    String cliNombre, cliApellido, cliDireccion;

    //getters y setters de la tabla hotel

    //codigo del cliente
    public int getCliId() {
        return cliId;
    }

    public void setCliId(int EstId) {
        this.cliId = EstId;
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
    public void MostrarClientes(JTable paramTablaTotalClientes){



         Conexion objetoConexion = new Conexion();


         DefaultTableModel modelo = new DefaultTableModel();


         String sql="";

         modelo.addColumn("ID_Cliente");
         modelo.addColumn("Num_Identidad");
         modelo.addColumn("Nombre");
         modelo.addColumn("Apellido");
         modelo.addColumn("Celular");
         modelo.addColumn("Dirección");


         paramTablaTotalClientes.setModel(modelo);

    //consulta select
         sql ="SELECT * FROM hotel.clientes";


         String [] datos = new String[6];

         Statement st;


        try {

            st= objetoConexion.establecerConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0]= rs.getString(1);
                datos[1]= rs.getString(6);
                datos[2]= rs.getString(2);
                datos[3]= rs.getString(3);
                datos[4]= rs.getString(4);
                datos[5]= rs.getString(5);


                modelo.addRow(datos);

            }

            paramTablaTotalClientes.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error:"+ e.toString());

        }

    }
    //Método insercion de registros
    public void insertarCliente(JTextField paramestNcc,JTextField paramestNombre,JTextField paramestApellido,JTextField paramestCelular,JTextField paramestDireccion){


        //setCliId(Integer.parseInt(paramestId.getText()));
        setCliCC(Integer.parseInt(paramestNcc.getText()));
        setCliNombre(paramestNombre.getText());
        setCliApellido(paramestApellido.getText());
        setCliCelular(Integer.parseInt(paramestCelular.getText()));
        setCliDireccion(paramestDireccion.getText());

        Conexion objetoConexion = new Conexion();

        String consulta = "INSERT INTO hotel.clientes (NOMBRE, APELLIDO, CELULAR, DIRECCION, NUM_IDENTIFICACION) VALUES (?, ?, ?, ?, ?);";



        try {

            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            //cs.setInt(1, getCliId());
            cs.setInt(5, getCliCC());
            cs.setString(1, getCliNombre());
            cs.setString(2, getCliApellido());
            cs.setInt(3, getCliCelular());
            cs.setString(4, getCliDireccion());

            cs.execute();

            JOptionPane.showMessageDialog(null,"Se insertó correctamente");


        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Error:"+ e.toString());
        }

    }
     public void SeleccionarCliente(JTable paramTablaClientes, JTextField paramestId,JTextField paramestNcc,JTextField paramestNombre,JTextField paramestApellido,JTextField paramestCelular,JTextField paramestDireccion){



        try {

            int fila=paramTablaClientes.getSelectedRow();

            if (fila>=0) {

               paramestId.setText(paramTablaClientes.getValueAt(fila, 0).toString());
               paramestNcc.setText(paramTablaClientes.getValueAt(fila, 1).toString());
               paramestNombre.setText(paramTablaClientes.getValueAt(fila, 2).toString());
               paramestApellido.setText(paramTablaClientes.getValueAt(fila, 3).toString());
               paramestCelular.setText(paramTablaClientes.getValueAt(fila, 4).toString());
               paramestDireccion.setText(paramTablaClientes.getValueAt(fila, 5).toString());


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
      public void ModificarClientes(JTextField paramestId, JTextField paramestNcc, JTextField paramestNombre,
              JTextField paramestApellido, JTextField paramestCelular, JTextField paramestDireccion){
 int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro "
            + "de que deseas modificar el REGISTRO?", "Confirmar Modificación"
            , JOptionPane.YES_NO_OPTION);
if (confirmacion == JOptionPane.YES_OPTION) {
          setCliId(Integer.parseInt(paramestId.getText()));
          setCliCC(Integer.parseInt(paramestNcc.getText()));
          setCliNombre(paramestNombre.getText());
          setCliApellido(paramestApellido.getText());
          setCliCelular(Integer.parseInt(paramestCelular.getText()));
          setCliDireccion(paramestDireccion.getText());

        Conexion objetoConexion = new Conexion();


        String consulta = "UPDATE hotel.clientes SET NOMBRE =?, APELLIDO=?, CELULAR=?, DIRECCION=?, NUM_IDENTIFICACION=? WHERE clientes.CODIGO_CLIENTE =?;";

        try {

            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            //establecer por orden de la consulta para no tener errores
            cs.setInt(6, getCliId());
            cs.setInt(5, getCliCC());
            cs.setString(1, getCliNombre());
            cs.setString(2, getCliApellido());
            cs.setInt(3, getCliCelular());
            cs.setString(4, getCliDireccion());

            cs.execute();

            JOptionPane.showMessageDialog(null,"Se modificó correctamente");


        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Error:"+ e.toString());
        }
        } else {
        JOptionPane.showMessageDialog(null, "La modificación ha sido cancelada");
    }

    }

    //Método EliminarEstudiante
    public void EliminarEstudiante( JTextField paramestId){


        setCliId(Integer.parseInt(paramestId.getText()));


        Conexion objetoConexion = new Conexion();


        String consulta = "DELETE FROM hotel.clientes WHERE clientes.CODIGO_CLIENTE=?";

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

   //Método BuscarEstudiantes
    public void BuscarCliente(JTable paramTablaTotalClientes, JTextField paramestNcc){

        String cedulaTexto = paramestNcc.getText();

        int cedula = Integer.parseInt(cedulaTexto);

        Conexion objetoConexion = new Conexion();


         DefaultTableModel modelo = new DefaultTableModel();


        modelo.addColumn("ID_Cliente");
        modelo.addColumn("Num_Identidad");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Celular");
        modelo.addColumn("Dirección");

         paramTablaTotalClientes.setModel(modelo);

        String sql = "SELECT * FROM hotel.clientes WHERE NUM_IDENTIFICACION = ?";

         String [] datos = new String[6];


        try {

           PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(sql);

           ps.setInt(1,cedula);

           ResultSet rs = ps.executeQuery();

            modelo.setColumnIdentifiers(new Object[]{"ID_Cliente","Num_Identidad", "Nombre", "Apellido", "Celular", "Dirección"});

            while (rs.next()) {


                datos[0]= rs.getString(1);
                datos[1]= rs.getString(6);
                datos[2]= rs.getString(2);
                datos[3]= rs.getString(3);
                datos[4]= rs.getString(4);
                datos[5]= rs.getString(5);

                modelo.addRow(datos);

            }

            paramTablaTotalClientes.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error:"+ e.toString());

        }
    }

}
