
package tela;

import componente.MeuCampoCheckBox;
import componente.MeuCampoInteiro;
import componente.MeuCampoTexto;
import dao.DaoTamanho;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.ParametrosConsulta;
import pojo.Tamanho;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;


public class TelaCadastroTamanho extends TelaCadastro{
    
    public static TelaCadastroTamanho tela;
    public Tamanho tamanho = new Tamanho();
    public DaoTamanho daoTamanho = new DaoTamanho(tamanho);
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoTamanho = new MeuCampoTexto(5, 2, true, "Tamanho" );
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    public ParametrosConsulta parametrosConsulta;
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroTamanho();
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
    
    
    public TelaCadastroTamanho() {
        
        super("Cadastro de Tamanho");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoTamanho,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoAtivo,ESQUERDA);
        adicionarComponente(4, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }
    
    public void setPersistencia() {
      tamanho.setId((int) campoCodigo.getValor());
      tamanho.setTamanho((String) campoTamanho.getValor());
      tamanho.setAtivo((boolean) campoAtivo.getValor());
             
    }
   
    public void getPersistencia() {
      campoCodigo.setValor(tamanho.getId());
      campoTamanho.setValor(tamanho.getTamanho());
      campoAtivo.setValor(tamanho.isAtivo());    
    }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoTamanho.incluir();
   }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoTamanho.alterar();       
   }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoTamanho.excluir();       
   }


    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       tamanho.setId(pk);
       daoTamanho.consultar();
       getPersistencia();
   }
   
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta(parametrosConsulta = new ParametrosConsulta("Consulta de Tamanho",
                               DaoTamanho.SQL_PESQUISAR,
                               new String[] {"Código", "Tamanho" ,"Ativo"},
                               new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                               new FiltroPesquisa("TAMANHO", "TAMANHO", String.class),
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
      tamanho.setId(pk);
      daoTamanho.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
   
   
    
  
}
