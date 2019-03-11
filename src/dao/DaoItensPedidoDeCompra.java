package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.Compra;
import pojo.ItensCompra;
import pojo.ItensPedidoDeCompra;
import pojo.PedidoDeCompra;

public class DaoItensPedidoDeCompra {

    
    private ItensPedidoDeCompra itensPedidodecompra;
    private final String SQL_INCLUIR =
            "INSERT INTO ITENSPEDIDODECOMPRA VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_ALTERAR =
            "UPDATE ITENSPEDIDODECOMPRA SET QUANTIDADE = ?, VALORUNITARIO = ?, DESCONTO = ?,"
            + " VALORTOTAL = ?, IDPEDIDODECOMPRA = ?, IDMATERIAPRIMA = ? WHERE ID = ?";
    private final String SQL_EXCLUIR =
            "DELETE FROM ITENSPEDIDODECOMPRA WHERE ID = ?";
    private static final String SQL_EXCLUIR_ITENS =
            "DELETE FROM ITENSPEDIDODECOMPRA WHERE IDPEDIDODECOMPRA = ?";
    private final String SQL_CONSULTAR =
            "SELECT * FROM ITENSPEDIDODECOMPRA WHERE ID = ?";
    private static final String SQL_CONSULTAR_ITENS =
            "SELECT * FROM ITENSPEDIDODECOMPRA WHERE IDPEDIDODECOMPRA = ?";
    
    public DaoItensPedidoDeCompra(ItensPedidoDeCompra itensPedidodecompra) {
        this.itensPedidodecompra = itensPedidodecompra;
    }
    
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
 
           ps.setInt(1, itensPedidodecompra.getId());
           ps.setInt(2, itensPedidodecompra.getQuantidade());
           ps.setBigDecimal(3, itensPedidodecompra.getValorunitario());
           ps.setBigDecimal(4, itensPedidodecompra.getDesconto());
           ps.setBigDecimal(5, itensPedidodecompra.getValortotal());
           ps.setInt(6, itensPedidodecompra.getPedidodecompra().getId());           
           ps.setInt(7, itensPedidodecompra.getMateriaprima().getId());           
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Item do Pedido de Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setInt(1, itensPedidodecompra.getQuantidade());
           ps.setBigDecimal(2, itensPedidodecompra.getValorunitario());
           ps.setBigDecimal(3, itensPedidodecompra.getDesconto());
           ps.setBigDecimal(4, itensPedidodecompra.getValortotal());
           ps.setInt(5, itensPedidodecompra.getPedidodecompra().getId());           
           ps.setInt(6, itensPedidodecompra.getMateriaprima().getId()); 
           ps.setInt(7, itensPedidodecompra.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Item do Pedido de Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, itensPedidodecompra.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Item do Pedido de Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }      
    
    public static boolean excluirItens(PedidoDeCompra pedidodecompra) {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR_ITENS);
           ps.setInt(1, pedidodecompra.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir os itens do pedido de compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } 
    
    public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, itensPedidodecompra.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               itensPedidodecompra.setQuantidade(rs.getInt(2));
               itensPedidodecompra.setValorunitario(rs.getBigDecimal(3));
               itensPedidodecompra.setDesconto(rs.getBigDecimal(4));
               itensPedidodecompra.setValortotal(rs.getBigDecimal(5));
               itensPedidodecompra.getPedidodecompra().setId(rs.getInt(6));
               itensPedidodecompra.getMateriaprima().setId(rs.getInt(7));
               DaoMateriaPrima materiaPrima = new DaoMateriaPrima(itensPedidodecompra.getMateriaprima());
               materiaPrima.consultar();
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Item do Pedido de Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
    
    public static void consultarItens(PedidoDeCompra pedidodecompra) {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR_ITENS);
            ps.setInt(1, pedidodecompra.getId());
            ResultSet rs = ps.executeQuery();
            List<ItensPedidoDeCompra> itens = new ArrayList();
            ItensPedidoDeCompra itensPedidoDeCompra;
            while (rs.next()) {
                itensPedidoDeCompra = new ItensPedidoDeCompra();
                itensPedidoDeCompra.setId(rs.getInt(1));
                itensPedidoDeCompra.setQuantidade(rs.getInt(2));
                itensPedidoDeCompra.setValorunitario(rs.getBigDecimal(3));
                itensPedidoDeCompra.setDesconto(rs.getBigDecimal(4));
                itensPedidoDeCompra.setValortotal(rs.getBigDecimal(5));
                itensPedidoDeCompra.getPedidodecompra().setId(rs.getInt(6));
                itensPedidoDeCompra.getMateriaprima().setId(rs.getInt(7));
                DaoMateriaPrima daoMateriaPrima = new DaoMateriaPrima(itensPedidoDeCompra.getMateriaprima());
                daoMateriaPrima.consultar();
                itens.add(itensPedidoDeCompra);
            }
            pedidodecompra.setItensPedidoDeCompra(itens);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar os itens do pedido de compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
 
    
  
}