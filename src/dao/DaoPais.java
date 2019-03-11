
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Pais;



public class DaoPais {
    
    
    private Pais pais;
    
    private final String SQL_INCLUIR =
            "INSERT INTO PAIS VALUES (?, ?, ?)";
    private final String SQL_ALTERAR =
            "UPDATE PAIS SET NOME= ?, ATIVO = ? WHERE ID = ? ";
    private final String SQL_EXCLUIR =
            "DELETE FROM PAIS WHERE ID = ?";
    private final String SQL_CONSULTAR =
            "SELECT * FROM PAIS WHERE ID = ?";
    public static final String SQL_PESQUISAR =
            "SELECT ID, NOME , ATIVO  FROM PAIS";
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT ID, NOME , ATIVO  FROM PAIS  WHERE ATIVO = 'S'";
    public static final String SQL_COMBOBOX =
            "SELECT ID, NOME, ATIVO FROM PAIS WHERE ATIVO = 'S' ORDER BY NOME";
     
   
     
     public DaoPais(Pais pais) {
        this.pais = pais;
    }
    
   
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
     
           ps.setInt(1, pais.getId());
           ps.setString(2, pais.getNome());
           ps.setString(3, (pais.isAtivo() ? "S" : "N"));
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return  true;
          
        } catch (Exception e) {
            if(e.getMessage().contains("TPAIS")) { //violation of PRIMARY or UNIQUE KEY constrain
               JOptionPane.showMessageDialog(null,"País já Cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
                
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o País.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
        }
            
} 
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setString(1, pais.getNome());
           ps.setString(2, (pais.isAtivo() ? "S" : "N"));
           ps.setInt(3, pais.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("TPAIS")) {
               
               JOptionPane.showMessageDialog(null,"País já cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
              
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o País.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
        }
    
  } 
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, pais.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"País está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o País.", "Erro", JOptionPane.ERROR_MESSAGE);
            
            }
            return false;
            }
      }
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, pais.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               pais.setNome(rs.getString(2));
               pais.setAtivo(rs.getString(3).equals("S"));
           }
          return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o País.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
       
        

}

