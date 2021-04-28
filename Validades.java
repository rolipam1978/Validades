import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JasperFillManager;

/**
 *
 * @author Ramiro
 */

public class Validades extends javax.swing.JFrame {
    
    ConexaoBD con = new ConexaoBD();
    Statement stm;
    ResultSet rs;
    PreparedStatement pstm;
    
    ProdutosBean pb;
    ProdutosControl pc;
    ValidadesBean vb;
    ValidadesControl vc;
    SubalmoxarifadoBean sb;
    SubalmoxarifadoControl sc;
    TodosBean tb;
    
    // jComboBoxes
    String status;
    String tipo;
    String apresentacao;
    String descricao;
    Integer codproduto;
    String origem;
    Short subalmoxarifado; // Para Salvar na Tabela Subalmoxarifados
    Short codsubalmoxarifado; // Para Salvar na Tabela Validades
    Short codsubalmoxarifadosearch; // Para Pesquisar por Subalmoxarifados
    Short codsubalmoxarifadorel; // Para Gerar Relatório por Subalmoxarifados
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    java.sql.Date d = null;
    
    MaskFormatter data, mesAno, ano4Digitos;
    private String tipoRegistro = "";
    
    List<ProdutosBean> produtos;
    List<ValidadesBean> validades;
    List<SubalmoxarifadoBean> subalmoxarifados;
    List<TodosBean> todos;
    
    DefaultTableModel tmValidades = new DefaultTableModel(null, new String[]{"Validade", "Produto"}) {

        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    ListSelectionModel lsmValidades;
    
    private HashMap parametros; // Para passar o parâmetro para o relatório
    JasperPrint jp;
    JasperViewer jrv;

    /**
     * Creates new form Validades
     */
    public Validades() {
        initComponents();
        AtualizarCBSubalmoxarifado();
    }
    
    private void tbDadosLinhaSelecionada(JTable tb){

        if (tb.getSelectedRow() != -1) {
              tfId.setText(""+todos.get(tb.getSelectedRow()).getVid());
              if(todos.get(tb.getSelectedRow()).getStatus().equals("L")){
                cbStatus.setSelectedItem("L - Longe da Validade (1+ ano)");
              }else if(todos.get(tb.getSelectedRow()).getStatus().equals("D")){
                  cbStatus.setSelectedItem("D - Dentro da Validade (6m a 1a)");
              }else if(todos.get(tb.getSelectedRow()).getStatus().equals("P")){
                  cbStatus.setSelectedItem("P - Perto da Validade (2m a 6m)");
              }else if(todos.get(tb.getSelectedRow()).getStatus().equals("V")){
                  cbStatus.setSelectedItem("V - Vencendo no Mês");
              }else if(todos.get(tb.getSelectedRow()).getStatus().equals("E")){
                  cbStatus.setSelectedItem("E - Vencido em Estoque");
              }else if(todos.get(tb.getSelectedRow()).getStatus().equals("R")){
                  cbStatus.setSelectedItem("R - Vencido Retirado do Estoque");
              }else if(todos.get(tb.getSelectedRow()).getStatus().equals("F")){
                  cbStatus.setSelectedItem("F - Troca com Fornecedor");
              }else if(todos.get(tb.getSelectedRow()).getStatus().equals("T")){
                  cbStatus.setSelectedItem("T - Troca com Outra Instituição");
              }else{
                  cbStatus.setSelectedItem("SELECIONE");
              }
              if(todos.get(tb.getSelectedRow()).getTipo().equals("MEDICAMENTO")){
                  cbTipo.setSelectedItem("MEDICAMENTO");
                  tfTipo.setText("");
              }else if(todos.get(tb.getSelectedRow()).getTipo().equals("MANIPULADO")){
                  cbTipo.setSelectedItem("MANIPULADO");
                  tfTipo.setText("");
              }else if(todos.get(tb.getSelectedRow()).getTipo().equals("SUBSTÂNCIA")){
                  cbTipo.setSelectedItem("SUBSTÂNCIA");
                  tfTipo.setText("");
              }else{
                  cbTipo.setSelectedItem("OUTROS");
                  tfTipo.setText(todos.get(tb.getSelectedRow()).getTipo());
              }
              if(todos.get(tb.getSelectedRow()).getApresentacao().equals("COMPRIMIDO")){
                  cbApresentacao.setSelectedItem("COMPRIMIDO");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("CÁPSULA")){
                  cbApresentacao.setSelectedItem("CÁPSULA");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("DRÁGEA")){
                  cbApresentacao.setSelectedItem("DRÁGEA");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("AMPOLA")){
                  cbApresentacao.setSelectedItem("AMPOLA");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("FRASCO")){
                  cbApresentacao.setSelectedItem("FRASCO");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("FRASCO-AMPOLA")){
                  cbApresentacao.setSelectedItem("FRASCO-AMPOLA");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("BOLSA")){
                  cbApresentacao.setSelectedItem("BOLSA");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("SACHÊ")){
                  cbApresentacao.setSelectedItem("SACHÊ");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("BISNAGA")){
                  cbApresentacao.setSelectedItem("BISNAGA");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("POTE")){
                  cbApresentacao.setSelectedItem("POTE");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("ADESIVO")){
                  cbApresentacao.setSelectedItem("ADESIVO");
                  tfApresentacao.setText("");
              }else if(todos.get(tb.getSelectedRow()).getApresentacao().equals("UNIDADE")){
                  cbApresentacao.setSelectedItem("UNIDADE");
                  tfApresentacao.setText("");
              }else{
                  cbApresentacao.setSelectedItem("OUTROS");
                  tfApresentacao.setText(todos.get(tb.getSelectedRow()).getApresentacao());
              }
              AtualizarCBDescricao();
              cbDescricao.setSelectedItem(todos.get(tb.getSelectedRow()).getDescricao());
              //System.out.println(todos.get(tb.getSelectedRow()).getDescricao());
              
              if(todos.get(tb.getSelectedRow()).getOrigem().equals("C")){
                  cbOrigem.setSelectedItem("C - Compra");
              }else if(todos.get(tb.getSelectedRow()).getOrigem().equals("D")){
                  cbOrigem.setSelectedItem("D - Doação");
              }else if(todos.get(tb.getSelectedRow()).getOrigem().equals("E")){
                  cbOrigem.setSelectedItem("E - Empréstimo");
              }else if(todos.get(tb.getSelectedRow()).getOrigem().equals("T")){
                  cbOrigem.setSelectedItem("Troca");
              }else if(todos.get(tb.getSelectedRow()).getOrigem().equals("I")){
                  cbOrigem.setSelectedItem("I - Transferência Interna");
              }else{
                  cbOrigem.setSelectedItem("SELECIONE");
              }
              tfOrigem.setText(todos.get(tb.getSelectedRow()).getDetalhe_origem());
              cbSubalmoxarifado.setSelectedItem(todos.get(tb.getSelectedRow()).getNome());
              tfLocal.setText(todos.get(tb.getSelectedRow()).getLocal());
              tfLote.setText(todos.get(tb.getSelectedRow()).getLote());
              tfValidade.setText(sdf.format(todos.get(tb.getSelectedRow()).getValidade()));
              tfQuantidade.setText(String.valueOf(todos.get(tb.getSelectedRow()).getQuantidade()));
              tfCmm.setText(String.valueOf(todos.get(tb.getSelectedRow()).getCmm()));
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rbMesAnoPesquisa = new javax.swing.JRadioButton();
        jLabel20 = new javax.swing.JLabel();
        rbAnoPesquisa = new javax.swing.JRadioButton();
        try{
            mesAno = new MaskFormatter("##/####");
        }catch(Exception e){
            e.printStackTrace();
        }
        tfMesAnoPesquisa = new JFormattedTextField(mesAno);
        try{
            ano4Digitos = new MaskFormatter("####");
        }catch(Exception e){
            e.printStackTrace();
        }
        tfAnoPesquisa = new JFormattedTextField(ano4Digitos);
        btFiltrar = new javax.swing.JButton();
        btLimparPsquisa = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbSubalmoxarifadoPesquisa = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDados = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        rbAno = new javax.swing.JRadioButton();
        rbMesAno = new javax.swing.JRadioButton();
        try{
            mesAno = new MaskFormatter("##/####");
        }catch(Exception e){
            e.printStackTrace();
        }
        tfMesAno = new JFormattedTextField(mesAno);
        try{
            ano4Digitos = new MaskFormatter("####");
        }catch(Exception e){
            e.printStackTrace();
        }
        tfAno = new JFormattedTextField(ano4Digitos);
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btRelatorio = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        cbSubalmoxarifadoRelatorio = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        cbStatus = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbTipo = new javax.swing.JComboBox<>();
        tfTipo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbApresentacao = new javax.swing.JComboBox<>();
        tfApresentacao = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbDescricao = new javax.swing.JComboBox<>();
        tfDescricao = new javax.swing.JTextField();
        btIncluirProduto = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbOrigem = new javax.swing.JComboBox<>();
        tfOrigem = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbSubalmoxarifado = new javax.swing.JComboBox<>();
        btIncluirAlmoxarifado = new javax.swing.JButton();
        tfSubalmoxarifado = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfLocal = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tfLote = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        try{
            data = new MaskFormatter("##/##/####");
        }catch(Exception e){
            e.printStackTrace();
        }
        tfValidade = new JFormattedTextField(data);
        jLabel19 = new javax.swing.JLabel();
        tfQuantidade = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        tfCmm = new javax.swing.JTextField();
        btLimpar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btNovo = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Validades");
        setLocation(new java.awt.Point(360, 20));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Desenvolvido por Ramiro Oliveira Pamponet by rolipam Informática - 2021");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        buttonGroup2.add(rbMesAnoPesquisa);
        rbMesAnoPesquisa.setText("Mês/Ano:");
        rbMesAnoPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMesAnoPesquisaActionPerformed(evt);
            }
        });

