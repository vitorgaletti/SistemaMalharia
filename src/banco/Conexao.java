
package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class Conexao {
    
    private static Connection conexao;
    
    public static Connection getConexao() {
        if (conexao != null) {
            return conexao;
        } else {
            try {
                Class.forName("org.firebirdsql.jdbc.FBDriver");
                conexao = DriverManager.getConnection("jdbc:firebirdsql:"
                        + "//localhost:3050/"
                        + System.getProperty("user.dir")
                        + "/bdmalharia.fdb?charSet=utf-8;defaultResultSetHoldable=True", "SYSDBA", "masterkey");
               } catch (ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro no driver.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.");
            }
            return conexao;
        }
    }
     
    public static int getGenerator(String nomeGenerator) {
        try {
            
            String sql = "SELECT GEN_ID(" + nomeGenerator + ", 1) FROM RDB$DATABASE";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar obter o generator " + nomeGenerator);
            return -1;
        }
   }
    
    public static List<String[]> executaQuery(String sql) {
        try {
            List<String[]> dados = new ArrayList();
            Statement st = Conexao.getConexao().createStatement();
            ResultSet rs = st.executeQuery(sql);
            int numeroColunas = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                String[] linha = new String[numeroColunas];
                for(int i = 1; i <= numeroColunas; i++) {
                    linha[i-1] = rs.getString(i);
                }
                dados.add(linha);
            }
            return dados;
              
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível consultar o banco de dados");
            return new ArrayList();
        }
    }
    
    public static List<Object[]> pesquisar(String sql) {
        try {
            List<Object[]> retorno = new ArrayList();
            Statement st = getConexao().createStatement();
            ResultSet rs = st.executeQuery(sql);
            int colunas = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] linha = new Object[colunas];
                for (int i = 1; i <= colunas; i++) {
                    linha[i - 1] = rs.getString(i);
                }
                retorno.add(linha);
            }
            return retorno;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível executar a pesquisa");
            e.printStackTrace();
            return null;
        }
    }
      
}
