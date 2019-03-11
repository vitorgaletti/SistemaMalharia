
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Estado;



public class DaoEstado {
    
    
    private Estado estado;
     
    private final String SQL_INCLUIR =
            "INSERT INTO ESTADO VALUES (?, ?, ?, ?, ?)";
    private final String SQL_ALTERAR =
            "UPDATE ESTADO SET NOME= ?, SIGLA= ?, ATIVO = ?, IDPAIS = ? WHERE ID = ? ";
    private final String SQL_EXCLUIR =
            "DELETE FROM ESTADO WHERE ID = ?";
    private final String SQL_CONSULTAR =
            "SELECT * FROM ESTADO WHERE ID = ?";
    public static final String SQL_PESQUISAR =
            "SELECT ESTADO.ID, ESTADO.NOME AS ESTADO_NOME, ESTADO.SIGLA, ESTADO.ATIVO, PAIS.NOME AS PAIS_NOME FROM PAIS,ESTADO WHERE PAIS.ID = ESTADO.IDPAIS ";
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT ESTADO.ID, ESTADO.NOME AS ESTADO_NOME, ESTADO.SIGLA, ESTADO.ATIVO, PAIS.NOME AS PAIS_NOME FROM PAIS,ESTADO WHERE PAIS.ID = ESTADO.IDPAIS AND ESTADO.ATIVO = 'S' ";
    public static final String SQL_COMBOBOX =
            "SELECT ID, NOME || ' - ' || SIGLA AS NOME FROM ESTADO WHERE ATIVO = 'S' ORDER BY NOME";
    
   
    public DaoEstado(Estado estado) {
        this.estado = estado;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement  ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
     
           ps.setInt(1, estado.getId());
           ps.setString(2, estado.getNome());
           ps.setString(3, estado.getSigla());
           ps.setString(4, (estado.isAtivo() ? "S" : "N"));
           ps.setInt(5, estado.getPais().getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
           } catch (Exception e) {
            if(e.getMessage().contains("TESTADO")) {
              JOptionPane.showMessageDialog(null,"Estado já cadastrado. ", "Erro", JOptionPane.WARNING_MESSAGE);
               } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Estado.", "Erro", JOptionPane.ERROR_MESSAGE);
                
            }
            return false;
        }
    }

    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setString(1, estado.getNome());
           ps.setString(2, estado.getSigla());
           ps.setString(3, (estado.isAtivo() ? "S" : "N"));
           ps.setInt(4, estado.getPais().getId());
           ps.setInt(5, estado.getId());
           ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("TESTADO")) {
               
               JOptionPane.showMessageDialog(null,"Estado já cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Estado.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, estado.getId());
           ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
             if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Estado está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
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
           ps.setInt(1, estado.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               estado.setNome(rs.getString(2));
               estado.setSigla(rs.getString(3));
               estado.setAtivo(rs.getString(4).equals("S"));
               estado.getPais().setId(rs.getInt(5));
               
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Estado.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
}
