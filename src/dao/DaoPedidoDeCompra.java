package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.Compra;
import pojo.ItensPedidoDeCompra;
import pojo.PedidoDeCompra;
import tela.TelaCompra;
import util.Utilitarios;

public class DaoPedidoDeCompra {

    private PedidoDeCompra pedidodecompra;
    private final String SQL_INCLUIR
            = "INSERT INTO PEDIDODECOMPRA VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_ALTERAR
            = "UPDATE PEDIDODECOMPRA SET DATA = ?, VALORTOTAL = ?, DESCONTO = ?, VALORLIQUIDO = ?,"
            + "STATUS = ?, IDFORNECEDOR = ?, IDCONDICAODEPAGAMENTO = ? WHERE ID = ?";
    private final String SQL_EXCLUIR
            = "DELETE FROM PEDIDODECOMPRA WHERE ID = ?";
    private final String SQL_CONSULTAR
            = "SELECT * FROM PEDIDODECOMPRA WHERE ID = ?";
    public static final String SQL_PESQUISAR
            = "SELECT PEDIDODECOMPRA.ID, PEDIDODECOMPRA.DATA,"
           + "PEDIDODECOMPRA.VALORLIQUIDO,PEDIDODECOMPRA.STATUS,FORNECEDOR.RAZAOSOCIAL AS PDC_FORNECEDOR,"
           + "CONDICAODEPAGAMENTO.DESCRICAO AS PDC_CONDICAODEPAGAMENTO FROM PEDIDODECOMPRA, FORNECEDOR,CONDICAODEPAGAMENTO "
           + "WHERE  PEDIDODECOMPRA.IDFORNECEDOR = FORNECEDOR.ID AND PEDIDODECOMPRA.IDCONDICAODEPAGAMENTO = CONDICAODEPAGAMENTO.ID";
    
    public static final String SQL_PESQUISAR_ATIVOS
            = "SELECT PEDIDODECOMPRA.ID, PEDIDODECOMPRA.DATA,"
           + "PEDIDODECOMPRA.VALORLIQUIDO,PEDIDODECOMPRA.STATUS,FORNECEDOR.RAZAOSOCIAL AS PDC_FORNECEDOR,"
           + "CONDICAODEPAGAMENTO.DESCRICAO AS PDC_CONDICAODEPAGAMENTO FROM PEDIDODECOMPRA, FORNECEDOR,CONDICAODEPAGAMENTO "
           + "WHERE  PEDIDODECOMPRA.IDFORNECEDOR = FORNECEDOR.ID AND PEDIDODECOMPRA.IDCONDICAODEPAGAMENTO = CONDICAODEPAGAMENTO.ID AND PEDIDODECOMPRA.STATUS = 'PF' ";
      public static final String SQL_COMBOBOX 
       = "SELECT PD.ID,  F.RAZAOSOCIAL || ' - ' || PD.DATA AS DATA FROM PEDIDODECOMPRA PD, FORNECEDOR  F WHERE PD.IDFORNECEDOR = F.ID AND PD.STATUS = 'PF' ORDER BY PD.DATA";
     
         


    public DaoPedidoDeCompra(PedidoDeCompra pedidodecompra) {
        this.pedidodecompra = pedidodecompra;
    }

