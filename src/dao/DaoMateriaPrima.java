
package dao;

import banco.Conexao;
import static dao.DaoLote.SQL_CONSULTARITENS;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.ItensProducao;
import pojo.Lote;
import pojo.MateriaPrima;
import pojo.Producao;
import util.Utilitarios;


public class DaoMateriaPrima {
    
    
    private MateriaPrima materiaprima;
    
    private final String SQL_INCLUIR =
            "INSERT INTO MATERIAPRIMA VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE MATERIAPRIMA SET NOME= ?, QUANTIDADE = ?, VALORUNITARIO = ?,"
            + "VALORTOTAL = ?, ELOTE = ?, ATIVO = ? WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM MATERIAPRIMA WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM MATERIAPRIMA WHERE ID = ?";
    
    public static final String SQL_PESQUISAR =
            "SELECT ID, NOME AS MATERIAPRIMA_NOME, QUANTIDADE, VALORUNITARIO, VALORTOTAL, ELOTE AS LOTE, ATIVO FROM  MATERIAPRIMA";
    
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT ID, NOME AS MATERIAPRIMA_NOME, QUANTIDADE, VALORUNITARIO, VALORTOTAL, ELOTE, ATIVO FROM  MATERIAPRIMA WHERE ATIVO = 'S' ";
     
    public static final String SQL_COMBOBOX =
            "SELECT ID, NOME, QUANTIDADE, VALORUNITARIO, VALORTOTAL, ELOTE FROM MATERIAPRIMA WHERE ATIVO = 'S' ORDER BY NOME";
    public static final String SQL_COMBOBOXLOTE =
            "SELECT ID, NOME, QUANTIDADE, ATIVO FROM MATERIAPRIMA WHERE ELOTE = 'S' ORDER BY NOME ";
  
           
     
    
    public DaoMateriaPrima(MateriaPrima materiaprima) {
        this.materiaprima = materiaprima;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
          
           ps.setInt(1, materiaprima.getId());
           ps.setString(2, materiaprima.getNome());
           ps.setInt(3, materiaprima.getQuantidade());
           ps.setBigDecimal(4, materiaprima.getValorunitario());
           ps.setBigDecimal(5, materiaprima.getValortotal());
           ps.setString(6, (materiaprima.isElote() ? "S" : "N"));
           ps.setString(7, (materiaprima.isAtivo() ? "S" : "N"));
           ps.executeUpdate();
           
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("TMATERIAPRIMA")) { //violation of PRIMARY or UNIQUE KEY constrain
               JOptionPane.showMessageDialog(null,"Matéria-Prima já Cadastrada.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir a Matéria-Prima.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
            
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setString(1, materiaprima.getNome());
           ps.setInt(2, materiaprima.getQuantidade());
           ps.setBigDecimal(3, materiaprima.getValorunitario());
           ps.setBigDecimal(4, materiaprima.getValortotal());
           ps.setString(5, (materiaprima.isElote() ? "S" : "N"));
           ps.setString(6, (materiaprima.isAtivo() ? "S" : "N"));
           ps.setInt(7, materiaprima.getId());
           ps.executeUpdate();
          
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("TMATERIAPRIMA")) { //violation of PRIMARY or UNIQUE KEY constrain
               JOptionPane.showMessageDialog(null,"Matéria-Prima já Cadastrada.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar a Matéria-Prima.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, materiaprima.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
             if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Matéria-Prima está sendo utilizada em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir a Matéria-Prima.", "Erro", JOptionPane.ERROR_MESSAGE);
            
            }
            return false;
            }
        }
      
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, materiaprima.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               materiaprima.setNome(rs.getString(2));
               materiaprima.setQuantidade(rs.getInt(3));
               materiaprima.setValorunitario(rs.getBigDecimal(4));
               materiaprima.setValortotal(rs.getBigDecimal(5));
               materiaprima.setElote(rs.getString(6).equals("S"));
               materiaprima.setAtivo(rs.getString(7).equals("S"));
               
               DaoLote.consultarItens(materiaprima);
                List<Integer> controlePksItens = new ArrayList();
                for (int i = 0; i < materiaprima.getLote().size(); i++) {
                    controlePksItens.add((int) materiaprima.getLote().get(i).getId());
                }
              materiaprima.setControlePksItens(controlePksItens);
           }
          
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar a Matéria-Prima.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
       
  
     
       
       
       
       
}
