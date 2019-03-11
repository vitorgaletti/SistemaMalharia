package tela;

import componente.MeuCampoCheckBox;
import componente.MeuCampoInteiro;
import componente.MeuCampoMonetario;
import componente.MeuCampoTexto;
import dao.DaoMateriaPrima;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.MateriaPrima;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.MonetarioRender;
import rendererizador.RenderizadorQtde;
import rendererizador.RenderizadorTexto;
import tabela.TabelaLote;
import tablemodel.TableLote;
import util.FiltroPesquisa;


public class TelaCadastroMateriaPrima extends TelaCadastro {
    
   
    public static TelaCadastroMateriaPrima tela;
    public MateriaPrima materiaprima = new MateriaPrima();
    public DaoMateriaPrima daoMateriaPrima = new DaoMateriaPrima(materiaprima);
    public ParametrosConsulta parametrosConsulta;
    public MateriaPrima itensmateriaprima = new MateriaPrima();
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(20, 50, true, "Nome");
    public MeuCampoInteiro campoQuantidade = new MeuCampoInteiro(5, true, false, "Quantidade");
    public MeuCampoMonetario campoValorUnitario = new MeuCampoMonetario(15, false, true, true, "Valor unitário");
    public MeuCampoMonetario campoValorTotal= new MeuCampoMonetario(15, false, false, true, "Valor total");
    public MeuCampoCheckBox campoElote = new MeuCampoCheckBox(false, true, "É Lote");
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public TabelaLote tabelaMateriaprima = new TabelaLote();
    
      public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroMateriaPrima();
            TelaSistema.meuJDesktopPane.add(tela);
            tela.addInternalFrameListener(new InternalFrameAdapter() { 
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    TelaSistema.meuJDesktopPane.remove(tela);
                    tela = null;
                }
            });
           
        }
        
        
        TelaSistema.meuJDesktopPane.moveToFront(tela);
        return tela;
    }
  
    public TelaCadastroMateriaPrima() {
        super("Cadastro de Matéria-Prima");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoNome,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoQuantidade,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoValorUnitario,ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoValorTotal,ESQUERDA);
        adicionarComponente(6, 1, 1, 1, campoElote,ESQUERDA);
        adicionarComponente(7, 1, 1, 1, campoAtivo,ESQUERDA);
        adicionarComponente(9, 1, 2, 2, tabelaMateriaprima,ESQUERDA);
        adicionarComponente(10, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
        adicionaListeners();
        adicionaListenersoutros();
    }
   
    public void adicionaListenersoutros() {
             
        campoElote.addItemListener(new ItemListener() {
            
        @Override
         public void itemStateChanged(ItemEvent e) {
             
        if (campoElote.getValor().equals(false)) {
            tabelaMateriaprima.setEnabled(false);
           }
           else {
               tabelaMateriaprima.setEnabled(true);
            }
         }
         });
     }
   

    public void setPersistencia() {
        materiaprima.setId((int) campoCodigo.getValor());
        materiaprima.setNome((String) campoNome.getValor());
        materiaprima.setQuantidade((int) campoQuantidade.getValor());
        materiaprima.setValorunitario(campoValorUnitario.getValor());
        materiaprima.setValortotal(campoValorTotal.getValor());
        materiaprima.setElote((boolean) campoElote.getValor());
        materiaprima.setAtivo((boolean) campoAtivo.getValor());       

    }

    public void getPersistencia() {
        campoCodigo.setValor(materiaprima.getId());
        campoNome.setValor(materiaprima.getNome());
        campoQuantidade.setValor(materiaprima.getQuantidade());
        campoValorUnitario.setValor(materiaprima.getValorunitario());
        campoValorTotal.setValor(materiaprima.getValortotal());
        campoElote.setValor(materiaprima.isElote());
        campoAtivo.setValor(materiaprima.isAtivo());
        ((TableLote) tabelaMateriaprima.getTabela().getModel()).setDados(materiaprima.getLote());
        
    }
    
    @Override
    public boolean incluirBD() {
        setPersistencia();
        getPersistencia(); 
        return daoMateriaPrima.incluir();
    }

    @Override
    public boolean alterarBD() {
        setPersistencia();
        return daoMateriaPrima.alterar();
    }

    @Override
    public boolean excluirBD() {
        setPersistencia();
        return daoMateriaPrima.excluir();
    }

    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       materiaprima.setId(pk);
       daoMateriaPrima.consultar();
       getPersistencia();
    }
   
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta(parametrosConsulta = new ParametrosConsulta("Consulta de Matéria-Prima",
                               DaoMateriaPrima.SQL_PESQUISAR,
                               new String[] {"Código", "Nome", "Quantidade", "Valor Unitário", "Valor Total", "Possui Lote", "Ativo"},
                               new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                               new FiltroPesquisa("NOME", "MATERIAPRIMA_NOME", String.class),
                               new FiltroPesquisa("LOTE", "LOTE", String.class),
                               new FiltroPesquisa("ATIVO", "ATIVO", String.class),
            
                                },
                                new DefaultTableCellRenderer[] { 
                                                                new InteiroRender(), new RenderizadorTexto(), new RenderizadorQtde(), new MonetarioRender(),
                                                                new MonetarioRender(), new RenderizadorTexto(), new RenderizadorTexto()},
                                this, this, false, false, false)
        );
    }

    @Override
    public void preencherDados(int pk) {
        materiaprima.setId(pk);
        daoMateriaPrima.consultar();
        getPersistencia();
        super.preencherDados(pk);
    }
    
    public void adicionaListeners() {
          KeyAdapter KeyAdapterTextoFiltro = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        atualizaCamposTotal(null);
                       }
                    }
                );
               }
             };
         
            campoQuantidade.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent fe) {
                     atualizaCamposTotal(null);
            }
            });
        
            campoValorUnitario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent fe) {
               
               campoValorUnitario.setBackground(Color.WHITE);
            }
            @Override
            public void focusGained(FocusEvent fe) {
                 campoValorUnitario.addKeyListener(KeyAdapterTextoFiltro);
                 campoValorUnitario.setBackground(new Color(27, 215, 252)); 
                
            }
            
         });

    }
    public void atualizaCamposTotal(JComponent quemChamou) {
       
        BigDecimal quantidade = BigDecimal.valueOf(new Double((int) campoQuantidade.getValor()));
        BigDecimal valorUnitario = (BigDecimal) campoValorUnitario.getValor();
        BigDecimal valorTotal = quantidade.multiply(valorUnitario);
        if (quemChamou != null && valorTotal.compareTo(BigDecimal.ZERO) == -1) {
            JOptionPane.showMessageDialog(null, "Valor negativo, verifique!!!");
            quemChamou.requestFocus();
        } else {
            campoValorTotal.setValor(valorTotal);
        }
    }

}


