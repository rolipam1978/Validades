
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramiro
 */
public class ConexaoBD {

       final private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
       final private String url = "jdbc:derby:VALIDADES";
       //final private String url = "jdbc:derby://localhost:1527/VALIDADES";
       final private String usuario = "root";
       final private String senha = "root";
       Statement stm;
       ResultSet rs;

       private Connection con;


       public Connection conecta()
       {
            try
            {
                Class.forName(driver);
                con = DriverManager.getConnection(url, usuario, senha);
                //JOptionPane.showMessageDialog(null, "Conectado ao Banco de Dados!");

            }
            catch (ClassNotFoundException Driver)
            {
                JOptionPane.showMessageDialog(null, "Falha na Conexão com o Banco de Dados!");
                Driver.printStackTrace();
                            }
            catch (SQLException Fonte)
            {
                JOptionPane.showMessageDialog(null, "Erro de Conexão!");
                Fonte.printStackTrace();
                            }
            return con;
       }
       public void desconecta()
       {
            boolean result = true;

            try
            {
                con.close();
                //JOptionPane.showMessageDialog(null, "Conexão Fechada!");
            }
            catch (SQLException erroSQL)
            {
                    JOptionPane.showMessageDialog(null, "Não Foi Possíve Fechar o Banco de Dados!");
                    result = false;
             }
            
       }
       public void executeSQL (String sql){
           try{
               stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
               rs = stm.executeQuery(sql);

               
           }catch (Exception ex){
               JOptionPane.showMessageDialog(null, "Erro ao Executar o Comando!");
               JOptionPane.showMessageDialog(null, ex);
               ex.printStackTrace();
           }
       }
       
}

