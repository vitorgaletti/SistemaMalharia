package dao;

import banco.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import pojo.ItensProducao;
import pojo.Producao;

import util.Utilitarios;

public class DaoProducao {

    private Producao producao;

    private final String SQL_INCLUIR
            = "INSERT INTO PRODUCAO VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String SQL_ALTERAR
            = "UPDATE PRODUCAO SET DATA = ?, PREVISAO = ?, DESCRICAO = ?,"
            + " QUANTIDADE = ?, STATUS = ?, IDPRODUTOACABADO = ?  WHERE ID = ? ";

    private final String SQL_EXCLUIR
            = "DELETE FROM PRODUCAO WHERE ID = ?";

    private final String SQL_CONSULTAR
            = "SELECT * FROM PRODUCAO WHERE ID = ?";

    public static final String SQL_PESQUISAR
            = "SELECT PRODUCAO.ID, PRODUCAO.DATA, PRODUCAO.PREVISAO, PRODUTOACABADO.DESCRICAO AS PRODUCAO_PRODUTOACABADO,"
            + " PRODUCAO.QUANTIDADE, PRODUCAO.STATUS FROM PRODUCAO, PRODUTOACABADO"
            + " WHERE  PRODUCAO.IDPRODUTOACABADO = PRODUTOACABADO.ID ";
    
    
    public DaoProducao(Producao producao) {
        this.producao = producao;
    }

    public boolean incluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_INCLUIR);
            
            producao.setId(Conexao.getGenerator("GEN_PRODUCAO"));
            ps.setInt(1, producao.getId());
            ps.setDate(2, Utilitarios.converteParaBanco(producao.getData()));
            ps.setDate(3, Utilitarios.converteParaBanco(producao.getPrevisao()));
            ps.setString(4, producao.getDescricao());
            ps.setInt(5, producao.getQuantidade());
            ps.setString(6, producao.getStatus());
            ps.setInt(7, producao.getProdutoacabado().getId());
            ps.executeUpdate();
            
            for (int i = 0; i < producao.getItensProducao().size(); i++) {
                DaoItensProducao daoItensProducao = new DaoItensProducao(producao.getItensProducao().get(i));
                producao.getItensProducao().get(i).setProducao(producao);
                daoItensProducao.incluir();
            }
            
            JOptionPane.showMessageDialog(null,"Produção realizada com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            
            return true;
            } catch (Exception e) {
            
            if(e.getMessage().contains("This operation is permitted on the event thread only")){
                return false;
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar incluir a Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
           }
        
    }
         
            
    

    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_ALTERAR);
            ps.setDate(1, Utilitarios.converteParaBanco(producao.getData()));
            ps.setDate(2, Utilitarios.converteParaBanco(producao.getPrevisao()));
            ps.setString(3, producao.getDescricao());
            ps.setInt(4, producao.getQuantidade());
            ps.setString(5, producao.getStatus());
            ps.setInt(6, producao.getProdutoacabado().getId());
            ps.setInt(7, producao.getId());
            ps.executeUpdate();
            for (int i = 0; i < producao.getItensProducao().size(); i++) {
                DaoItensProducao daoItensProducao = new DaoItensProducao(producao.getItensProducao().get(i));
                if (producao.getItensProducao().get(i).getId() > 0) {
                    daoItensProducao.alterar();
                } else {
                    producao.getItensProducao().get(i).setProducao(producao);
                    daoItensProducao.incluir();
                }
            }
            for (int i = 0; i < producao.getControlePksItens().size(); i++) {
                boolean achou = false;
                for (int ii = 0; ii < producao.getItensProducao().size(); ii++) {
                    if (producao.getControlePksItens().get(i)
                            == producao.getItensProducao().get(ii).getId()) {
                        achou = true;
                    }
                }
                if (achou == false) {
                    ItensProducao itensProducao = new ItensProducao();
                    itensProducao.setId(producao.getControlePksItens().get(i));
                    DaoItensProducao daoItensProducao = new DaoItensProducao(itensProducao);
                    daoItensProducao.excluir();
                }
            }
            JOptionPane.showMessageDialog(null,"Alterado com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar alterar a Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean excluir() {
        try {
            DaoItensProducao.excluirItens(producao);
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_EXCLUIR);
            ps.setInt(1, producao.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Excluído com Sucesso.","Mensagem", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve um problema ao tentar excluir o Producao.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQL_CONSULTAR);
            ps.setInt(1, producao.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                producao.setData(Utilitarios.converteDoBanco(rs.getDate(2)));
                producao.setPrevisao(Utilitarios.converteDoBanco(rs.getDate(3)));
                producao.setDescricao(rs.getString(4));
                producao.setQuantidade(rs.getInt(5));
                producao.setStatus(rs.getString(6));
                producao.getProdutoacabado().setId(rs.getInt(7));
                DaoProdutoAcabado produtoacabado = new DaoProdutoAcabado(producao.getProdutoacabado());
                produtoacabado.consultar();
                DaoItensProducao.consultarItens(producao);
                List<Integer> controlePksItens = new ArrayList();
                for (int i = 0; i < producao.getItensProducao().size(); i++) {
                    controlePksItens.add((int) producao.getItensProducao().get(i).getId());

                }
                producao.setControlePksItens(controlePksItens);
            }

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a Produção.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}
