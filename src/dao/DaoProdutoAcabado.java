
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.ProdutoAcabado;


public class DaoProdutoAcabado {
    
    
    private ProdutoAcabado produtoacabado;
    
    private final String SQL_INCLUIR =
            "INSERT INTO PRODUTOACABADO VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE PRODUTOACABADO SET DESCRICAO = ?, QUANTIDADE = ?, VALOR = ?,"
            + "ATIVO = ?, IDMODELO = ?, IDCOR = ?, IDTAMANHO = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM PRODUTOACABADO WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM PRODUTOACABADO WHERE ID = ?";
    
    public static final String SQL_PESQUISAR =
            "SELECT PRODUTOACABADO.ID, PRODUTOACABADO.DESCRICAO AS PRODUTOACABADO_DESCRICAO, PRODUTOACABADO.QUANTIDADE,"
            + "PRODUTOACABADO.VALOR, PRODUTOACABADO.ATIVO, MODELO.NOME AS PRODUTOACABADO_MODELO, COR.NOME AS PRODUTOACABADO_COR,"
            + "TAMANHO.TAMANHO AS PRODUTOACABADO_TAMANHO FROM PRODUTOACABADO"
            + " LEFT JOIN MODELO ON MODELO.ID = PRODUTOACABADO.IDMODELO "
            + " LEFT JOIN COR ON COR.ID = PRODUTOACABADO.IDCOR "
            + " LEFT JOIN TAMANHO ON TAMANHO.ID = PRODUTOACABADO.IDTAMANHO";
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT PRODUTOACABADO.ID, PRODUTOACABADO.DESCRICAO AS PRODUTOACABADO_DESCRICAO, PRODUTOACABADO.QUANTIDADE,"
            + "PRODUTOACABADO.VALOR, PRODUTOACABADO.ATIVO, MODELO.NOME AS PRODUTOACABADO_MODELO, COR.NOME AS PRODUTOACABADO_COR,"
            + "TAMANHO.TAMANHO AS PRODUTOACABADO_TAMANHO FROM PRODUTOACABADO"
            + " LEFT JOIN MODELO ON MODELO.ID = PRODUTOACABADO.IDMODELO "
            + " LEFT JOIN COR ON COR.ID = PRODUTOACABADO.IDCOR "
            + " LEFT JOIN TAMANHO ON TAMANHO.ID = PRODUTOACABADO.IDTAMANHO WHERE PRODUTOACABADO.ATIVO = 'S' ";
    public static final String SQL_COMBOBOX =
            "SELECT ID, DESCRICAO, QUANTIDADE, VALOR, ATIVO  FROM PRODUTOACABADO WHERE ATIVO = 'S' ORDER BY QUANTIDADE";
    
    public DaoProdutoAcabado(ProdutoAcabado produtoacabado) {
        this.produtoacabado = produtoacabado;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
      
           ps.setInt(1, produtoacabado.getId());
           ps.setString(2, produtoacabado.getDescricao());
           ps.setInt(3, produtoacabado.getQuantidade());
           ps.setBigDecimal(4, produtoacabado.getValor());
           ps.setString(5, (produtoacabado.isAtivo() ? "S" : "N"));
           if (produtoacabado.getModelo().getId() == 0){
              ps.setNull(6, java.sql.Types.INTEGER); 
           } else {
               ps.setInt(6, produtoacabado.getModelo().getId());
           } if (produtoacabado.getCor().getId() == 0) {
                 ps.setNull(7, java.sql.Types.INTEGER);
           } else {
               ps.setInt(7, produtoacabado.getCor().getId());  
           } if (produtoacabado.getTamanho().getId() == 0) {
                 ps.setNull(8, java.sql.Types.INTEGER);
           } else{
                 ps.setInt(8, produtoacabado.getTamanho().getId());
           }
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           ps.executeUpdate();
          return true;
           
         } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Produto Acabado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           
           ps.setString(1, produtoacabado.getDescricao());
           ps.setInt(2, produtoacabado.getQuantidade());
           ps.setBigDecimal(3, produtoacabado.getValor());
           ps.setString(4, (produtoacabado.isAtivo() ? "S" : "N"));
           
           if (produtoacabado.getModelo().getId() == 0) {
               ps.setNull(5, java.sql.Types.INTEGER);
           } else {
              ps.setInt(5, produtoacabado.getModelo().getId());
           } if (produtoacabado.getCor().getId() == 0) {
                    ps.setNull(6, java.sql.Types.INTEGER);
           } else {
                   ps.setInt(6, produtoacabado.getCor().getId()); 
           }  if(produtoacabado.getTamanho().getId() == 0) {
                   ps.setNull(7, java.sql.Types.INTEGER);
           } else {
                   ps.setInt(7, produtoacabado.getTamanho().getId());
           }
                    ps.setInt(8, produtoacabado.getId());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Alterado com Sucesso.", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
                    return true;
         
           } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Produto Acabado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, produtoacabado.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
            if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Produto Acabado está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Produto Acabado.", "Erro", JOptionPane.ERROR_MESSAGE);
            
            } 
            return false;
        }
      } 
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, produtoacabado.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               produtoacabado.setDescricao(rs.getString(2));
               produtoacabado.setQuantidade(rs.getInt(3));
               produtoacabado.setValor(rs.getBigDecimal(4));
               produtoacabado.setAtivo(rs.getString(5).equals("S"));
               produtoacabado.getModelo().setId(rs.getInt(6));
               produtoacabado.getCor().setId(rs.getInt(7));
               produtoacabado.getTamanho().setId(rs.getInt(8));

             }
            return true;  
        
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Produto Acabado.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           
        }
        return false;
    }
}
