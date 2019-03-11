
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Cidade;



public class DaoCidade {
    
    
    private Cidade cidade;
    
    private final String SQL_INCLUIR =
            "INSERT INTO CIDADE VALUES (?, ?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE CIDADE SET NOME= ?, ATIVO = ?, IDESTADO = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM CIDADE WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM CIDADE WHERE ID = ?";
    public static final String SQL_PESQUISAR =
            "SELECT CIDADE.ID, CIDADE.NOME AS CIDADE_NOME, CIDADE.ATIVO, ESTADO.NOME AS ESTADO_NOME, ESTADO.SIGLA FROM CIDADE,ESTADO WHERE ESTADO.ID = CIDADE.IDESTADO";
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT CIDADE.ID, CIDADE.NOME AS CIDADE_NOME, CIDADE.ATIVO, ESTADO.NOME AS ESTADO_NOME, ESTADO.SIGLA FROM CIDADE,ESTADO WHERE ESTADO.ID = CIDADE.IDESTADO AND CIDADE.ATIVO = 'S'";
    public static final String SQL_COMBOBOX =
            "SELECT CIDADE.ID, CIDADE.NOME || ' - ' || ESTADO.SIGLA AS CIDADE, CIDADE.ATIVO  FROM CIDADE, ESTADO WHERE ESTADO.ID = CIDADE.IDESTADO AND CIDADE.ATIVO = 'S' ORDER BY CIDADE.NOME";
    
    public DaoCidade(Cidade cidade) {
        this.cidade = cidade;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
    
           ps.setInt(1, cidade.getId());
           ps.setString(2, cidade.getNome());
           ps.setString(3, (cidade.isAtivo() ? "S" : "N"));
           ps.setInt(4, cidade.getEstado().getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (SQLException e) {
            if(e.getMessage().contains("TCIDADE")) {
               
               JOptionPane.showMessageDialog(null,"Cidade já cadastrada.", "Erro", JOptionPane.WARNING_MESSAGE);
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
           ps.setString(1, cidade.getNome());
           ps.setString(2, (cidade.isAtivo() ? "S" : "N"));
           ps.setInt(3, cidade.getEstado().getId());
           ps.setInt(4, cidade.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("TCIDADE")) {
               
               JOptionPane.showMessageDialog(null,"Cidade já cadastrada.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar a Cidade.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, cidade.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
             if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Cidade está sendo utilizada em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir a Cidade.", "Erro", JOptionPane.ERROR_MESSAGE);
            
        }
       return false;
    }
 }
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, cidade.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               cidade.setNome(rs.getString(2));
               cidade.setAtivo(rs.getString(3).equals("S"));
               cidade.getEstado().setId(rs.getInt(4));
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar a Cidade.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
}
