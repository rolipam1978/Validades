import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramiro
 */
public class ValidadesControl {
    
    ConexaoBD con = new ConexaoBD();
    PreparedStatement pstm;
    ResultSet rs;
    SimpleDateFormat dataf = new SimpleDateFormat("dd/MM/yyyy");
    java.sql.Date d = null;
    
    public void Salvar (ValidadesBean vb){
        
        try{
            String sql = "insert into validades (id, codproduto, status, lote, validade, quantidade, cmm, subalmoxarifado, local, origem, "
                    + "detalhe_origem) values (?,?,?,?,?,?,?,?,?,?,?)";
            pstm = con.conecta().prepareStatement(sql);
            pstm.setInt(1, vb.getId());
            pstm.setInt(2, vb.getCodproduto());
            pstm.setString(3, vb.getStatus());
            pstm.setString(4, vb.getLote());
            pstm.setDate(5, vb.getValidade());
            pstm.setInt(6, vb.getQuantidade());
            pstm.setInt(7, vb.getCmm());
            pstm.setInt(8, vb.getSubalmoxarifado());
            pstm.setString(9, vb.getLocal());
            pstm.setString(10, vb.getOrigem());
            pstm.setString(11, vb.getDetalhe_origem());
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Gravado com Sucesso!");
            
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não Foi Possível Gravar o Registro!");
        }finally{
            con.desconecta();
        }
    }
    
    public void Alterar (ValidadesBean vb){
        
        try{
            String sql = "update validades set codproduto = ?, status = ?, lote = ?, validade = ?, quantidade = ?, cmm = ?, subalmoxarifado = ?, "
                    + "local = ?, origem = ?, detalhe_origem = ? where id = ?";
            pstm = con.conecta().prepareStatement(sql);
            pstm.setInt(1, vb.getCodproduto());
            pstm.setString(2, vb.getStatus());
            pstm.setString(3, vb.getLote());
            pstm.setDate(4, vb.getValidade());
            pstm.setInt(5, vb.getQuantidade());
            pstm.setInt(6, vb.getCmm());
            pstm.setInt(7, vb.getSubalmoxarifado());
            pstm.setString(8, vb.getLocal());
            pstm.setString(9, vb.getOrigem());
            pstm.setString(10, vb.getDetalhe_origem());
            pstm.setInt(11, vb.getId());
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Alterado com Sucesso!");
            
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não Foi Possível Alterar o Registro!");
        }finally{
            con.desconecta();
        }
    }
    
    public void Excluir(ValidadesBean vb){
        
        String sql = "delete from validades where id = ?";
        String nome = "Deseja Deletar o Registro Selecionado?";
        int opcao = JOptionPane.showConfirmDialog(null, nome, "Deletar Registro", JOptionPane.YES_NO_OPTION);
        try{
            pstm = con.conecta().prepareStatement(sql);
            pstm.setInt(1, vb.getId());
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
}
