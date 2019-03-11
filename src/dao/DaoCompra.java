package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.Compra;
import pojo.ItensCompra;
import pojo.Compra;
import pojo.ItensPedidoDeCompra;
import pojo.PedidoDeCompra;




import util.Utilitarios;

public class DaoCompra {

    private Compra compra;
    
  
    private final String SQL_INCLUIR
            = "INSERT INTO COMPRA VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_ALTERAR
            = "UPDATE COMPRA SET DATA = ?, VALORTOTAL = ?, DESCONTO = ?, VALORLIQUIDO = ?,"
            + "STATUS = ?, CP = ?, IDFORNECEDOR = ?, IDCONDICAODEPAGAMENTO = ?, IDPEDIDODECOMPRA = ? WHERE ID = ?";
    private final String SQL_EXCLUIR
            = "DELETE FROM COMPRA WHERE ID = ?";
    private final String SQL_CONSULTAR
            = "SELECT * FROM COMPRA WHERE ID = ?";
    public static final String SQL_PESQUISAR
            = "SELECT COMPRA.ID, COMPRA.DATA,COMPRA.VALORLIQUIDO,"
            + " COMPRA.STATUS,FORNECEDOR.RAZAOSOCIAL AS C_FORNECEDOR,CONDICAODEPAGAMENTO.DESCRICAO AS C_CONDICAODEPAGAMENTO, COMPRA.CP AS CP FROM FORNECEDOR"
            + " LEFT JOIN COMPRA ON COMPRA.IDFORNECEDOR = FORNECEDOR.ID "
            + " INNER JOIN CONDICAODEPAGAMENTO ON CONDICAODEPAGAMENTO.ID = COMPRA.IDCONDICAODEPAGAMENTO ";
    public static final String SQL_PESQUISAR_ATIVOS
            = "SELECT COMPRA.ID, COMPRA.DATA,COMPRA.VALORLIQUIDO,"
            + " COMPRA.STATUS,FORNECEDOR.RAZAOSOCIAL AS C_FORNECEDOR,CONDICAODEPAGAMENTO.DESCRICAO AS C_CONDICAODEPAGAMENTO, COMPRA.CP AS CP FROM FORNECEDOR"
            + " LEFT JOIN COMPRA ON COMPRA.IDFORNECEDOR = FORNECEDOR.ID "
            + " INNER JOIN CONDICAODEPAGAMENTO ON CONDICAODEPAGAMENTO.ID = COMPRA.IDCONDICAODEPAGAMENTO AND COMPRA.STATUS = 'CF' AND CP = 'N' ";
     public static final String SQL_COMBOBOX = "SELECT ID, DATA FROM COMPRA";
    
    
    
    public DaoCompra(Compra compra) {
        this.compra = compra;
       }

    public boolean incluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
            compra.setId(Conexao.getGenerator("GEN_COMPRA"));
            ps.setInt(1, compra.getId());
            ps.setDate(2, Utilitarios.converteParaBanco(compra.getData()));
            ps.setBigDecimal(3, compra.getValortotal());
            ps.setBigDecimal(4, compra.getDesconto());
            ps.setBigDecimal(5, compra.getValorliquido());
            ps.setString(6, compra.getStatus());
            ps.setString(7, (compra.isCp()? "S" : "N"));
            ps.setInt(8, compra.getFornecedor().getId());
            ps.setInt(9, compra.getCondicaodepagamento().getId());
            
            if (compra.getPedidodecompra().getId() == 0) {
                 ps.setNull(10, java.sql.Types.INTEGER);
            } else {
                 ps.setInt(10, compra.getPedidodecompra().getId());
            }
            ps.executeUpdate();
                for (int i = 0; i < compra.getItensCompra().size(); i++) {
                DaoItensCompra daoItemCompra = new DaoItensCompra(compra.getItensCompra().get(i));
                compra.getItensCompra().get(i).setCompra(compra);
                daoItemCompra.incluir();
            }
            
             JOptionPane.showMessageDialog(null,"Compra realizada com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir a  Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
            ps.setDate(1, Utilitarios.converteParaBanco(compra.getData()));
            ps.setBigDecimal(2, compra.getValortotal());
            ps.setBigDecimal(3, compra.getDesconto());
            ps.setBigDecimal(4, compra.getValorliquido());
            ps.setString(5, compra.getStatus());
            ps.setString(6, (compra.isCp()?"S" : "N"));
            ps.setInt(7, compra.getFornecedor().getId());
            ps.setInt(8, compra.getCondicaodepagamento().getId());
            
            if (compra.getPedidodecompra().getId() == 0){
               ps.setNull(9, java.sql.Types.INTEGER); 
            } else {
                 ps.setInt(9, compra.getPedidodecompra().getId());
            }
            ps.setInt(10, compra.getId());
            ps.executeUpdate();
             
            for (int i = 0; i < compra.getItensCompra().size(); i++) {
                DaoItensCompra daoItensCompra = new DaoItensCompra(compra.getItensCompra().get(i));
                if (compra.getItensCompra().get(i).getId() > 0) {
                    daoItensCompra.alterar();
                } else {
                    compra.getItensCompra().get(i).setCompra(compra);
                    daoItensCompra.incluir();
                }
            }
            for (int i = 0; i < compra.getControlePksItens().size(); i++) {
                boolean achou = false;
                for (int ii = 0; ii < compra.getItensCompra().size(); ii++) {
                    if (compra.getControlePksItens().get(i)
                            == compra.getItensCompra().get(ii).getId()) {
                        achou = true;
                    }
                }
                    if (achou == false) {
                        ItensCompra itensCompra = new ItensCompra();
                        itensCompra.setId(compra.getControlePksItens().get(i));
                        DaoItensCompra daoItensCompra = new DaoItensCompra(itensCompra);
                        daoItensCompra.excluir();
                    }
                }
            
            JOptionPane.showMessageDialog(null,"Alterada com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar a  Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean excluir() {
        try {
           
            DaoItensCompra.excluirItens(compra);
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
            ps.setInt(1, compra.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Excluída com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir a  Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
            ps.setInt(1, compra.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                compra.setData(Utilitarios.converteParaBanco(rs.getDate(2)));
                compra.setValortotal(rs.getBigDecimal(3));
                compra.setDesconto(rs.getBigDecimal(4));
                compra.setValorliquido(rs.getBigDecimal(5));
                compra.setStatus(rs.getString(6));
                compra.setCp(rs.getString(7).equals("S"));
                compra.getFornecedor().setId(rs.getInt(8));
                compra.getCondicaodepagamento().setId(rs.getInt(9));
                compra.getPedidodecompra().setId(rs.getInt(10));
                   
                


                DaoItensCompra.consultarItens(compra);
                List<Integer> controlePksItens = new ArrayList();
                for (int i = 0; i < compra.getItensCompra().size(); i++) {
                    controlePksItens.add((int) compra.getItensCompra().get(i).getId());
                }
                compra.setControlePksItens(controlePksItens);
                
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}