        jLabel20.setText("Pesquisar Validades por:");

        buttonGroup2.add(rbAnoPesquisa);
        rbAnoPesquisa.setText("Ano:");
        rbAnoPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAnoPesquisaActionPerformed(evt);
            }
        });

        tfMesAnoPesquisa.setEditable(false);
        tfMesAnoPesquisa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tfAnoPesquisa.setEditable(false);
        tfAnoPesquisa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btFiltrar.setText("Filtrar");
        btFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFiltrarActionPerformed(evt);
            }
        });

        btLimparPsquisa.setText("Limpar");
        btLimparPsquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparPsquisaActionPerformed(evt);
            }
        });

        jLabel21.setText("MM/AAAA");

        jLabel22.setText("AAAA");

        jLabel16.setText("Selecione o Subalmoxarifado ou TODOS:");

        cbSubalmoxarifadoPesquisa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbSubalmoxarifadoPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS" }));
        cbSubalmoxarifadoPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSubalmoxarifadoPesquisaActionPerformed(evt);
            }
        });

        tbDados.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tbDados.setModel(tmValidades);
        tbDados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     
        lsmValidades = tbDados.getSelectionModel();     
        lsmValidades.addListSelectionListener(new ListSelectionListener() {                 
            public void valueChanged(ListSelectionEvent e){                     
                if(!e.getValueIsAdjusting()){                         
                    tbDadosLinhaSelecionada(tbDados);             
                }         
            }     
        });
        jScrollPane1.setViewportView(tbDados);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(cbSubalmoxarifadoPesquisa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rbAnoPesquisa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfAnoPesquisa))
                                    .addComponent(jLabel20)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rbMesAnoPesquisa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfMesAnoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22)))
                            .addComponent(jLabel16))
                        .addGap(0, 80, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btFiltrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btLimparPsquisa)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rbAnoPesquisa, rbMesAnoPesquisa});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbMesAnoPesquisa)
                    .addComponent(tfMesAnoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbAnoPesquisa)
                    .addComponent(tfAnoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbSubalmoxarifadoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFiltrar)
                    .addComponent(btLimparPsquisa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbSubalmoxarifadoPesquisa, tfAnoPesquisa, tfMesAnoPesquisa});

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Relatórios"));

        buttonGroup1.add(rbAno);
        rbAno.setText("Validades por Ano:");
        rbAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAnoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbMesAno);
        rbMesAno.setText("Validades por Mês/Ano:");
        rbMesAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMesAnoActionPerformed(evt);
            }
        });

        tfMesAno.setEditable(false);
        tfMesAno.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tfAno.setEditable(false);
        tfAno.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setText("AAAA");

        jLabel4.setText("MM/AAAA");

        btRelatorio.setText("Gerar Relatório");
        btRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioActionPerformed(evt);
            }
        });

        jLabel17.setText("Selecione o Subalmoxarifado:");

        cbSubalmoxarifadoRelatorio.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbSubalmoxarifadoRelatorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS" }));
        cbSubalmoxarifadoRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSubalmoxarifadoRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(rbAno)
                        .addComponent(rbMesAno)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfAno, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(tfMesAno))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addComponent(cbSubalmoxarifadoRelatorio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btRelatorio)
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rbAno, rbMesAno});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbAno)
                    .addComponent(tfAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbMesAno)
                    .addComponent(tfMesAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSubalmoxarifadoRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRelatorio)
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbSubalmoxarifadoRelatorio, tfAno, tfMesAno});

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastros"));

        jLabel2.setText("ID:");

        tfId.setEditable(false);
        tfId.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        cbStatus.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE", "L - Longe da Validade (1+ ano)", "D - Dentro da Validade (6m a 1a)", "P - Perto da Validade (2m a 6m)", "V - Vencendo no Mês", "E - Vencido em Estoque", "R - Vencido Retirado do Estoque", "F - Troca com Fornecedor", "T - Troca com Outra Instituição" }));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        jLabel5.setText("Status:");

        jLabel6.setText("Dados do Produto:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Tipo:");

        cbTipo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE", "MEDICAMENTO", "MANIPULADO", "SUBSTÂNCIA", "OUTROS" }));
        cbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoActionPerformed(evt);
            }
        });

        tfTipo.setEditable(false);
        tfTipo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfTipoFocusLost(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Apresentação:");

        cbApresentacao.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbApresentacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE", "COMPRIMIDO", "CÁPSULA", "DRÁGEA", "AMPOLA", "FRASCO", "FRASCO-AMPOLA", "BOLSA", "SACHÊ", "BISNAGA", "POTE", "ADESIVO", "UNIDADE", "OUTROS" }));
        cbApresentacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbApresentacaoActionPerformed(evt);
            }
        });

        tfApresentacao.setEditable(false);
        tfApresentacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfApresentacaoFocusLost(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Descrição:");

        cbDescricao.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbDescricao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE" }));
        cbDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDescricaoActionPerformed(evt);
            }
        });

        tfDescricao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfDescricaoFocusLost(evt);
            }
        });

        btIncluirProduto.setText("Incluir");
        btIncluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIncluirProdutoActionPerformed(evt);
            }
        });

        jLabel10.setText("Controle de Validade:");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Origem:");

        cbOrigem.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbOrigem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE", "C - Compra", "D - Doação", "E - Empréstimo", "T - Troca", "I - Transferência Interna" }));
        cbOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOrigemActionPerformed(evt);
            }
        });

        tfOrigem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfOrigemFocusLost(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Subalmoxarifado:");

        cbSubalmoxarifado.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbSubalmoxarifado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE" }));
        cbSubalmoxarifado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSubalmoxarifadoActionPerformed(evt);
            }
        });

        btIncluirAlmoxarifado.setText("Incluir");
        btIncluirAlmoxarifado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIncluirAlmoxarifadoActionPerformed(evt);
            }
        });

        tfSubalmoxarifado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfSubalmoxarifadoFocusLost(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Local:");

        tfLocal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfLocalFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel14.setText("Estante, Prateleira, Armário, Geladeira, etc");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Lote:");

        jLabel18.setText("Validade:");

        tfValidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Quantidade:");

        tfQuantidade.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel23.setText("CMM:");

        tfCmm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

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

        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btNovo.setText("Novo");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel24.setText("Descrever Detalhadamente a Origem");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel9))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cbDescricao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbApresentacao, 0, 230, Short.MAX_VALUE)
                                        .addComponent(cbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel19))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbSubalmoxarifado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tfLocal)
                                        .addComponent(tfLote)
                                        .addComponent(tfQuantidade)
                                        .addComponent(cbOrigem, javax.swing.GroupLayout.Alignment.TRAILING, 0, 230, Short.MAX_VALUE))))
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(tfSubalmoxarifado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btIncluirAlmoxarifado))
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfCmm, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btIncluirProduto)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfTipo)
                                            .addComponent(tfApresentacao)
                                            .addComponent(tfOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addComponent(tfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btLimpar)))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btExcluir, btLimpar, btNovo, btSalvar});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tfApresentacao, tfOrigem, tfTipo});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, jLabel12, jLabel13, jLabel15, jLabel19, jLabel7, jLabel8, jLabel9});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tfCmm, tfValidade});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbApresentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfApresentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(btIncluirProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbSubalmoxarifado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btIncluirAlmoxarifado)
                        .addComponent(tfSubalmoxarifado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tfLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tfLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(tfValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(tfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(tfCmm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btLimpar)
                    .addComponent(btExcluir)
                    .addComponent(btSalvar)
                    .addComponent(btNovo))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbApresentacao, cbDescricao, cbOrigem, cbStatus, cbSubalmoxarifado, cbTipo, tfCmm, tfDescricao, tfId, tfLocal, tfLote, tfOrigem, tfQuantidade, tfSubalmoxarifado, tfTipo, tfValidade});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jMenu1.setText("Arquivo");

        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");

        jMenuItem2.setText("Produtos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void rbMesAnoPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMesAnoPesquisaActionPerformed
        tfMesAnoPesquisa.setEditable(true);
        tfMesAnoPesquisa.requestFocus();
        tfMesAnoPesquisa.setText("");
        tfAnoPesquisa.setText("");
        tfAnoPesquisa.setEditable(false);
        
    }//GEN-LAST:event_rbMesAnoPesquisaActionPerformed

    private void rbAnoPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAnoPesquisaActionPerformed
        tfAnoPesquisa.setEditable(true);
        tfAnoPesquisa.requestFocus();
        tfAnoPesquisa.setText("");
        tfMesAnoPesquisa.setText("");
        tfMesAnoPesquisa.setEditable(false);
        
    }//GEN-LAST:event_rbAnoPesquisaActionPerformed

    private void cbSubalmoxarifadoPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSubalmoxarifadoPesquisaActionPerformed
        
        try{
            String sql = "select id from subalmoxarifados where nome like '" +String.valueOf(cbSubalmoxarifadoPesquisa.getSelectedItem())+ "'";
            con.conecta();
            con.executeSQL(sql);
            if(con.rs.first()){
                codsubalmoxarifadosearch = con.rs.getShort("id");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }
    }//GEN-LAST:event_cbSubalmoxarifadoPesquisaActionPerformed

    private void btFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiltrarActionPerformed
        String sql = "";
        String mes = tfMesAnoPesquisa.getText().substring(0, 2); 
        String ano = tfMesAnoPesquisa.getText().substring(3, 7);
        
        if(rbMesAnoPesquisa.isSelected() && cbSubalmoxarifadoPesquisa.getSelectedItem().equals("TODOS")){
            // Pesquisa Validades por Mês e Ano em TODOS os Subalmoxarifados
            sql = "select v.id as vid, v.status as status, v.codproduto as codproduto, p.id as pid, p.tipo as tipo, p.apresentacao as apresentacao, "
                    + "p.descricao as descricao, v.origem as origem, v.detalhe_origem as detalhe_origem, v.subalmoxarifado as subalmoxarifado, "
                    + "s.id as sid, s.nome as nome, v.local as local, v.lote as lote, v.validade as validade, v.quantidade as qtde, v.cmm as cmm "
                    + "from validades v, produtos p, subalmoxarifados s where month(validade) = " + mes + " and year(validade) = " + ano + " "
                    + "and v.codproduto = p.id and v.subalmoxarifado = s.id order by validade" ;
        }else if(rbAnoPesquisa.isSelected() && cbSubalmoxarifadoPesquisa.getSelectedItem().equals("TODOS")){
            // Pesquisa Validades por Ano em TODOS os Subalmoxarifados
            sql = "select v.id as vid, v.status as status, v.codproduto as codproduto, p.id as pid, p.tipo as tipo, p.apresentacao as apresentacao, "
                    + "p.descricao as descricao, v.origem as origem, v.detalhe_origem as detalhe_origem, v.subalmoxarifado as subalmoxarifado, "
                    + "s.id as sid, s.nome as nome, v.local as local, v.lote as lote, v.validade as validade, v.quantidade as qtde, v.cmm as cmm "
                    + "from validades v, produtos p, subalmoxarifados s where year(validade) = " + tfAnoPesquisa.getText() + " "
                    + "and v.codproduto = p.id and v.subalmoxarifado = s.id order by validade";
        }else if(rbMesAnoPesquisa.isSelected() && !cbSubalmoxarifadoPesquisa.getSelectedItem().equals("TODOS")){
            // Pesquisa Validades por Mês e Ano para o Subalmoxarifado Selecionado no ComboBox
            sql = "select v.id as vid, v.status as status, v.codproduto as codproduto, p.id as pid, p.tipo as tipo, p.apresentacao as apresentacao, "
                    + "p.descricao as descricao, v.origem as origem, v.detalhe_origem as detalhe_origem, v.subalmoxarifado as subalmoxarifado, "
                    + "s.id as sid, s.nome as nome, v.local as local, v.lote as lote, v.validade as validade, v.quantidade as qtde, v.cmm as cmm "
                    + "from validades v, produtos p, subalmoxarifados s "
                    + "where month(validade) = " + mes + " and year(validade) = " + ano + " and subalmoxarifado = "+ codsubalmoxarifadosearch + " "
                    + "and v.codproduto = p.id and v.subalmoxarifado = s.id order by validade";
        }else if(rbAnoPesquisa.isSelected() && !cbSubalmoxarifadoPesquisa.getSelectedItem().equals("TODOS")){
            // Pesquisa Validades por Ano para o Subalmoxarifado Selecionado no ComboBox
            sql = "select v.id as vid, v.status as status, v.codproduto as codproduto, p.id as pid, p.tipo as tipo, p.apresentacao as apresentacao, "
                    + "p.descricao as descricao, v.origem as origem, v.detalhe_origem as detalhe_origem, v.subalmoxarifado as subalmoxarifado, "
                    + "s.id as sid, s.nome as nome, v.local as local, v.lote as lote, v.validade as validade, v.quantidade as qtde, v.cmm as cmm "
                    + "from validades v, produtos p, subalmoxarifados s "
                    + "where year(validade) = " + tfAnoPesquisa.getText() + " and subalmoxarifado = "+ codsubalmoxarifadosearch + " "
                    + "and v.codproduto = p.id and v.subalmoxarifado = s.id order by validade";
        }
        
        con.conecta();
        tbDados.getColumnModel().getColumn(0).setPreferredWidth(10);
        tbDados.getColumnModel().getColumn(1).setPreferredWidth(100);
        
        while(tmValidades.getRowCount() > 0){
            tmValidades.removeRow(0);
        }
        //JOptionPane.showMessageDialog(null, sql);
        con.executeSQL(sql);
        todos = new ArrayList<>();
        try{
            while(con.rs.next()){
                tb = new TodosBean();
                tb.setVid(con.rs.getInt("vid"));
                tb.setStatus(con.rs.getString("status"));
                tb.setCodproduto(con.rs.getInt("codproduto"));
                tb.setLote(con.rs.getString("lote"));
                tb.setValidade(con.rs.getDate("validade"));
                tb.setQuantidade(con.rs.getInt("qtde"));
                tb.setCmm(con.rs.getInt("cmm"));
                tb.setSubalmoxarifado(con.rs.getShort("subalmoxarifado"));
                tb.setLocal(con.rs.getString("local"));
                tb.setOrigem(con.rs.getString("origem"));
                tb.setDetalhe_origem(con.rs.getString("detalhe_origem"));
                tb.setPid(con.rs.getInt("pid"));
                tb.setTipo(con.rs.getString("tipo"));
                tb.setApresentacao(con.rs.getString("apresentacao"));
                tb.setDescricao(con.rs.getString("descricao"));
                tb.setSid(con.rs.getInt("sid"));
                tb.setNome(con.rs.getString("nome"));
                todos.add(tb);
            }
            
            if(todos.size() == 0){
                
            }else{
                for(int i = 0 ; i < todos.size() ; i++){
                    tmValidades.addRow(new String[] {
                        sdf.format(todos.get(i).getValidade()),
                        todos.get(i).getDescricao()
                    });
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }
        
    }//GEN-LAST:event_btFiltrarActionPerformed

    private void btLimparPsquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparPsquisaActionPerformed
        tmValidades.setNumRows(0);
        tfMesAnoPesquisa.setText("");
        tfAnoPesquisa.setText("");
        cbSubalmoxarifadoPesquisa.setSelectedItem("TODOS");
    }//GEN-LAST:event_btLimparPsquisaActionPerformed

    private void rbAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAnoActionPerformed
        tfAno.setEditable(true);
        tfAno.requestFocus();
        tfAno.setText("");
        tfMesAno.setText("");
        tfMesAno.setEditable(false);
        
    }//GEN-LAST:event_rbAnoActionPerformed

    private void rbMesAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMesAnoActionPerformed
        tfMesAno.setEditable(true);
        tfMesAno.requestFocus();
        tfMesAno.setText("");
        tfAno.setText("");
        tfAno.setEditable(false);
        
    }//GEN-LAST:event_rbMesAnoActionPerformed

    private void cbSubalmoxarifadoRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSubalmoxarifadoRelatorioActionPerformed
        
        try{
            String sql = "select id from subalmoxarifados where nome like '" +String.valueOf(cbSubalmoxarifadoRelatorio.getSelectedItem())+ "'";
            con.conecta();
            con.executeSQL(sql);
            if(con.rs.first()){
                codsubalmoxarifadorel = con.rs.getShort("id");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }   
    }//GEN-LAST:event_cbSubalmoxarifadoRelatorioActionPerformed

    private void btRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioActionPerformed
        if(rbAno.isSelected() && cbSubalmoxarifadoRelatorio.getSelectedItem().equals("TODOS")){
            // imprimir relatório de validades por ano para todos os subalmoxarifados
            ValidadesAno();
            
        }else if(rbAno.isSelected() && !cbSubalmoxarifadoRelatorio.getSelectedItem().equals("TODOS")){
            // imprimir relatório de validades por ano para o subalmoxarifado selecionado
            ValidadesAnoPorSubalmoxarifado();
            
        }else if(rbMesAno.isSelected() && cbSubalmoxarifadoRelatorio.getSelectedItem().equals("TODOS")){
            // imprimir relatório de validades por mês/ano para todos os subalmoxarifados
            ValidadesMesAno();
            
        }else if(rbMesAno.isSelected() && !cbSubalmoxarifadoRelatorio.getSelectedItem().equals("TODOS")){
            // imprimir relatório de validades por mês/ano para o subalmoxarifado selecionado
            ValidadesMesAnoPorSubalmoxarifado();
        }
    }//GEN-LAST:event_btRelatorioActionPerformed

    private void ValidadesAno(){
        try{
            parametros = new HashMap();
            parametros.put("ano", "%"+tfAno.getText());
            jp = JasperFillManager.fillReport("./Relatorios/ValidadesAno.jasper", parametros, con.conecta());
            jrv = new JasperViewer(jp, false);
            jrv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jrv.setVisible(true);
            jrv.setExtendedState(MAXIMIZED_BOTH);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Tentar Imprimir Relatório.");
            e.printStackTrace();
        }         
    }
    
    private void ValidadesAnoPorSubalmoxarifado(){
        try{
            parametros = new HashMap();
            parametros.put("ano", "%"+tfAno.getText());
            parametros.put("subalmoxarifado", subalmoxarifado); // Pego o Código do Subalmoxarifado
            jp = JasperFillManager.fillReport("./Relatorios/ValidadesAnoSubalmoxarifado.jasper", parametros, con.conecta());
            jrv = new JasperViewer(jp, false);
            jrv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jrv.setVisible(true);
            jrv.setExtendedState(MAXIMIZED_BOTH);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Tentar Imprimir Relatório.");
            e.printStackTrace();
        }         
    }
    
    private void ValidadesMesAno(){
        try{
            parametros = new HashMap();
            parametros.put("mesAno", "%"+tfMesAno.getText());
            jp = JasperFillManager.fillReport("./Relatorios/ValidadesMesAno.jasper", parametros, con.conecta());
            jrv = new JasperViewer(jp, false);
            jrv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jrv.setVisible(true);
            jrv.setExtendedState(MAXIMIZED_BOTH);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Tentar Imprimir Relatório.");
            e.printStackTrace();
        }         
    }
    
    private void ValidadesMesAnoPorSubalmoxarifado(){
        try{
            parametros = new HashMap();
            parametros.put("MesAno", "%"+tfMesAno.getText());
            parametros.put("subalmoxarifado", subalmoxarifado); // Pego o Código do Subalmoxarifado
            jp = JasperFillManager.fillReport("./Relatorios/ValidadesMesAnoSubalmoxarifado.jasper", parametros, con.conecta());
            jrv = new JasperViewer(jp, false);
            jrv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jrv.setVisible(true);
            jrv.setExtendedState(MAXIMIZED_BOTH);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Tentar Imprimir Relatório.");
            e.printStackTrace();
        }         
    }
    
    private Integer gerarIDProduto(){
        Integer id = 0;
        try{
            con.conecta();
            String sql = "select max(id) as id from produtos";
            con.executeSQL(sql);
            if(con.rs.first()){
                id = con.rs.getInt("id") + 1;
            }else{
                id = 1;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        } 
        return id;
    }
    
    private void AtualizarCBDescricao(){
        pb = new ProdutosBean();
        produtos = new ArrayList<>();
        if(cbTipo.getSelectedItem().equals("SELECIONE")){
            
        }else if(cbTipo.getSelectedItem().equals("OUTROS")){
            tipo = tfTipo.getText();
        }else{
            tipo = String.valueOf(cbTipo.getSelectedItem());
        }
        String sql = "select * from produtos where tipo like '" + tipo + "' order by descricao";
        cbDescricao.removeAllItems();
        cbDescricao.addItem("SELECIONE");
        try{
            con.conecta();
            con.executeSQL(sql);
            while(con.rs.next()){
                cbDescricao.addItem(con.rs.getString("descricao"));
                pb.setId(con.rs.getInt("id"));
                pb.setDescricao(con.rs.getString("descricao"));
                pb.setTipo(con.rs.getString("tipo"));
                pb.setApresentacao(con.rs.getString("apresentacao"));
                produtos.add(pb);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }
    }
    
    private Integer gerarIDSubalmoxarifado(){
        Integer id = 0;
        try{
            con.conecta();
            String sql = "select max(id) as id from subalmoxarifados";
            con.executeSQL(sql);
            if(con.rs.first()){
                id = con.rs.getInt("id") + 1;
            }else{
                id = 1;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        } 
        return id;        
    }
    
    private void AtualizarCBSubalmoxarifado(){
        sb = new SubalmoxarifadoBean();
        subalmoxarifados = new ArrayList<>();
        String sql = "select * from subalmoxarifados order by nome";
        cbSubalmoxarifado.removeAllItems();
        cbSubalmoxarifadoPesquisa.removeAllItems();
        cbSubalmoxarifadoRelatorio.removeAllItems();
        cbSubalmoxarifado.addItem("SELECIONE");
        cbSubalmoxarifadoPesquisa.addItem("TODOS");
        cbSubalmoxarifadoRelatorio.addItem("TODOS");
        try{
            con.conecta();
            con.executeSQL(sql);
            while(con.rs.next()){
                cbSubalmoxarifado.addItem(con.rs.getString("nome"));
                cbSubalmoxarifadoPesquisa.addItem(con.rs.getString("nome"));
                cbSubalmoxarifadoRelatorio.addItem(con.rs.getString("nome"));
                sb.setId(con.rs.getInt("id"));
                sb.setNome(con.rs.getString("nome"));
                subalmoxarifados.add(sb);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }    
    }
    
    private void gerarID(){
        try{
            con.conecta();
            String sql = "select max(id) as id from validades";
            con.executeSQL(sql);
            if(con.rs.first()){
                tfId.setText(""+(con.rs.getInt("id") + 1));
            }else{
                tfId.setText("1");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }         
    }
    
        private void Deletar(){
        ValidadesBean vb = new ValidadesBean();
        ValidadesControl vc = new ValidadesControl();
        vb.setId(Integer.parseInt(tfId.getText()));
        vc.Excluir(vb);
    }
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Produtos prod = new Produtos();
        prod.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        tipoRegistro = "novo";
        gerarID();
        cbStatus.requestFocus();
    }//GEN-LAST:event_btNovoActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        if (tfId.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Selecione um Registro na Tabela para Alterar ou Clique em Novo.");
        }else{
            vb = new ValidadesBean();
            vb.setId(Integer.parseInt(tfId.getText()));
            vb.setStatus(status);
            vb.setCodproduto(codproduto);
            vb.setOrigem(origem);
            vb.setDetalhe_origem(tfOrigem.getText());
            vb.setSubalmoxarifado(codsubalmoxarifado);
            vb.setLocal(tfLocal.getText());
            vb.setLote(tfLote.getText());
            try {
                vb.setValidade(d = new java.sql.Date(sdf.parse(tfValidade.getText()).getTime()));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            vb.setQuantidade(Integer.parseInt(tfQuantidade.getText()));
            vb.setCmm(Integer.parseInt(tfCmm.getText()));

            vc = new ValidadesControl();

            if(tipoRegistro.equals("novo")){
                // Salvar
                vc.Salvar(vb);
                tmValidades.setNumRows(0);
            }else{
                // Alterar um Registro Selecionado na Tabela
                if(tbDados.getSelectedRow() != -1){
                    vc.Alterar(vb);
                    tmValidades.setNumRows(0);
                }
            }
            LimparCampos();
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // Selecione um item da Tabela
        if(tbDados.getSelectedRow() != -1){
            Deletar();
            LimparCampos();
            AtualizarCBSubalmoxarifado();
            cbSubalmoxarifado.setSelectedItem("SELECIONE");
            AtualizarCBDescricao();
            cbDescricao.setSelectedItem("SELECIONE");
            tmValidades.setNumRows(0);
        }else{
            JOptionPane.showMessageDialog(null, "Selecione um Registro na Tabela para Excluir.");
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        LimparCampos();
    }//GEN-LAST:event_btLimparActionPerformed

    private void tfLocalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfLocalFocusLost
        String local = tfLocal.getText();
        tfLocal.setText(local.toUpperCase());
    }//GEN-LAST:event_tfLocalFocusLost

    private void tfSubalmoxarifadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSubalmoxarifadoFocusLost
        String subalmoxarifado = tfSubalmoxarifado.getText();
        tfSubalmoxarifado.setText(subalmoxarifado.toUpperCase());
    }//GEN-LAST:event_tfSubalmoxarifadoFocusLost

    private void btIncluirAlmoxarifadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIncluirAlmoxarifadoActionPerformed
        sb = new SubalmoxarifadoBean();
        sc = new SubalmoxarifadoControl();
        String sql = "select nome from subalmoxarifados where nome like '" +tfSubalmoxarifado.getText()+ "'";
        try{
            con.conecta();
            con.executeSQL(sql);
            if(con.rs.first()){
                JOptionPane.showMessageDialog(null, "Já Existe um Subalmoxarifado com Essa Descrição.");
                tfSubalmoxarifado.setText("");
                cbSubalmoxarifado.setSelectedItem(con.rs.getString("nome"));
            }else{
                sb.setId(gerarIDSubalmoxarifado());
                sb.setNome(tfSubalmoxarifado.getText());
                sc.Incluir(sb);
                AtualizarCBSubalmoxarifado();
                tfSubalmoxarifado.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally{
            con.desconecta();
        }
    }//GEN-LAST:event_btIncluirAlmoxarifadoActionPerformed

    private void cbSubalmoxarifadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSubalmoxarifadoActionPerformed
        try{
            String sql = "select id from subalmoxarifados where nome like '" +String.valueOf(cbSubalmoxarifado.getSelectedItem())+ "'";
            con.conecta();
            con.executeSQL(sql);
            if(con.rs.first()){
                codsubalmoxarifado = con.rs.getShort("id");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }
    }//GEN-LAST:event_cbSubalmoxarifadoActionPerformed

    private void tfOrigemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfOrigemFocusLost
        String origem = tfOrigem.getText();
        tfOrigem.setText(origem.toUpperCase());
    }//GEN-LAST:event_tfOrigemFocusLost

    private void cbOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOrigemActionPerformed
        if(cbOrigem.getSelectedItem().equals("C - Compra")){
            origem = "C";
        }else if(cbOrigem.getSelectedItem().equals("D - Doação")){
            origem = "D";
        }else if(cbOrigem.getSelectedItem().equals("E - Empréstimo")){
            origem = "E";
        }else if(cbOrigem.getSelectedItem().equals("T - Troca")){
            origem = "T";
        }else if(cbOrigem.getSelectedItem().equals("I - Transferência Interna")){
            origem = "I";
        }else{
            origem = null;
        }
    }//GEN-LAST:event_cbOrigemActionPerformed

    private void btIncluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIncluirProdutoActionPerformed
        pb = new ProdutosBean();
        pc = new ProdutosControl();
        String sql = "select descricao from produtos where descricao like '" +tfDescricao.getText()+ "'";
        try{
            con.conecta();
            con.executeSQL(sql);
            if(con.rs.first()){
                JOptionPane.showMessageDialog(null, "Já Existe um Registro com Essa Descrição.");
                tfDescricao.setText("");
                cbDescricao.setSelectedItem(con.rs.getString("descricao"));
            }else{
                pb.setId(gerarIDProduto());
                if(cbTipo.getSelectedItem().equals("SELECIONE")){
                    JOptionPane.showMessageDialog(null, "Selecione um Tipo para Continuar");
                }else if (cbTipo.getSelectedItem().equals("OUTROS")){
                    pb.setTipo(tfTipo.getText());
                }else{
                    pb.setTipo(String.valueOf(cbTipo.getSelectedItem()));
                }
                if(cbApresentacao.getSelectedItem().equals("SELECIONE")){
                    JOptionPane.showMessageDialog(null, "Selecione uma Apresentação para Continuar");
                }else if(cbApresentacao.getSelectedItem().equals("OUTROS")){
                    pb.setApresentacao(tfApresentacao.getText());
                }else{
                    pb.setApresentacao(String.valueOf(cbApresentacao.getSelectedItem()));
                }
                pb.setDescricao(tfDescricao.getText());
                pc.Incluir(pb);
                AtualizarCBDescricao();
                tfDescricao.setText("");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }
    }//GEN-LAST:event_btIncluirProdutoActionPerformed

    private void tfDescricaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfDescricaoFocusLost
        String descricao = tfDescricao.getText();
        tfDescricao.setText(descricao.toUpperCase());
    }//GEN-LAST:event_tfDescricaoFocusLost

    private void cbDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDescricaoActionPerformed

        String sql = "select id from produtos where descricao like '" + String.valueOf(cbDescricao.getSelectedItem()) + "'";
        try{
            con.conecta();
            con.executeSQL(sql);
            if(con.rs.first()){
                codproduto = con.rs.getInt("id");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.desconecta();
        }
    }//GEN-LAST:event_cbDescricaoActionPerformed

    private void tfApresentacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfApresentacaoFocusLost
        String apresentacao = tfApresentacao.getText();
        tfApresentacao.setText(apresentacao.toUpperCase());
    }//GEN-LAST:event_tfApresentacaoFocusLost

    private void cbApresentacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbApresentacaoActionPerformed

        if(cbApresentacao.getSelectedItem().equals("SELECIONE")){

        }else if(cbApresentacao.getSelectedItem().equals("OUTROS")){
            tfApresentacao.setEditable(true);
            tfApresentacao.requestFocus();
            apresentacao = tfApresentacao.getText();
        }else{
            apresentacao = String.valueOf(cbApresentacao.getSelectedItem());
        }

    }//GEN-LAST:event_cbApresentacaoActionPerformed

    private void tfTipoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfTipoFocusLost
        String tipoProduto = tfTipo.getText();
        tfTipo.setText(tipoProduto.toUpperCase());
        if(tipoRegistro.equals("novo")){
            AtualizarCBDescricao();
        }
    }//GEN-LAST:event_tfTipoFocusLost

    private void cbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoActionPerformed
        if(cbTipo.getSelectedItem().equals("SELECIONE")){

        }else if(cbTipo.getSelectedItem().equals("OUTROS")){
            tfTipo.setEditable(true);
            tfTipo.requestFocus();
            tipo = tfTipo.getText();
            AtualizarCBDescricao();
        }else{
            tipo = String.valueOf(cbTipo.getSelectedItem());
            AtualizarCBDescricao();
        }
    }//GEN-LAST:event_cbTipoActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        if(cbStatus.getSelectedItem().equals("L - Longe da Validade (1+ ano)")){
            status = "L";
        }else if(cbStatus.getSelectedItem().equals("D - Dentro da Validade (6m a 1a)")){
            status = "D";
        }else if(cbStatus.getSelectedItem().equals("P - Perto da Validade (2m a 6m)")){
            status = "P";
        }else if(cbStatus.getSelectedItem().equals("V - Vencendo no Mês")){
            status = "V";
        }else if(cbStatus.getSelectedItem().equals("E - Vencido em Estoque")){
            status = "E";
        }else if(cbStatus.getSelectedItem().equals("R - Vencido Retirado do Estoque")){
            status = "R";
        }else if(cbStatus.getSelectedItem().equals("F - Troca com Fornecedor")){
            status = "F";
        }else if(cbStatus.getSelectedItem().equals("T - Troca com Outra Instituição")){
            status = "T";
        }else{
            status = null;
        }
    }//GEN-LAST:event_cbStatusActionPerformed

    private void LimparCampos(){
        tfId.setText("");
        cbStatus.setSelectedItem("SELECIONE");
        cbTipo.setSelectedItem("SELECIONE");
        tfTipo.setText("");
        tfTipo.setEditable(false);
        cbApresentacao.setSelectedItem("SELECIONE");
        tfApresentacao.setText("");
        tfApresentacao.setEditable(false);
        cbDescricao.removeAllItems();
        cbDescricao.addItem("SELECIONE");
        cbDescricao.setSelectedItem("SELECIONE");
        tfDescricao.setText("");
        cbOrigem.setSelectedItem("SELECIONE");
        tfOrigem.setText("");
        cbSubalmoxarifado.setSelectedItem("SELECIONE");
        tfSubalmoxarifado.setText("");
        tfLocal.setText("");
        tfLote.setText("");
        tfValidade.setText("");
        tfQuantidade.setText("");
        tfCmm.setText("");
        tipoRegistro = "";
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
            java.util.logging.Logger.getLogger(Validades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Validades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Validades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Validades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Validades().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btFiltrar;
    private javax.swing.JButton btIncluirAlmoxarifado;
    private javax.swing.JButton btIncluirProduto;
    private javax.swing.JButton btLimpar;
    private javax.swing.JButton btLimparPsquisa;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btRelatorio;
    private javax.swing.JButton btSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbApresentacao;
    private javax.swing.JComboBox<String> cbDescricao;
    private javax.swing.JComboBox<String> cbOrigem;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<String> cbSubalmoxarifado;
    private javax.swing.JComboBox<String> cbSubalmoxarifadoPesquisa;
    private javax.swing.JComboBox<String> cbSubalmoxarifadoRelatorio;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbAno;
    private javax.swing.JRadioButton rbAnoPesquisa;
    private javax.swing.JRadioButton rbMesAno;
    private javax.swing.JRadioButton rbMesAnoPesquisa;
    private javax.swing.JTable tbDados;
    private javax.swing.JTextField tfAno;
    private javax.swing.JTextField tfAnoPesquisa;
    private javax.swing.JTextField tfApresentacao;
    private javax.swing.JTextField tfCmm;
    private javax.swing.JTextField tfDescricao;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfLocal;
    private javax.swing.JTextField tfLote;
    private javax.swing.JTextField tfMesAno;
    private javax.swing.JTextField tfMesAnoPesquisa;
    private javax.swing.JTextField tfOrigem;
    private javax.swing.JTextField tfQuantidade;
    private javax.swing.JTextField tfSubalmoxarifado;
    private javax.swing.JTextField tfTipo;
    private javax.swing.JTextField tfValidade;
    // End of variables declaration//GEN-END:variables
}
