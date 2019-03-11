package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.ItensCompra;
import pojo.Compra;
import pojo.ContaPagar;
import pojo.ParcelaContaPagar;




import util.Utilitarios;

public class DaoContaPagar {

    private ContaPagar contaPagar;
    
  
    private final String SQL_INCLUIR
            = "INSERT INTO CONTAPAGAR VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private final String SQL_ALTERAR
            = "UPDATE CONTAPAGAR SET DATA = ?, VALORTOTAL = ?, DESCONTO = ?, VALORLIQUIDO = ?, VALORPENDENTE = ?,"
            + "DESCRICAO = ?, QUITADA = ?, IDCOMPRA = ?, IDFORNECEDOR = ?, IDCONDICAODEPAGAMENTO = ? WHERE ID = ?";
    private final String SQL_EXCLUIR
            = "DELETE FROM CONTAPAGAR WHERE ID = ?";
    private final String SQL_CONSULTAR
            = "SELECT * FROM CONTAPAGAR WHERE ID = ?";
    public static final String SQL_PESQUISAR
            = "SELECT CONTAPAGAR.ID, CONTAPAGAR.DATA, CONTAPAGAR.DESCRICAO AS CP_DESCRICAO, CONTAPAGAR.VALORLIQUIDO , CONTAPAGAR.QUITADA,FORNECEDOR.RAZAOSOCIAL AS CP_FORNECEDOR,CONDICAODEPAGAMENTO.DESCRICAO  AS CP_CONDICAODEPAGAMENTO, COMPRA.ID AS COMPRA, COMPRA.CP AS CP FROM CONTAPAGAR"
             + " LEFT JOIN  COMPRA ON COMPRA.ID = CONTAPAGAR.IDCOMPRA" 
             + " LEFT JOIN FORNECEDOR  ON FORNECEDOR.ID = CONTAPAGAR.IDFORNECEDOR" 
             + " INNER JOIN CONDICAODEPAGAMENTO ON CONDICAODEPAGAMENTO.ID = CONTAPAGAR.IDCONDICAODEPAGAMENTO"; 
           

    public DaoContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
       }

    public boolean incluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
            contaPagar.setId(Conexao.getGenerator("GEN_CONTAPAGAR"));
            ps.setInt(1, contaPagar.getId());
            ps.setDate(2, Utilitarios.converteParaBanco(contaPagar.getData()));
            ps.setBigDecimal(3, contaPagar.getValortotal());
            ps.setBigDecimal(4, contaPagar.getDesconto());
            ps.setBigDecimal(5, contaPagar.getValorliquido());
            ps.setBigDecimal(6, contaPagar.getValorpendente());
            ps.setString(7, (contaPagar.getDescricao()));
            ps.setString(8, (contaPagar.isQuitada()? "S" : "N"));
        
           if (contaPagar.getCompra().getId() == 0) {
                ps.setNull(9, java.sql.Types.INTEGER);
           } else {
                ps.setInt(9, contaPagar.getCompra().getId());
            } if (contaPagar.getFornecedor().getId() == 0) {
                 ps.setNull(10, java.sql.Types.INTEGER);
            } else {
                 ps.setInt(10, contaPagar.getFornecedor().getId());
            }
            
            ps.setInt(11, contaPagar.getCondicaodepagamento().getId());
            ps.executeUpdate();
                for (int i = 0; i < contaPagar.getParcelaContaPagar().size(); i++) {
                DaoParcelaContaPagar daoParcelaContaPagar = new DaoParcelaContaPagar(contaPagar.getParcelaContaPagar().get(i));
                contaPagar.getParcelaContaPagar().get(i).setContapagar(contaPagar);
                daoParcelaContaPagar.incluir();
            }
                
            JOptionPane.showMessageDialog(null,"Conta Pagar realizada com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir a Conta Pagar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
            ps.setDate(1, Utilitarios.converteParaBanco(contaPagar.getData()));
            ps.setBigDecimal(2, contaPagar.getValortotal());
            ps.setBigDecimal(3, contaPagar.getDesconto());
            ps.setBigDecimal(4, contaPagar.getValorliquido());
            ps.setBigDecimal(5, contaPagar.getValorpendente());
            ps.setString(6, contaPagar.getDescricao());
            ps.setString(7, (contaPagar.isQuitada() ?"S" : "N"));
          
            if (contaPagar.getCompra().getId() == 0) {
               ps.setNull(8, java.sql.Types.INTEGER);   
            } else {
                 ps.setInt(8, contaPagar.getCompra().getId());
            } if (contaPagar.getFornecedor().getId() == 0 ){
                 ps.setNull(9, java.sql.Types.INTEGER); 
            } else {
                ps.setInt(9, contaPagar.getFornecedor().getId());
            }
     
            ps.setInt(10, contaPagar.getCondicaodepagamento().getId());
            ps.setInt(11, contaPagar.getId());
            ps.executeUpdate();
             
            
            
            for (int i = 0; i < contaPagar.getParcelaContaPagar().size(); i++) {
                DaoParcelaContaPagar daoParcelaContaPagar = new DaoParcelaContaPagar(contaPagar.getParcelaContaPagar().get(i));
                if (contaPagar.getParcelaContaPagar().get(i).getId() > 0) {
                    daoParcelaContaPagar.alterar();
                } else {
                    contaPagar.getParcelaContaPagar().get(i).setContapagar(contaPagar);
                    daoParcelaContaPagar.incluir();
                }
            }
            for (int i = 0; i < contaPagar.getControlePksItens().size(); i++) {
                boolean achou = false;
                for (int ii = 0; ii < contaPagar.getParcelaContaPagar().size(); ii++) {
                    if (contaPagar.getControlePksItens().get(i)
                            == contaPagar.getParcelaContaPagar().get(ii).getId()) {
                        achou = true;
                    }
                }
                    if (achou == false) {
                        ParcelaContaPagar parcelaContaPagar = new ParcelaContaPagar();
                        parcelaContaPagar.setId(contaPagar.getControlePksItens().get(i));
                        DaoParcelaContaPagar daoParcelaContaPagar = new DaoParcelaContaPagar(parcelaContaPagar);
                        daoParcelaContaPagar.excluir();
                    }
                }
//            
            JOptionPane.showMessageDialog(null,"Alterada com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar a Conta Pagar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean excluir() {
        try {
            DaoParcelaContaPagar.excluirItens(contaPagar);
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
            ps.setInt(1, contaPagar.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Excluída com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir a  Conta Pagar .", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
            ps.setInt(1, contaPagar.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contaPagar.setData(Utilitarios.converteDoBanco(rs.getDate(2)));
                contaPagar.setValortotal(rs.getBigDecimal(3));
                contaPagar.setDesconto(rs.getBigDecimal(4));
                contaPagar.setValorliquido(rs.getBigDecimal(5));
                contaPagar.setValorpendente(rs.getBigDecimal(6));
                contaPagar.setDescricao(rs.getString(7));
                contaPagar.setQuitada(rs.getString(8).equals("S"));
                contaPagar.getCondicaodepagamento().setId(rs.getInt(11));
                contaPagar.getCompra().setId(rs.getInt(9));
                contaPagar.getFornecedor().setId(rs.getInt(10));
                
                DaoParcelaContaPagar.consultarItens(contaPagar);
                List<Integer> controlePksItens = new ArrayList();
                for (int i = 0; i < contaPagar.getParcelaContaPagar().size(); i++) {
                    controlePksItens.add((int) contaPagar.getParcelaContaPagar().get(i).getId());
                }
                contaPagar.setControlePksItens(controlePksItens);
                
            }
           
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a Conta Pagar.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}