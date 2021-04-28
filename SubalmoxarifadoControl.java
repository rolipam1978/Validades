
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramiro
 */
public class SubalmoxarifadoControl {
    
    ConexaoBD con = new ConexaoBD();
    PreparedStatement pstm;
    ResultSet rs;
    
    public void Incluir(SubalmoxarifadoBean sb){
     
        try{
            String sql = "insert into subalmoxarifados (id, nome) values (?,?)";
            pstm = con.conecta().prepareStatement(sql);
            pstm.setInt(1, sb.getId());
            pstm.setString(2, sb.getNome());
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Gravado com Sucesso!");
            
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não Foi Possível Gravar o Registro!");
        }finally{
            con.desconecta();
        }
        
    }
    
}
