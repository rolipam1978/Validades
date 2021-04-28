
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ramiro
 */
public class Produtos extends javax.swing.JFrame {
    
    ConexaoBD con = new ConexaoBD();
    Statement stm;
    ResultSet rs;
    PreparedStatement pstm;
    
    ProdutosBean pb;
    ProdutosControl pc;
    List<ProdutosBean> produtos;
    String tipo;
    
    DefaultTableModel tmProdutos = new DefaultTableModel(null, new String[]{"Descrição", "Apresentação", "Tipo"}) {

        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    ListSelectionModel lsmProdutos;

    /**
     * Creates new form Produtos
     */
    public Produtos() {
        initComponents();
        PreencherCBProduto();
        PreencherTBProdutos();
    }
    
    private void PreencherCBProduto(){
        String sql = "select distinct(tipo) from produtos order by tipo";
        
        con.conecta();
        con.executeSQL(sql);
        try{
            while(con.rs.next()){
                cbFiltrar.addItem(con.rs.getString("tipo"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }
    }
    
    private void FiltrarTBProdutos(){
        String sql = "select * from produtos where tipo like '" + tipo + "' order by descricao";
        
        con.conecta();
        tbProdutos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbProdutos.getColumnModel().getColumn(1).setPreferredWidth(30);
        tbProdutos.getColumnModel().getColumn(2).setPreferredWidth(20);
        
        while(tmProdutos.getRowCount() > 0){
            tmProdutos.removeRow(0);
        }        
        
        con.executeSQL(sql);
        produtos = new ArrayList<>();
        try{
            while(con.rs.next()){
                pb = new ProdutosBean();
                pb.setId(con.rs.getInt("id"));
                pb.setTipo(con.rs.getString("tipo"));
                pb.setApresentacao(con.rs.getString("apresentacao"));
                pb.setDescricao(con.rs.getString("descricao"));
                produtos.add(pb);
            }
            if(produtos.size() == 0){
                
            }else{
                for(int i = 0; i < produtos.size(); i++){
                    tmProdutos.addRow(new String [] {
                        produtos.get(i).getDescricao(),
                        produtos.get(i).getApresentacao(),
                        produtos.get(i).getTipo()
                    });
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }        
    }
    
    private void PreencherTBProdutos(){
        String sql = "select * from produtos order by descricao";
        
        con.conecta();
        tbProdutos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbProdutos.getColumnModel().getColumn(1).setPreferredWidth(30);
        tbProdutos.getColumnModel().getColumn(2).setPreferredWidth(20);
        
        while(tmProdutos.getRowCount() > 0){
            tmProdutos.removeRow(0);
        }        
        
        con.executeSQL(sql);
        produtos = new ArrayList<>();
        try{
            while(con.rs.next()){
                pb = new ProdutosBean();
                pb.setId(con.rs.getInt("id"));
                pb.setTipo(con.rs.getString("tipo"));
                pb.setApresentacao(con.rs.getString("apresentacao"));
                pb.setDescricao(con.rs.getString("descricao"));
                produtos.add(pb);
            }
            if(produtos.size() == 0){
                
            }else{
                for(int i = 0; i < produtos.size(); i++){
                    tmProdutos.addRow(new String [] {
                        produtos.get(i).getDescricao(),
                        produtos.get(i).getApresentacao(),
                        produtos.get(i).getTipo()
                    });
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }
    }
    
    private void tbProdutosLinhaSelecionada(JTable tb){
        if (tb.getSelectedRow() != -1) {
            tfId.setText(String.valueOf(produtos.get(tb.getSelectedRow()).getId()));
            tfTipo.setText(produtos.get(tb.getSelectedRow()).getTipo());
            tfApresentacao.setText(produtos.get(tb.getSelectedRow()).getApresentacao());
            tfDescricao.setText(produtos.get(tb.getSelectedRow()).getDescricao()); 
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbProdutos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        tfTipo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfApresentacao = new javax.swing.JTextField();
        tfDescricao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btLimpar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbFiltrar = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Produtos");
        setLocation(new java.awt.Point(500, 200));
        setResizable(false);

        tbProdutos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tbProdutos.setModel(tmProdutos);
        tbProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     
        lsmProdutos = tbProdutos.getSelectionModel();     
        lsmProdutos.addListSelectionListener(new ListSelectionListener() {                 
            public void valueChanged(ListSelectionEvent e){                     
                if(!e.getValueIsAdjusting()){                         
                    tbProdutosLinhaSelecionada(tbProdutos);             
                }         
            }     
        });
        jScrollPane1.setViewportView(tbProdutos);

        jLabel1.setText("ID:");

        tfId.setEditable(false);
        tfId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setText("Tipo:");

        jLabel3.setText("Apresentação:");

        jLabel4.setText("Descrição:");

        btLimpar.setText("Limpar");
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        btExcluir.setText("Excluir");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        jLabel5.setText("Filtrar Dados da Tabela por Tipo");

        cbFiltrar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE" }));
        cbFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 252, Short.MAX_VALUE)
                        .addComponent(btAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btLimpar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfApresentacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfDescricao)
                            .addComponent(tfTipo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFiltrar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfApresentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btLimpar)
                    .addComponent(btExcluir)
                    .addComponent(btAlterar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        LimparCampos();
        cbFiltrar.setSelectedItem("SELECIONE");
    }//GEN-LAST:event_btLimparActionPerformed

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        if(tbProdutos.getSelectedRow() != -1){
            Alterar();
            LimparCampos();
            PreencherTBProdutos();
        }else{
            JOptionPane.showMessageDialog(null, "Selecione um Registro na Tabela pata Alterar");
        }
    }//GEN-LAST:event_btAlterarActionPerformed

    private void Alterar(){
        pb = new ProdutosBean();
        pc = new ProdutosControl();
        pb.setTipo(tfTipo.getText());
        pb.setApresentacao(tfApresentacao.getText());
        pb.setDescricao(tfDescricao.getText());
        pb.setId(Integer.parseInt(tfId.getText()));
        pc.Editar(pb);
    }
    
    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        if(tbProdutos.getSelectedRow() != -1){
            Deletar();
            LimparCampos();   
            PreencherTBProdutos();
        }else{
            JOptionPane.showMessageDialog(null, "Selecione um Registro na Tabela pata Excluir");
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void cbFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFiltrarActionPerformed
        if(cbFiltrar.getSelectedItem().equals("SELECIONE")){
            PreencherTBProdutos();
            LimparCampos();
        }else{
            tipo = String.valueOf(cbFiltrar.getSelectedItem());
            FiltrarTBProdutos();
            LimparCampos();
        }
    }//GEN-LAST:event_cbFiltrarActionPerformed

    private void Deletar(){
        pb = new ProdutosBean();
        pc = new ProdutosControl();
        pb.setId(Integer.parseInt(tfId.getText()));
        pc.Excluir(pb);   
    }
    
    private void LimparCampos(){
        tbProdutos.getSelectionModel().clearSelection();
        tfId.setText("");
        tfTipo.setText("");
        tfApresentacao.setText("");
        tfDescricao.setText("");   
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Produtos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Produtos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Produtos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Produtos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Produtos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btLimpar;
    private javax.swing.JComboBox<String> cbFiltrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbProdutos;
    private javax.swing.JTextField tfApresentacao;
    private javax.swing.JTextField tfDescricao;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfTipo;
    // End of variables declaration//GEN-END:variables
}
