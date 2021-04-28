import java.sql.Date;

/**
 *
 * @author Ramiro
 */
public class ValidadesBean {
    
    private Integer id;
    private Integer codproduto;
    private String status;
    private String lote;
    private Date validade;
    private Integer quantidade;
    private Integer cmm; // Consumo Médio Mensal
    private Short subalmoxarifado; // Código do Subalmoxarifado
    private String local;
    private String origem;
    private String detalhe_origem;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
