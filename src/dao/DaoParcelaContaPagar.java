package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.Compra;
import pojo.ContaPagar;
import pojo.ParcelaContaPagar;

import pojo.PedidoDeCompra;
import util.Utilitarios;

public class DaoParcelaContaPagar {

    
    private ParcelaContaPagar parcelaContaPagar;
    
    private final String SQL_INCLUIR =
            "INSERT INTO PARCELACONTAPAGAR VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQL_ALTERAR =
            "UPDATE PARCELACONTAPAGAR SET VENCIMENTO = ?, VALOR = ?, VALORPENDENTE = ?, QUITADA = ?,"
            + " IDCONTAPAGAR = ?  WHERE ID = ?";
    private final String SQL_EXCLUIR =
            "DELETE FROM PARCELACONTAPAGAR WHERE ID = ?";
    private static final String SQL_EXCLUIR_PARCELAS =
            "DELETE FROM PARCELACONTAPAGAR WHERE IDCONTAPAGAR = ?";
    private final String SQL_CONSULTAR =
            "SELECT * FROM PARCELACONTAPAGAR WHERE ID = ?";
    private static final String SQL_CONSULTAR_PARCELAS =
            "SELECT * FROM PARCELACONTAPAGAR WHERE IDCONTAPAGAR = ?";
    
    public DaoParcelaContaPagar(ParcelaContaPagar parcelaContaPagar) {
        this.parcelaContaPagar = parcelaContaPagar;
    }
    
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
 
           ps.setInt(1, parcelaContaPagar.getId());
           ps.setDate(2, Utilitarios.converteParaBanco(parcelaContaPagar.getVencimento()));
           ps.setBigDecimal(3, parcelaContaPagar.getValor());
           ps.setBigDecimal(4, parcelaContaPagar.getValorpendente());
           ps.setString(5, (parcelaContaPagar.isQuitada()? "S" : "N"));
           ps.setInt(6, parcelaContaPagar.getContapagar().getId());           
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir a Parcela de Conta Pagar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setDate(1, Utilitarios.converteParaBanco(parcelaContaPagar.getVencimento()));
           ps.setBigDecimal(2, parcelaContaPagar.getValor());
           ps.setBigDecimal(3, parcelaContaPagar.getValorpendente());
           ps.setString(4, (parcelaContaPagar.isQuitada() ?"S" : "N"));
           ps.setInt(5, parcelaContaPagar.getContapagar().getId()); 
           ps.setInt(6, parcelaContaPagar.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar a Parcela de Conta Pagar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, parcelaContaPagar.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir a Parcela de Conta Pagar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }      
    
    public static boolean excluirItens(ContaPagar contaPagar) {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR_PARCELAS);
           ps.setInt(1, contaPagar.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir as Parcelas da Conta Pagar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } 
    
    public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, parcelaContaPagar.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               parcelaContaPagar.setVencimento(Utilitarios.converteDoBanco(rs.getDate(2)));
               parcelaContaPagar.setValor(rs.getBigDecimal(3));
               parcelaContaPagar.setValorpendente(rs.getBigDecimal(4));
               parcelaContaPagar.setQuitada(rs.getString(5).equals("S"));
               parcelaContaPagar.getContapagar().setId(rs.getInt(6));

           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar a Parcela de Conta Pagar.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
    
    public static void consultarItens(ContaPagar contaPagar) {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR_PARCELAS);
            ps.setInt(1, contaPagar.getId());
            ResultSet rs = ps.executeQuery();
            List<ParcelaContaPagar> parcelas = new ArrayList();
            ParcelaContaPagar parcelaContaPagar;
            while (rs.next()) {
                parcelaContaPagar = new ParcelaContaPagar();
                parcelaContaPagar.setId(rs.getInt(1));
                parcelaContaPagar.setVencimento(rs.getDate(2));
                parcelaContaPagar.setValor(rs.getBigDecimal(3));
                parcelaContaPagar.setValorpendente(rs.getBigDecimal(4));
                parcelaContaPagar.setQuitada(rs.getString(5).equals("S"));
                parcelaContaPagar.getContapagar().setId(rs.getInt(6));
               
   
                parcelas.add(parcelaContaPagar);
            }
            contaPagar.setParcelaContaPagar(parcelas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar os itens do pedido de compra.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
 
    
  
}