    public boolean incluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
            pedidodecompra.setId(Conexao.getGenerator("GEN_PEDIDODECOMPRA"));
            ps.setInt(1, pedidodecompra.getId());
            ps.setDate(2, Utilitarios.converteParaBanco(pedidodecompra.getData()));
            ps.setBigDecimal(3, pedidodecompra.getValortotal());
            ps.setBigDecimal(4, pedidodecompra.getDesconto());
            ps.setBigDecimal(5, pedidodecompra.getValorliquido());
            ps.setString(6, pedidodecompra.getStatus());
            ps.setInt(7, pedidodecompra.getFornecedor().getId());
            ps.setInt(8, pedidodecompra.getCondicaodepagamento().getId());
            ps.executeUpdate();
            for (int i = 0; i < pedidodecompra.getItensPedidoDeCompra().size(); i++) {
                DaoItensPedidoDeCompra daoItemCompra = new DaoItensPedidoDeCompra(pedidodecompra.getItensPedidoDeCompra().get(i));
                pedidodecompra.getItensPedidoDeCompra().get(i).setPedidodecompra(pedidodecompra);
                daoItemCompra.incluir();
            }
             JOptionPane.showMessageDialog(null,"Pedido de Compra realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
              
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Pedido de  Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           
            ps.setDate(1, Utilitarios.converteParaBanco(pedidodecompra.getData()));
            ps.setBigDecimal(2, pedidodecompra.getValortotal());
            ps.setBigDecimal(3, pedidodecompra.getDesconto());
            ps.setBigDecimal(4, pedidodecompra.getValorliquido());
            ps.setString(5, pedidodecompra.getStatus());
            ps.setInt(6, pedidodecompra.getFornecedor().getId());
            ps.setInt(7, pedidodecompra.getCondicaodepagamento().getId());
            ps.setInt(8, pedidodecompra.getId());
            ps.executeUpdate();
            for (int i = 0; i < pedidodecompra.getItensPedidoDeCompra().size(); i++) {
                DaoItensPedidoDeCompra daoItensPedidoDeCompra = new DaoItensPedidoDeCompra(pedidodecompra.getItensPedidoDeCompra().get(i));
                if (pedidodecompra.getItensPedidoDeCompra().get(i).getId() > 0) {
                    daoItensPedidoDeCompra.alterar();
                } else {
                    pedidodecompra.getItensPedidoDeCompra().get(i).setPedidodecompra(pedidodecompra);
                    daoItensPedidoDeCompra.incluir();
                }
            }
            for (int i = 0; i < pedidodecompra.getControlePksItens().size(); i++) {
                boolean achou = false;
                for (int ii = 0; ii < pedidodecompra.getItensPedidoDeCompra().size(); ii++) {
                    if (pedidodecompra.getControlePksItens().get(i)
                            == pedidodecompra.getItensPedidoDeCompra().get(ii).getId()) {
                        achou = true;
                    }
                }
                    if (achou == false) {
                        ItensPedidoDeCompra itensPedidoDeCompra = new ItensPedidoDeCompra();
                        itensPedidoDeCompra.setId(pedidodecompra.getControlePksItens().get(i));
                        DaoItensPedidoDeCompra daoItensPedidoDeCompra = new DaoItensPedidoDeCompra(itensPedidoDeCompra);
                        daoItensPedidoDeCompra.excluir();
                    }
                
            }
             JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Pedido de  Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean excluir() {
        try {
            DaoItensPedidoDeCompra.excluirItens(pedidodecompra);
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
            ps.setInt(1, pedidodecompra.getId());
            ps.executeUpdate();
           
            JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
            } catch (Exception e) {
             if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Pedido de Compra está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
                  e.printStackTrace();
                  JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Pedido de  Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
             }
           
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
            ps.setInt(1, pedidodecompra.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pedidodecompra.setData(Utilitarios.converteDoBanco(rs.getDate(2)));
                pedidodecompra.setValortotal(rs.getBigDecimal(3));
                pedidodecompra.setDesconto(rs.getBigDecimal(4));
                pedidodecompra.setValorliquido(rs.getBigDecimal(5));
                pedidodecompra.setStatus(rs.getString(6));
                pedidodecompra.getFornecedor().setId(rs.getInt(7));
                pedidodecompra.getCondicaodepagamento().setId(rs.getInt(8));
                
                DaoFornecedor fornecedor = new DaoFornecedor(pedidodecompra.getFornecedor());
                fornecedor.consultar();
                DaoItensPedidoDeCompra.consultarItens(pedidodecompra);
                List<Integer> controlePksItens = new ArrayList();
                for (int i = 0; i < pedidodecompra.getItensPedidoDeCompra().size(); i++) {
                    controlePksItens.add((int) pedidodecompra.getItensPedidoDeCompra().get(i).getId());
                }
                pedidodecompra.setControlePksItens(controlePksItens);
            }
            
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o pedido da compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    
     
}
