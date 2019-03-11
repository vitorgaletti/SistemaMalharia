
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import pojo.Cliente;



public class DaoCliente {
    
    
    private Cliente cliente;
    
    private final String SQL_INCLUIR =
            "INSERT INTO CLIENTE VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final String SQL_ALTERAR =
            "UPDATE CLIENTE SET NOMERAZAOSOCIAL= ?, APELIDONOMEFANTASIA = ?, TIPOCLIENTE = ?, CPFCNPJ = ?,"
            +" RGIE = ?, ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CEP = ?, FONE1 = ?,"
            + "FONE2 = ?, EMAIL = ?, REDESOCIAL = ?, ATIVO = ?, IDCIDADE = ?  WHERE ID = ? ";
    
    private final String SQL_EXCLUIR =
            "DELETE FROM CLIENTE WHERE ID = ?";
    
    private final String SQL_CONSULTAR =
            "SELECT * FROM CLIENTE WHERE ID = ?";
   public static final String SQL_PESQUISAR =
            "SELECT CLIENTE.ID, CLIENTE.NOMERAZAOSOCIAL AS CLIENTE_NOMERAZAOSOCIAL, CLIENTE.TIPOCLIENTE AS CLIENTE_TIPOCLIENTE, CLIENTE.CPFCNPJ AS CLIENTE_CPFCNPJ,"
                     + "CLIENTE.ATIVO AS CLIENTE_ATIVO, CIDADE.NOME || ' - ' || ESTADO.SIGLA AS CLIENTE_CIDADE  FROM CLIENTE, CIDADE, ESTADO WHERE CIDADE.ID = CLIENTE.IDCIDADE AND ESTADO.ID = CIDADE.IDESTADO";
    public static final String SQL_COMBOBOX =
             "SELECT ID, NOMERAZAOSOCIAL, APELIDONOMEFANTASIA, TIPOCLIENTE, CPFCNPJ, RGIE,"
            + "ENDERECO, NUMERO, COMPLEMENTO, BAIRRO, CEP, FONE1, FONE2, EMAIL, REDESOCIAL FROM CLIENTE WHERE ATIVO = 'S' ORDER BY NOMERAZAOSOCIAL";
   
    public DaoCliente(Cliente cliente) {
        this.cliente = cliente;
    }
            
    public boolean incluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
      
           ps.setInt(1, cliente.getId());
           ps.setString(2, cliente.getNomerazaosocial());
           ps.setString(3, cliente.getApelidonomefantasia());
           ps.setString(4, cliente.getTipocliente());
           ps.setString(5, cliente.getCpfcnpj());
           ps.setString(6, cliente.getRgie());
           ps.setString(7, cliente.getEndereco());
           ps.setString(8, cliente.getNumero());
           ps.setString(9, cliente.getComplemento());
           ps.setString(10, cliente.getBairro());
           ps.setString(11, cliente.getCep());
           ps.setString(12, cliente.getFone1());
           ps.setString(13, cliente.getFone2());
           ps.setString(14, cliente.getEmail());
           ps.setString(15, cliente.getRedesocial());
           ps.setString(16, (cliente.isAtivo() ? "S" : "N"));
           ps.setInt(17, cliente.getCidade().getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Cadastro realizado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
       } catch (Exception e) {
            if(e.getMessage().contains("TCLIENTE")) {
              
               JOptionPane.showMessageDialog(null,"Cliente já cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir o Cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
        }
    }
    
    
    public boolean alterar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
           ps.setString(1, cliente.getNomerazaosocial());
           ps.setString(2, cliente.getApelidonomefantasia());
           ps.setString(3, cliente.getTipocliente());
           ps.setString(4, cliente.getCpfcnpj());
           ps.setString(5, cliente.getRgie());
           ps.setString(6, cliente.getEndereco());
           ps.setString(7, cliente.getNumero());
           ps.setString(8, cliente.getComplemento());
           ps.setString(9, cliente.getBairro());
           ps.setString(10, cliente.getCep());
           ps.setString(11, cliente.getFone1());
           ps.setString(12, cliente.getFone2());
           ps.setString(13, cliente.getEmail());
           ps.setString(14, cliente.getRedesocial());
           ps.setString(15, (cliente.isAtivo() ? "S" : "N"));
           ps.setInt(16, cliente.getCidade().getId());
           ps.setInt(17, cliente.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
      } catch (Exception e) {
            if(e.getMessage().contains("TCLIENTE")) {
               
               JOptionPane.showMessageDialog(null,"Cliente já cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar o Cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
           
            }
            return false;
        }
    }
    
    
      public boolean excluir() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
           ps.setInt(1, cliente.getId());
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
           return true;
        } catch (Exception e) {
              if(e.getMessage().contains("violation of FOREIGN KEY ")) { 
               JOptionPane.showMessageDialog(null,"Cliente está sendo utilizado em outro Cadastro.", "Erro", JOptionPane.WARNING_MESSAGE);
            } else {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            
         }
         return false;
       }
}   
       public boolean consultar() {
        try {
           PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
           ps.setInt(1, cliente.getId());
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               cliente.setNomerazaosocial(rs.getString(2));
               cliente.setApelidonomefantasia(rs.getString(3));
               cliente.setTipocliente(rs.getString(4));
               cliente.setCpfcnpj(rs.getString(5));
               cliente.setRgie(rs.getString(6));
               cliente.setEndereco(rs.getString(7));
               cliente.setNumero(rs.getString(8));
               cliente.setComplemento(rs.getString(9));
               cliente.setBairro(rs.getString(10));
               cliente.setCep(rs.getString(11));
               cliente.setFone1(rs.getString(12));
               cliente.setFone2(rs.getString(13));
               cliente.setEmail(rs.getString(14));
               cliente.setRedesocial(rs.getString(15));
               cliente.setAtivo(rs.getString(16).equals("S"));
               cliente.getCidade().setId(rs.getInt(17));
           }
           return true;
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Não foi possível consultar o Cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
           return false;
        }
    }
}
