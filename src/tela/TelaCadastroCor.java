
package tela;

import componente.MeuCampoCheckBox;
import componente.MeuCampoInteiro;
import componente.MeuCampoTexto;
import dao.DaoCor;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Cor;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;


public class TelaCadastroCor extends TelaCadastro{
    
    public static TelaCadastroCor tela;
    public Cor cor = new Cor();
    public DaoCor daoCor = new DaoCor(cor);
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(20, 50, true, "Nome" );
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    public ParametrosConsulta parametrosConsulta;
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
      public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroCor();
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
    
    
    public TelaCadastroCor() {
        
        super("Cadastro de Cor");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoNome,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoAtivo,ESQUERDA);
        adicionarComponente(4, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }
    
    public void setPersistencia() {
      cor.setId((int) campoCodigo.getValor());
      cor.setNome((String) campoNome.getValor());
      cor.setAtivo((boolean) campoAtivo.getValor());
             
    }
   
    public void getPersistencia() {
      campoCodigo.setValor(cor.getId());
      campoNome.setValor(cor.getNome());
      campoAtivo.setValor(cor.isAtivo());  
     
   }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoCor.incluir();
    }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoCor.alterar();       
    }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoCor.excluir();       
    }

    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       cor.setId(pk);
       daoCor.consultar();
       getPersistencia();
   }
      
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta(parametrosConsulta = new ParametrosConsulta("Consulta de Cor",
                               DaoCor.SQL_PESQUISAR,
                               new String[] {"Código", "Nome" ,"Ativo"},
                               new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                               new FiltroPesquisa("NOME", "NOME", String.class),
                               new FiltroPesquisa("ATIVO", "ATIVO", String.class),
            
                                },
                                new DefaultTableCellRenderer[] { 
                                                                new InteiroRender(), new RenderizadorTexto(),
                                                                new RenderizadorTexto()},
                                this, this, false, false, false)
        );
    }
   
    @Override
    public void preencherDados(int pk) {
      cor.setId(pk);
      daoCor.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
   
   
    
  
}
