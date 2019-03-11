
package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Caixa;




public class DaoCaixa {
    
    
    private Caixa caixa;
    
    private final String SQL_INCLUIR =
            "INSERT INTO CAIXA VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQL_ALTERAR =
            "UPDATE CAIXA SET DATA = ?, SALDOINICIAL = ?, SALDOFINAL = ?, DIFERENCA = ?, ABERTO = ? WHERE ID = ? ";
    private final String SQL_EXCLUIR =
            "DELETE FROM PAIS WHERE ID = ?";
    private final String SQL_CONSULTAR =
            "SELECT * FROM CAIXA WHERE ID = ?";
    public static final String SQL_PESQUISAR =
            "SELECT ID, DATA, SALDOINICIAL, SALDOFINAL, DIFERENCA, ABERTO FROM CAIXA  ORDER BY DATA";

    public DaoCaixa(Caixa caixa) {
        this.caixa = caixa;
    }
    
}

