
package dao;

import banco.Conexao;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.Lote;
import pojo.MateriaPrima;


import util.Utilitarios;


public class DaoLote {

  private Lote  lote;
  
   
    
    private final String SQL_INCLUIR =
            "INSERT INTO LOTE VALUES (?, ?, ?, ?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE LOTE SET DATAVALIDADE = ?, QUANTIDADE = ?, LOTE = ?, ATIVO = ?, IDMATERIAPRIMA = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM LOTE WHERE ID = ?";

    private final String SQL_CONSULTAR =
            "SELECT * FROM LOTE WHERE ID = ?";
    public static final String SQL_PESQUISAR =
            "SELECT LOTE.ID, LOTE.DATAVALIDADE AS DATA, LOTE.QUANTIDADE , LOTE.LOTE, LOTE.ATIVO, MATERIAPRIMA.NOME AS LOTE_MATERIAPRIMA FROM LOTE,MATERIAPRIMA WHERE MATERIAPRIMA.ID = LOTE.IDMATERIAPRIMA";
  
    public static final String SQL_CONSULTARITENS =
            "SELECT * FROM LOTE WHERE IDMATERIAPRIMA = ? ORDER BY DATAVALIDADE ";
    
    
    public DaoLote(Lote lote) {
        this.lote = lote;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
           lote.setId(Conexao.getGenerator("GEN_LOTE"));
           ps.setInt(1, lote.getId());
           ps.setDate(2, Utilitarios.converteParaBanco(lote.getDatavalidade()));
           ps.setInt(3, lote.getQuantidade());
           ps.setString(4, lote.getLote());
           ps.setString(5, (lote.isAtivo() ? "S" : "N"));
           ps.setInt(6, lote.getMateriaprima().getId());
           ps.executeUpdate();
           
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
          
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Lote.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
          
           
           ps.setDate(1, Utilitarios.converteParaBanco(lote.getDatavalidade()));
           ps.setInt(2, lote.getQuantidade());
           ps.setString(3, lote.getLote());
           ps.setString(4, (lote.isAtivo() ? "S" : "N"));
           ps.setInt(5, lote.getMateriaprima().getId());
           ps.setInt(6, lote.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Lote.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, lote.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Lote.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
      
 
      
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, lote.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               lote.setDatavalidade(Utilitarios.converteDoBanco(rs.getDate(2)));
               lote.setQuantidade(rs.getInt(3));
               lote.setLote(rs.getString(4));
               lote.setAtivo(rs.getString(5).equals("S"));
               lote.getMateriaprima().setId(rs.getInt(6));
               DaoMateriaPrima materiaPrima = new DaoMateriaPrima(lote.getMateriaprima());
               materiaPrima.consultar();
               
               
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Lote.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
       
        public static void consultarItens(MateriaPrima lote) {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTARITENS);
            ps.setInt(1, lote.getId());
            ResultSet rs = ps.executeQuery();
            List<Lote> itens = new ArrayList();
             Lote itenslote;
            while (rs.next()) {
                
                itenslote = new Lote();
                itenslote.setId(rs.getInt(1));
                itenslote.setDatavalidade(rs.getDate(2));
                itenslote.setQuantidade(rs.getInt(3));
                itenslote.setLote(rs.getString(4));
                itenslote.setAtivo(rs.getString(5).equals("S"));
                itenslote.getMateriaprima().setId(rs.getInt(6));
                
                itens.add(itenslote);
            }
             lote.setLote(itens);
           
           
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar os itens do lote.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    } 

    
       
       
      
}
