
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.Tamanho;
import util.Utilitarios;


public class DaoTamanho {
    
    
    private Tamanho  tamanho;
    
    private final String SQL_INCLUIR =
            "INSERT INTO TAMANHO VALUES (?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE TAMANHO SET TAMANHO = ?, ATIVO = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM TAMANHO WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM TAMANHO WHERE ID = ?";
    
    public static final String SQL_PESQUISAR =
            "SELECT ID, TAMANHO, ATIVO  FROM TAMANHO";
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT ID, TAMANHO, ATIVO  FROM TAMANHO WHERE ATIVO = 'S' ";
    public static final String SQL_COMBOBOX =
            "SELECT ID, TAMANHO FROM TAMANHO WHERE ATIVO = 'S' ORDER BY TAMANHO";
    
    
    public DaoTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
       
           ps.setInt(1, tamanho.getId());
           ps.setString(2, tamanho.getTamanho());
           ps.setString(3, (tamanho.isAtivo() ? "S" : "N"));
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Tamanho realizada com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
           if(e.getMessage().contains("TTAMANHO")) { //violation of PRIMARY or UNIQUE KEY constrain
               JOptionPane.showMessageDialog(null,"Tamanho já Cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Tamanho.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setString(1, tamanho.getTamanho());
           ps.setString(2, (tamanho.isAtivo() ? "S" : "N"));
           ps.setInt(3, tamanho.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("TTAMANHO")) { //violation of PRIMARY or UNIQUE KEY constrain
               JOptionPane.showMessageDialog(null,"Tamanho já Cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Tamanho.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, tamanho.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Tamanho está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Tamanho.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
      
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, tamanho.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               tamanho.setTamanho(rs.getString(2));
               tamanho.setAtivo(rs.getString(3).equals("S"));
               
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Tamanho", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
}
