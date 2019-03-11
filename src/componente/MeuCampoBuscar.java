package componente;

import banco.Conexao;
import dao.DaoMateriaPrima;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import pojo.MateriaPrima;
import pojo.ParametrosConsulta;
import tela.TelaCadastroProducao;
import static tela.TelaCadastroProducao.QtdEstoque;
import tela.TelaCompra;
import tela.TelaConsulta;
import tela.TelaContaPagar;
import tela.TelaPedidoDeCompra;



public class MeuCampoBuscar extends JPanel implements MeuComponente {
   private MeuCampoInteiro codigo = new MeuCampoInteiro(3, false, true, null);
   //private MeuCampoTexto texto = new MeuCampoTexto(15, 45, false, null);
   private MeuJTextField texto = new MeuJTextField(15, true, false, null);
   private Icon icon = new ImageIcon("src/imagens/pesquisar.png");
   private JButton jb = new  JButton(icon);
   
   private Icon iconGetTela = new ImageIcon("src/imagens/add.png");
   private JButton jbGetTela = new  JButton(iconGetTela);
   
   private boolean podeHabilitar;
   private List<Integer> pks;
   private String sql;
   private boolean obrigatorio;
   private String nome;
   public boolean achou = false;
   private ParametrosConsulta parametrosConsulta;
    
   
   public MeuCampoBuscar(final Class tela, final Class tela2, String sql, ParametrosConsulta parametrosConsulta, boolean obrigatorio, boolean podeHabilitar, String nome) {
       this.obrigatorio = obrigatorio;
       this.sql = sql;
       this.parametrosConsulta = parametrosConsulta;
       this.nome = nome;
       this.podeHabilitar = podeHabilitar;
       parametrosConsulta.setChamador(this);
       setLayout(new FlowLayout());
       add(codigo);
       add(texto);
       texto.setEditable(false);
       add(jb);
       jb.setToolTipText("Clique para iniciar uma pesquisa de " + getNome() + ".");
       add(jbGetTela);
       jbGetTela.setToolTipText("Clique para iniciar um novo cadastro de " + getNome() + ".");
       achou = false;
       
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new TelaConsulta(parametrosConsulta);     
            }
        });
       
        jbGetTela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Method metodoGetTela = tela2.getMethod("getTela", new Class[]{});
                    metodoGetTela.invoke(null, new Object[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
      
        
        codigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == VK_ENTER) {
                    if (codigo.getValor().equals(0)) {
                        achou = false;
                        JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado para o código " + codigo.getValor() + ". Verifique os cadastros clicando no ícone de pesquisa.");
                        codigo.setValor(0);
                        texto.limpar();
                        codigo.requestFocus(); 
                    } else if (verificaDuplicidadeContasGeradas() == true) {        
                        codigo.requestFocus();
                        codigo.limpar();
                    } else if (verificaDuplicidadeContasGeradas() == false) {
                        preencher();
                    }
                    
                }
            } 
        });
        
        codigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (codigo.eVazio()) {
                            texto.setText("");
                            
                        }   
                    }
                });
            }
        });
      
  } 
      
   @Override
   public void addKeyListener(KeyListener kl) { 
       codigo.addKeyListener(kl);
   }
    
   public void preencher()  {        
        try {   
            String tempSql = "SELECT * from (" + sql + ") WHERE ID = " + codigo.getValor();
           
            ResultSet rs = Conexao.getConexao().createStatement().executeQuery(tempSql);
            if (rs.next()) {          
                codigo.setText(rs.getString(1));
                texto.setValor(rs.getString(2));
                achou = true;
                
                if (parametrosConsulta.getTelaChamadora().equals(TelaCadastroProducao.tela)) {
                    
                    TelaCadastroProducao.tela.tabelaItem.getTableModel().alteraMateriaPrima(TelaCadastroProducao.tela.campoItensMateriaPrima.getValor(), TelaCadastroProducao.tela.tabelaItem.getLinhaSelecionada());
                    MateriaPrima materiaprima = TelaCadastroProducao.tela.tabelaItem.getTableModel().getMateriaPrima(TelaCadastroProducao.tela.tabelaItem.getLinhaSelecionada());
                     TelaCadastroProducao.tela.QtdEstoque.setValor(materiaprima.getQuantidade());
                    
                    } else if(parametrosConsulta.getTelaChamadora().equals(TelaPedidoDeCompra.tela)) {
                     TelaPedidoDeCompra.tela.tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().alteraMateriaPrima(TelaPedidoDeCompra.tela.campoItensMateriaPrima.getValor(), TelaPedidoDeCompra.tela.tabelaItensPedidoDeCompra.getLinhaSelecionada());
                     MateriaPrima materiaprima = TelaPedidoDeCompra.tela.tabelaItensPedidoDeCompra.getTableItensPedidoDeCompra().getMateriaPrima(TelaPedidoDeCompra.tela.tabelaItensPedidoDeCompra.getLinhaSelecionada());
                     TelaPedidoDeCompra.tela.campoValorunitarioItens.setValor(materiaprima.getValorunitario());
                     TelaPedidoDeCompra.tela.QtdeEstoque.setValor(materiaprima.getQuantidade());
                
                    } else if(parametrosConsulta.getTelaChamadora().equals(TelaCompra.tela)) {
                          if (getNome().equals("Matéria-Prima")) {
                              TelaCompra.tela.tabelaItensCompra.getTableItensCompra().alteraMateriaPrima(TelaCompra.tela.campoItensMateriaPrima.getValor(), TelaCompra.tela.tabelaItensCompra.getLinhaSelecionada());
                              MateriaPrima materiaprima = TelaCompra.tela.tabelaItensCompra.getTableItensCompra().getMateriaPrima(TelaCompra.tela.tabelaItensCompra.getLinhaSelecionada()); 
                              TelaCompra.tela.campoValorunitarioItens.setValor(materiaprima.getValorunitario());
                              TelaCompra.tela.QtdeEstoque.setValor(materiaprima.getQuantidade());
                                TelaCompra.tela.atualizaCamposTotal(null);
                                DaoMateriaPrima daoMateriaPrima = new DaoMateriaPrima(materiaprima);
                                materiaprima.setId((int) TelaCompra.tela.campoItensMateriaPrima.getValor());
                                daoMateriaPrima.consultar();
                                TelaCompra.tela.botaoLote.setEnabled(materiaprima.isElote() && TelaCompra.tela.campoStatus.getValor() == "CF"); 
                    
                        }
                    
                    }
                
                
             } else if (codigo.getValor().equals(0) /*&& achou == true*/) {

            } else {
                achou = false;
                JOptionPane.showMessageDialog(null, "Não foi possível encontrar o Código Desejado. Tente clicar no ícone de Consulta");
                codigo.setValor(0);
                texto.limpar();
                codigo.requestFocus(); 
                }
            
            } catch (Exception e) {  
                return;
        }
    }

 public void preencherDuploClique()  {        
        try {
            String tempSql = "SELECT * from (" + sql + ") WHERE ID = " + codigo.getValor();
           
            ResultSet rs = Conexao.getConexao().createStatement().executeQuery(tempSql);
            if (rs.next()) {          
                codigo.setText(rs.getString(1));
                texto.setValor(rs.getString(2));
                achou = true;
            } else {
                achou = false;
                JOptionPane.showMessageDialog(null, "Não foi possível encontrar o Código Desejado. Tente clicar no ícone de Consulta2 ");
                codigo.setValor("");
                texto.limpar();
                codigo.requestFocus();
             
            }
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null, "Não foi possível preencher os campos.");
            e.printStackTrace();
            return;
        }
    }
      
    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        if (codigo.getText().trim().equals("") || texto.getText().trim().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void limpar() {
        codigo.setText("");
        texto.setText("");
    }

    @Override
    public void habilitar(boolean status) {
        codigo.setEnabled(podeHabilitar && status);
        texto.setEnabled(podeHabilitar && status);
        jb.setEnabled( podeHabilitar && status);
        jbGetTela.setEnabled( podeHabilitar && status);
    }
    
    public void editar(boolean status) {
        codigo.setEditable(podeHabilitar && status);
        texto.setEditable(podeHabilitar && status);
        jb.setEnabled( podeHabilitar && status);
        jbGetTela.setEnabled( podeHabilitar && status);
    }

    public Object getValor() {
        return codigo.getValor();
    }
        
    public void setValor(Object valor) {
        codigo.setValor(valor);
        preencher();
        
        if (parametrosConsulta.getChamador().getClass() == MeuCampoBuscar.class && parametrosConsulta.getTelaChamadora() == TelaContaPagar.class) {
            codigo.setValor(codigo.getValor());
        }
    }
    
    public void setValorDuploClique(Object valor) {
        
        codigo.setValor(valor);
        preencher();
         
        if (parametrosConsulta.getChamador().getClass() == MeuCampoBuscar.class && parametrosConsulta.getTelaChamadora().getClass() == TelaContaPagar.class) {
            TelaContaPagar.tela.preencher1();
        }
        
        if (parametrosConsulta.getChamador().getClass() == MeuCampoBuscar.class && parametrosConsulta.getTelaChamadora().getClass() == TelaCompra.class){
               
                TelaCompra.tela.preencherDados2();
            
        }
    }

    @Override
    public String getNome() {
        return nome;
    }
    
    public boolean verificaDuplicidadeContasGeradas() {
        try {
            if (getNome() == "Compra") {
                String tempSqlConsultar = "SELECT CONTAPAGAR.ID, CONTAPAGAR.IDCOMPRA, CONTAPAGAR.DATA FROM CONTAPAGAR"
                                    + " LEFT JOIN COMPRA ON CONTAPAGAR.IDCOMPRA = COMPRA.ID WHERE CONTAPAGAR.IDCOMPRA = " + codigo.getValor();
            int varAuxiliarCodCompra = (int) codigo.getValor();
            
            ResultSet rs = Conexao.getConexao().createStatement().executeQuery(tempSqlConsultar);
            if (rs.next()) {          
                codigo.setText(rs.getString(1));
                int varAuxiliarCodContaPagar = (int) codigo.getValor();
                codigo.limpar();
                JOptionPane.showMessageDialog(null, " A Compra com o código " + varAuxiliarCodCompra + " Já foi Gerada." +
                                                    "\n Tente clicar no icone de Consulta para verificar");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
            
        } catch (Exception e) {  
            JOptionPane.showMessageDialog(null, "Não foi possível preencher os campos.");
            e.printStackTrace();
            return false;
        }
    }

   
}