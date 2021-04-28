
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramiro
 */
public class ProdutosControl {
    
    ConexaoBD con = new ConexaoBD();
    PreparedStatement pstm;
    ResultSet rs;
    
    public void Incluir(ProdutosBean pb){
        
        try{
            String sql = "insert into produtos (id, tipo, apresentacao, descricao) values (?,?,?,?)";
            pstm = con.conecta().prepareStatement(sql);
            pstm.setInt(1, pb.getId());
            pstm.setString(2, pb.getTipo());
            pstm.setString(3, pb.getApresentacao());
            pstm.setString(4, pb.getDescricao());
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Gravado com Sucesso!");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não Foi Possível Gravar o Registro!");
        }finally{
            con.desconecta();
        }
    }
    
    public void Excluir(ProdutosBean pb){
        String sql = "delete from produtos where id = ?";
        String nome = "Deseja Deletar o Produto Selecionado?";
        int opcao = JOptionPane.showConfirmDialog(null, nome, "Deletar Registro", JOptionPane.YES_NO_OPTION);
        try{
            pstm = con.conecta().prepareStatement(sql);
            pstm.setInt(1, pb.getId());
            if (opcao == JOptionPane.YES_OPTION) {
                int excluiu = pstm.executeUpdate();
                if (excluiu == 1) {
                    JOptionPane.showMessageDialog(null, "Registro Excluído com Sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Registro Não Foi Excluído!");
            }
            
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não Foi Possível Excluir o Registro!");
        }finally{
            con.desconecta();
        }        
    }
    
    public void Editar(ProdutosBean pb){
        String sql = "update produtos set tipo = ?, apresentacao = ?, descricao = ? where id = ?";
        
        try{
            pstm = con.conecta().prepareStatement(sql);
            pstm.setString(1, pb.getTipo());
            pstm.setString(2, pb.getApresentacao());
            pstm.setString(3, pb.getDescricao());
            pstm.setInt(4, pb.getId());
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Alterado com Sucesso!");
            
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não Foi Possível Editar o Registro!");
        }finally{
            con.desconecta();
        }
    }
}
