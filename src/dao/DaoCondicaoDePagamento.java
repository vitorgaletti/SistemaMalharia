
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.CondicaoDePagamento;


public class DaoCondicaoDePagamento {
    
    
    private CondicaoDePagamento condicaodepagamento;
    
    private final String SQL_INCLUIR =
            "INSERT INTO CONDICAODEPAGAMENTO VALUES (?, ?, ?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE CONDICAODEPAGAMENTO SET DESCRICAO = ?, CARENCIA = ?, NPARCELAS = ?,"
            + "PRAZOENTREPARCELAS = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM CONDICAODEPAGAMENTO WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM CONDICAODEPAGAMENTO WHERE ID = ?";
    
    public static final String SQL_PESQUISAR =
            "SELECT ID, DESCRICAO FROM CONDICAODEPAGAMENTO";
    public static final String SQL_COMBOBOX =
            "SELECT ID, DESCRICAO, CARENCIA, NPARCELAS, PRAZOENTREPARCELAS FROM CONDICAODEPAGAMENTO ORDER BY DESCRICAO";
    
    public DaoCondicaoDePagamento(CondicaoDePagamento condicaodepagamento) {
        this.condicaodepagamento = condicaodepagamento;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
       
           ps.setInt(1, condicaodepagamento.getId());
           ps.setString(2, condicaodepagamento.getDescricao());
           ps.setInt(3, condicaodepagamento.getCarencia());
           ps.setInt(4, condicaodepagamento.getNparcelas());
           ps.setInt(5, condicaodepagamento.getPrazoentreparcelas());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir a Condição de Pagamento", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
          
           ps.setString(1, condicaodepagamento.getDescricao());
           ps.setInt(2, condicaodepagamento.getCarencia());
           ps.setInt(3, condicaodepagamento.getNparcelas());
           ps.setInt(4, condicaodepagamento.getPrazoentreparcelas());
           ps.setInt(5, condicaodepagamento.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Condição de Pagamento está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar a Condição de Pagamento.", "Erro", JOptionPane.ERROR_MESSAGE);
            
           }
            return false;
       }
    }   
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, condicaodepagamento.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Condição de Pagamento está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir a Condição de Pagamento", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
        
    }
      
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, condicaodepagamento.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               condicaodepagamento.setDescricao(rs.getString(2));
               condicaodepagamento.setCarencia(rs.getInt(3));
               condicaodepagamento.setNparcelas(rs.getInt(4));
               condicaodepagamento.setPrazoentreparcelas(rs.getInt(5));
               
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar a Condição de Pagamento.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
}
