
import java.sql.Date;

/**
 *
 * @author Ramiro
 */
public class TodosBean {
    
    // Produtos
    private Integer pid;
    private String tipo;
    private String apresentacao;
    private String descricao;
    
    // Subalmoxarifados
    private Integer sid;
    private String nome;
    
    // Validades
    private Integer vid;
    private String status;
    private Integer codproduto;
    private String lote;
    private Date validade;
    private Integer quantidade;
    private Integer cmm;
    private Short subalmoxarifado; // código do subalmoxarifado
    private String local;
    private String origem;
    private String detalhe_origem;

    /**
     * @return the pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the apresentacao
     */
    public String getApresentacao() {
        return apresentacao;
    }

    /**
     * @param apresentacao the apresentacao to set
     */
    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the sid
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * @param sid the sid to set
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the vid
     */
    public Integer getVid() {
        return vid;
    }

    /**
     * @param vid the vid to set
     */
    public void setVid(Integer vid) {
        this.vid = vid;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the codproduto
     */
    public Integer getCodproduto() {
        return codproduto;
    }

    /**
     * @param codproduto the codproduto to set
     */
    public void setCodproduto(Integer codproduto) {
        this.codproduto = codproduto;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * @return the validade
     */
    public Date getValidade() {
        return validade;
    }

    /**
     * @param validade the validade to set
     */
    public void setValidade(Date validade) {
        this.validade = validade;
    }

    /**
     * @return the quantidade
     */
    public Integer getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the cmm
     */
    public Integer getCmm() {
        return cmm;
    }

    /**
     * @param cmm the cmm to set
     */
    public void setCmm(Integer cmm) {
        this.cmm = cmm;
    }

    /**
     * @return the subalmoxarifado
     */
    public Short getSubalmoxarifado() {
        return subalmoxarifado;
    }

    /**
     * @param subalmoxarifado the subalmoxarifado to set
     */
    public void setSubalmoxarifado(Short subalmoxarifado) {
        this.subalmoxarifado = subalmoxarifado;
    }

    /**
     * @return the local
     */
    public String getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * @return the origem
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * @param origem the origem to set
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    /**
     * @return the detalhe_origem
     */
    public String getDetalhe_origem() {
        return detalhe_origem;
    }

    /**
     * @param detalhe_origem the detalhe_origem to set
     */
    public void setDetalhe_origem(String detalhe_origem) {
        this.detalhe_origem = detalhe_origem;
    }
    
    
}
