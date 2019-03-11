
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.Modelo;
import util.Utilitarios;


public class DaoModelo {
    
    
    private Modelo  modelo;
    
    private final String SQL_INCLUIR =
            "INSERT INTO MODELO VALUES (?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE MODELO SET NOME = ?, ATIVO = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM MODELO WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM MODELO WHERE ID = ?";
    
    public static final String SQL_PESQUISAR =
            "SELECT ID, NOME, ATIVO  FROM MODELO";
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT ID, NOME, ATIVO  FROM MODELO WHERE ATIVO = 'S' ";
    public static final String SQL_COMBOBOX =
            "SELECT ID, NOME FROM MODELO WHERE ATIVO = 'S' ORDER BY NOME";
    
    public DaoModelo(Modelo modelo) {
        this.modelo = modelo;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
      
           ps.setInt(1, modelo.getId());
           ps.setString(2, modelo.getNome());
           ps.setString(3, (modelo.isAtivo() ? "S" : "N"));
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("TMODELO")) { //violation of PRIMARY or UNIQUE KEY constrain
               JOptionPane.showMessageDialog(null,"Modelo já Cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Modelo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setString(1, modelo.getNome());
           ps.setString(2, (modelo.isAtivo() ? "S" : "N"));
           ps.setInt(3, modelo.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("TMODELO")) { //violation of PRIMARY or UNIQUE KEY constrain
               JOptionPane.showMessageDialog(null,"Modelo já Cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Modelo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, modelo.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Modelo está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Modelo.", "Erro", JOptionPane.ERROR_MESSAGE);
            
            }
            return false;
            }
      }
      
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, modelo.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               modelo.setNome(rs.getString(2));
               modelo.setAtivo(rs.getString(3).equals("S"));
               
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Modelo", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
}
