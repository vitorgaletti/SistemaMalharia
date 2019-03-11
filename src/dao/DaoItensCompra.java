package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.ItensCompra;
import pojo.Compra;
import pojo.Lote;

public class DaoItensCompra {
    private ItensCompra itensCompra;
    private Lote lote;
    private final String SQL_INCLUIR =
            "INSERT INTO ITENSCOMPRA VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_ALTERAR =
            "UPDATE ITENSCOMPRA SET QUANTIDADE = ?, VALORUNITARIO = ?, DESCONTO = ?,"
            + " VALORTOTAL = ?, IDCOMPRA = ?, IDMATERIAPRIMA = ? WHERE ID = ?";
    private final String SQL_EXCLUIR =
            "DELETE FROM ITENSCOMPRA WHERE ID = ?";
    private static final String SQL_EXCLUIR_ITENS =
            "DELETE FROM ITENSCOMPRA WHERE IDCOMPRA = ?";
    private final String SQL_CONSULTAR =
            "SELECT * FROM ITENSCOMPRA WHERE ID = ?";
    private static final String SQL_CONSULTAR_ITENS =
            "SELECT * FROM ITENSCOMPRA WHERE IDCOMPRA = ?";
    
    public DaoItensCompra(ItensCompra itensCompra) {
        this.itensCompra = itensCompra;
    }
    
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
           itensCompra.setId(Conexao.getGenerator("GEN_ITENSCOMPRA"));
           ps.setInt(1, itensCompra.getId());
           ps.setInt(2, itensCompra.getQuantidade());
           ps.setBigDecimal(3, itensCompra.getValorunitario());
           ps.setBigDecimal(4, itensCompra.getDesconto());
           ps.setBigDecimal(5, itensCompra.getValortotal());
           ps.setInt(6, itensCompra.getCompra().getId());           
           ps.setInt(7, itensCompra.getMateriaprima().getId());           
           ps.executeUpdate();
           
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Item da Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setInt(1, itensCompra.getQuantidade());
           ps.setBigDecimal(2, itensCompra.getValorunitario());
           ps.setBigDecimal(3, itensCompra.getDesconto());
           ps.setBigDecimal(4, itensCompra.getValortotal());
           ps.setInt(5, itensCompra.getCompra().getId());           
           ps.setInt(6, itensCompra.getMateriaprima().getId()); 
           ps.setInt(7, itensCompra.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Item da Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, itensCompra.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Item da Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }      
    
    public static boolean excluirItens(Compra compra) {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR_ITENS);
           ps.setInt(1, compra.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir os itens da compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } 
    
    public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, itensCompra.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               itensCompra.setQuantidade(rs.getInt(2));
               itensCompra.setValorunitario(rs.getBigDecimal(3));
               itensCompra.setDesconto(rs.getBigDecimal(4));
               itensCompra.setValortotal(rs.getBigDecimal(5));
               itensCompra.getCompra().setId(rs.getInt(6));
               itensCompra.getMateriaprima().setId(rs.getInt(7));
               DaoMateriaPrima materiaPrima = new DaoMateriaPrima(itensCompra.getMateriaprima());
               materiaPrima.consultar();
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Item da Compra.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
    
    public static void consultarItens(Compra compra) {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR_ITENS);
            ps.setInt(1, compra.getId());
            ResultSet rs = ps.executeQuery();
            List<ItensCompra> itens = new ArrayList();
            ItensCompra itensCompra;
            while (rs.next()) {
                itensCompra = new ItensCompra();
                itensCompra.setId(rs.getInt(1));
                itensCompra.setQuantidade(rs.getInt(2));
                itensCompra.setValorunitario(rs.getBigDecimal(3));
                itensCompra.setDesconto(rs.getBigDecimal(4));
                itensCompra.setValortotal(rs.getBigDecimal(5));
                itensCompra.getCompra().setId(rs.getInt(6));
                itensCompra.getMateriaprima().setId(rs.getInt(7));
                DaoMateriaPrima daoMateriaPrima = new DaoMateriaPrima(itensCompra.getMateriaprima());
                daoMateriaPrima.consultar();
                itens.add(itensCompra);
            }
            compra.setItensCompra(itens);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar os itens da compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}