
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.Fornecedor;



public class DaoFornecedor {
    
    
    private Fornecedor fornecedor;
    
    private final String SQL_INCLUIR =
            "INSERT INTO FORNECEDOR VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    private final String SQL_ALTERAR =
            "UPDATE FORNECEDOR SET NOME = ?, RAZAOSOCIAL = ?, NOMEFANTASIA = ?, CNPJ = ?,"
            +" FONE1 = ?, FONE2 = ?, ENDERECO = ?, NUMERO = ?, CEP = ?, BAIRRO = ?, COMPLEMENTO = ?, EMAIL = ?,"
            + " ATIVO = ?, IDCIDADE = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM FORNECEDOR WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM FORNECEDOR WHERE ID = ?";
    
    public static final String SQL_PESQUISAR =
            "SELECT FORNECEDOR.ID, FORNECEDOR.RAZAOSOCIAL AS FORNECEDOR_RAZAOSOCIAL,FORNECEDOR.CNPJ AS FORNECEDOR_CNPJ, "
           + "FORNECEDOR.ATIVO, CIDADE.NOME AS FORNECEDOR_CIDADE FROM FORNECEDOR,CIDADE WHERE CIDADE.ID = FORNECEDOR.IDCIDADE"; 
    public static final String SQL_PESQUISAR_ATIVOS =
            "SELECT FORNECEDOR.ID, FORNECEDOR.RAZAOSOCIAL AS FORNECEDOR_RAZAOSOCIAL,FORNECEDOR.CNPJ AS FORNECEDOR_CNPJ, "
           + "FORNECEDOR.ATIVO, CIDADE.NOME AS FORNECEDOR_CIDADE FROM FORNECEDOR,CIDADE WHERE CIDADE.ID = FORNECEDOR.IDCIDADE AND FORNECEDOR.ATIVO = 'S' "; 
    public static final String SQL_COMBOBOX =
             "SELECT ID, RAZAOSOCIAL  FROM FORNECEDOR WHERE ATIVO = 'S' ORDER BY RAZAOSOCIAL"; 
             
    
    public DaoFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
   
           ps.setInt(1, fornecedor.getId());
           ps.setString(2, fornecedor.getNome());
           ps.setString(3, fornecedor.getRazaosocial());
           ps.setString(4, fornecedor.getNomefantasia());
           ps.setString(5, fornecedor.getCnpj());
           ps.setString(6, fornecedor.getFone1());
           ps.setString(7, fornecedor.getFone2());
           ps.setString(8, fornecedor.getEndereco());
           ps.setString(9, fornecedor.getNumero());
           ps.setString(10, fornecedor.getCep());
           ps.setString(11, fornecedor.getBairro());
           ps.setString(12, fornecedor.getComplemento());
           ps.setString(13, fornecedor.getEmail());
           ps.setString(14, (fornecedor.isAtivo() ? "S" : "N"));
           ps.setInt(15, fornecedor.getCidade().getId());
           ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
           } catch (Exception e) {
            if(e.getMessage().contains("TFORNECEDOR")) {
           
               JOptionPane.showMessageDialog(null,"Fornecedor já Cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Fornecedor.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
        }
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setString(1, fornecedor.getNome());
           ps.setString(2, fornecedor.getRazaosocial());
           ps.setString(3, fornecedor.getNomefantasia());
           ps.setString(4, fornecedor.getCnpj());
           ps.setString(5, fornecedor.getFone1());
           ps.setString(6, fornecedor.getFone2());
           ps.setString(7, fornecedor.getEndereco());
           ps.setString(8, fornecedor.getNumero());
           ps.setString(9, fornecedor.getCep());
           ps.setString(10, fornecedor.getBairro());
           ps.setString(11, fornecedor.getComplemento());
           ps.setString(12, fornecedor.getEmail());
           ps.setString(13, (fornecedor.isAtivo() ? "S" : "N"));
           ps.setInt(14, fornecedor.getCidade().getId());
           ps.setInt(15, fornecedor.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
          } catch (Exception e) {
            if(e.getMessage().contains("TFORNECEDOR")) {
                JOptionPane.showMessageDialog(null,"Fornecedor já cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Fornecedor.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, fornecedor.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
            } catch (Exception e) {
             if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Fornecedor está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Fornecedor.", "Erro", JOptionPane.ERROR_MESSAGE);
            
            }
            return false;
            }
      }
      
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, fornecedor.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               fornecedor.setNome(rs.getString(2));
               fornecedor.setRazaosocial(rs.getString(3));
               fornecedor.setNomefantasia(rs.getString(4));
               fornecedor.setCnpj(rs.getString(5));
               fornecedor.setFone1(rs.getString(6));
               fornecedor.setFone2(rs.getString(7));
               fornecedor.setEndereco(rs.getString(8));
               fornecedor.setNumero(rs.getString(9));
               fornecedor.setCep(rs.getString(10));
               fornecedor.setBairro(rs.getString(11));
               fornecedor.setComplemento(rs.getString(12));
               fornecedor.setEmail(rs.getString(13));
               fornecedor.setAtivo(rs.getString(14).equals("S"));
               fornecedor.getCidade().setId(rs.getInt(15));
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Fornecedor.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
}
