
package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoCheckBox;
import componente.MeuCampoData;
import componente.MeuCampoFormatado;
import componente.MeuCampoInteiro;
import dao.DaoLote;
import dao.DaoMateriaPrima;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Lote;
import pojo.ParametrosConsulta;
import rendererizador.CellRendererData;
import rendererizador.InteiroRender;
import rendererizador.MonetarioRender;
import rendererizador.RenderizadorQtde;
import rendererizador.RenderizadorTexto;

import util.FiltroPesquisa;




public class TelaCadastroLote extends TelaCadastro{
    
    public static TelaCadastroLote tela;
    public Lote lote = new Lote();
    public DaoLote daoLote = new DaoLote(lote);
   
    
    
    public ParametrosConsulta parametrosConsulta =
            new ParametrosConsulta("Consulta de Matéria-Prima",
                                   DaoMateriaPrima.SQL_PESQUISAR_ATIVOS,
                                   new String[] {"Código", "Nome", "Quantidade", "Valor Unitário", "Valor Total", "Possui Lote", "Ativo"},
                                   new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                   new FiltroPesquisa("NOME", "MATERIAPRIMA_NOME", String.class),
                                   new FiltroPesquisa("LOTE", "LOTE", String.class),
                                   new FiltroPesquisa("ATIVO", "ATIVO", String.class),
            
                                },
                                new DefaultTableCellRenderer[] { 
                                                                new InteiroRender(), new RenderizadorTexto(), new RenderizadorQtde(), new MonetarioRender(),
                                                                new MonetarioRender(), new RenderizadorTexto(), new RenderizadorTexto()},
                                this, this, false, false, false
        ); 
  
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoData campoDataValidade = new MeuCampoData(10, true, "Data de Validade" );
    public MeuCampoInteiro campoQuantidade = new MeuCampoInteiro(5, true, true, "Quantidade" );
    public MeuCampoInteiro campoQuantidadeTotal = new MeuCampoInteiro(5, false, false, "Quantidade Total");
    public MeuCampoFormatado campoLote = new MeuCampoFormatado(20, 50, true, "Lote");
    public MeuCampoBuscar campoMateriaPrima = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroMateriaPrima.class, DaoMateriaPrima.SQL_COMBOBOXLOTE, parametrosConsulta, true, true, "Matéria-Prima");
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
      
   
    
        
     
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroLote();
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
    
    public TelaCadastroLote() {
        
        super("Cadastro de Lote");
        
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoDataValidade,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoQuantidade,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoLote,ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoMateriaPrima,ESQUERDA);
        adicionarComponente(6, 1, 1, 1, campoAtivo,ESQUERDA);
        adicionarComponente(4, 4, 1, 1, campoQuantidadeTotal,ESQUERDA);
        adicionarComponente(7, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
        adicionaListener();
    
    }
    
    public void setPersistencia() {
      lote.setId((int) campoCodigo.getValor());
      lote.setDatavalidade((Date) campoDataValidade.getValor());
      lote.setQuantidade((int) campoQuantidade.getValor());
      lote.setLote((String) campoLote.getValor());
      lote.getMateriaprima().setId((int) campoMateriaPrima.getValor());
      lote.setAtivo((boolean) campoAtivo.getValor());
    }
   
    public void getPersistencia() {
      campoCodigo.setValor(lote.getId());
      campoDataValidade.setValor(lote.getDatavalidade());
      campoQuantidade.setValor(lote.getQuantidade());
      campoLote.setValor(lote.getLote());
      campoMateriaPrima.setValor(lote.getMateriaprima().getId());
      campoAtivo.setValor(lote.isAtivo());  
      
      
    }
   
   
    @Override
    public boolean incluirBD() {
     Integer qtdetotal = (Integer) TelaCompra.tela.campoQuantidadeItens.getValor();
     Integer quantidade = (Integer) campoQuantidade.getValor();
     
     setPersistencia();
     getPersistencia(); 
     daoLote.incluir();
      
    if(quantidade < qtdetotal){
          JOptionPane.showMessageDialog(null, "Quantidade de Lote Insuficiente.");
          
          TelaCadastro telaLote = new TelaCadastroLote();
          getDesktopPane().add(telaLote);
          telaLote.moveToFront();
          telaLote.incluir();
          habilitarCampos(true);
      } 
       
    return true;
        
}
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoLote.alterar();       
    }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoLote.excluir();       
    }

    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       lote.setId(pk);
       daoLote.consultar();
       getPersistencia();
    }
    
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta((parametrosConsulta) = new ParametrosConsulta("Consulta de Lote",
        DaoLote.SQL_PESQUISAR,
        new String [] {"Código", "Data de Validade", "Quantidade", "Lote", "Ativo", "Matéria-Prima"},
        new FiltroPesquisa[]{
            new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
            new FiltroPesquisa("DATA DE VALIDADE", "DATA", Date.class),
            new FiltroPesquisa("LOTE", "LOTE", String.class),
            new FiltroPesquisa("ATIVO", "ATIVO", String.class),
            new FiltroPesquisa("MATÉRIA-PRIMA", "LOTE_MATERIAPRIMA", String.class)
        },
                                    new DefaultTableCellRenderer[] { 
                                                                    new InteiroRender(), new CellRendererData(), new RenderizadorQtde(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, true, false, false)
        );
    
       
    }
   
    
    @Override
    public void preencherDados(int pk) {
      lote.setId(pk);
      daoLote.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
    
   public void adicionaListener() {
         
            campoDataValidade.addFocusListener(new FocusAdapter() {
           
            @Override
            public void focusLost(FocusEvent fe) {
                 verificaData();
               
            }
        });
         
      
            campoQuantidade.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent fe) {
                  verificaQtdeLote();
                  
                
            }
            @Override
            public void focusGained(FocusEvent fe) {
                  Integer qtdetotal = (Integer) TelaCompra.tela.campoQuantidadeItens.getValor();
                  campoQuantidade.setValor(qtdetotal);  
                  campoQuantidadeTotal.setValor(qtdetotal);
                 
                }
            });
                
        }
    
       public void verificaData() {
       Date data = new Date();  
       Date validade = ((Date) campoDataValidade.getValor());
      
        if( validade.before(data)) {
           JOptionPane.showMessageDialog(null, "Data de Validade Inválida");
           campoDataValidade.requestFocus();
        } else {
           return;
       } 
    }    

  
public void verificaQtdeLote() {
         
        Integer qtdetotal = (Integer) TelaCompra.tela.campoQuantidadeItens.getValor();
        Integer quantidade = (Integer) campoQuantidade.getValor();

        if(quantidade > qtdetotal || quantidade == 0 ) { 
            
             JOptionPane.showMessageDialog(null, "Quantidade deve ser menor que Quantidade Total e maior que zero");
             campoQuantidade.requestFocus();
             
         } else {
            return; 
         } 
         
       }
     
    }
 

