package dao;

import banco.Conexao;
import static com.sun.glass.ui.Cursor.setVisible;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.Producao;
import pojo.ItensProducao;
import tela.TelaCadastroProducao;

public class DaoItensProducao {
    private ItensProducao itensProducao;
    private final String SQL_INCLUIR =
            "INSERT INTO ITENSPRODUCAO VALUES (?, ?, ?, ?)";
    private final String SQL_ALTERAR =
            "UPDATE ITENSPRODUCAO SET QUANTIDADE = ?, IDPRODUCAO = ?,"
            + "IDMATERIAPRIMA = ?  WHERE ID = ?";
    private final String SQL_EXCLUIR =
            "DELETE FROM ITENSPRODUCAO WHERE ID = ?";
    private static final String SQL_EXCLUIR_ITENS =
            "DELETE FROM ITENSPRODUCAO WHERE IDPRODUCAO = ?";
    private final String SQL_CONSULTAR =
            "SELECT * FROM ITENSPRODUCAO WHERE ID = ?";
    private static final String SQL_CONSULTAR_ITENS =
            "SELECT * FROM ITENSPRODUCAO WHERE IDPRODUCAO = ?";
    
    public DaoItensProducao(ItensProducao itensProducao) {
        this.itensProducao = itensProducao;
    }
    
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
      
           ps.setInt(1, itensProducao.getId());
           ps.setInt(2, itensProducao.getQuantidade());
           ps.setInt(3, itensProducao.getProducao().getId());  
           ps.setInt(4, itensProducao.getMateriaprima().getId()); 
           ps.executeUpdate();
           return true;
           
         } catch (Exception e) {
             
            if(e.getMessage().contains("ATT_PRODUCAO")) { 
               
                JOptionPane.showMessageDialog(null,"Estoque Insuficiente. Quantidade Disponível : " + TelaCadastroProducao.QtdEstoque.getValor() , "Erro", JOptionPane.ERROR_MESSAGE);
                setVisible(false);
                
               
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Item da Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
          
        }
        
    }
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setInt(1, itensProducao.getQuantidade());
           ps.setInt(2, itensProducao.getProducao().getId()); 
           ps.setInt(3, itensProducao.getMateriaprima().getId());  
           ps.setInt(4, itensProducao.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Item da Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, itensProducao.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Item da Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }      
    
    public static boolean excluirItens(Producao producao) {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR_ITENS);
           ps.setInt(1, producao.getId());
           ps.executeUpdate();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir os itens da Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } 
    
    public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, itensProducao.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               itensProducao.setQuantidade(rs.getInt(2));
               itensProducao.getProducao().setId(rs.getInt(3));
               itensProducao.getMateriaprima().setId(rs.getInt(4));
               DaoMateriaPrima materiaprima = new DaoMateriaPrima(itensProducao.getMateriaprima());
               materiaprima.consultar();
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Item da Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
    
    public static void consultarItens(Producao producao) {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR_ITENS);
            ps.setInt(1, producao.getId());
            ResultSet rs = ps.executeQuery();
            List<ItensProducao> itens = new ArrayList();
            ItensProducao itensProducao;
            while (rs.next()) {
                itensProducao = new ItensProducao();
                itensProducao.setId(rs.getInt(1));
                itensProducao.setQuantidade(rs.getInt(2));
                itensProducao.getProducao().setId(rs.getInt(3));
                itensProducao.getMateriaprima().setId(rs.getInt(4));
                DaoMateriaPrima daoMateriaPrima = new DaoMateriaPrima(itensProducao.getMateriaprima());
                daoMateriaPrima.consultar();
                itens.add(itensProducao);
            }
            producao.setItensProducao(itens);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar os itens da Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}