
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.Cor;
import util.Utilitarios;


public class DaoCor {
    
    private Cor  cor;
    
    private final String SQL_INCLUIR =
            "INSERT INTO COR VALUES (?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE COR SET NOME = ?, ATIVO = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM COR WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM COR WHERE ID = ?";
    
    public static final String SQL_PESQUISAR =
            "SELECT ID, NOME, ATIVO  FROM COR";
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT ID, NOME, ATIVO  FROM COR WHERE ATIVO = 'S' ";
    public static final String SQL_COMBOBOX =
            "SELECT ID, NOME FROM COR WHERE ATIVO = 'S' ORDER BY NOME";
    
    public DaoCor(Cor cor) {
        this.cor = cor;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
        
           ps.setInt(1, cor.getId());
           ps.setString(2, cor.getNome());
           ps.setString(3, (cor.isAtivo() ? "S" : "N"));
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
              if(e.getMessage().contains("TCOR")) { 
               JOptionPane.showMessageDialog(null,"Cor já Cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir a Cor.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setString(1, cor.getNome());
           ps.setString(2, (cor.isAtivo() ? "S" : "N"));
           ps.setInt(3, cor.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
           if(e.getMessage().contains("TCOR")) { 
               JOptionPane.showMessageDialog(null,"Cor já Cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar a Cor.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, cor.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
            } catch (Exception e) {
               if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Cor está sendo utilizada em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir a Cor.", "Erro", JOptionPane.ERROR_MESSAGE);
            
            }
             return false;
             }
        }   
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, cor.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               cor.setNome(rs.getString(2));
               cor.setAtivo(rs.getString(3).equals("S"));
               
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar a Cor", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
}
