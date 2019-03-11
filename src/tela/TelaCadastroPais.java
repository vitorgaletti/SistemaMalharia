
package tela;

import componente.MeuCampoCheckBox;
import componente.MeuCampoInteiro;
import componente.MeuCampoTexto;
import dao.DaoPais;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Pais;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;



public class TelaCadastroPais extends TelaCadastro{
    
    public static TelaCadastroPais tela;
    public Pais pais = new Pais();
    public DaoPais daoPais = new DaoPais(pais);
    public ParametrosConsulta parametrosConsulta;
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(20, 50, true, "Nome" );
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroPais();
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
    
   
    
    public TelaCadastroPais() {
        
        super("Cadastro de País");
        adicionarComponente(1, 1, 1, 1, campoCodigo, ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoNome, ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoAtivo, ESQUERDA);
        adicionarComponente(4, 2, 1, 1, frase, ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }
    
   
    
    public void setPersistencia() {
      pais.setId((int) campoCodigo.getValor());
      pais.setNome((String) campoNome.getValor());
      pais.setAtivo((boolean) campoAtivo.getValor());
             
    }
   
    public void getPersistencia() {
      campoCodigo.setValor(pais.getId());
      campoNome.setValor(pais.getNome());
      campoAtivo.setValor(pais.isAtivo());  
    }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoPais.incluir();
    }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoPais.alterar();       
    }
   
   @Override
   public boolean excluirBD() {
      setPersistencia();
      return daoPais.excluir();       
   }
   
    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       pais.setId(pk);
       daoPais.consultar();
       getPersistencia();
    }
   
    @Override
    public void consultar() {
        super.consultar();
        new TelaConsulta(parametrosConsulta = new ParametrosConsulta("Consulta de País",
                               DaoPais.SQL_PESQUISAR,
                               new String[] {"Código", "Nome", "Ativo"},
                               new FiltroPesquisa[] {new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                               new FiltroPesquisa("NOME", "NOME", String.class),
                               new FiltroPesquisa("ATIVO", "ATIVO", String.class)
            
                                },
                                new DefaultTableCellRenderer[] { 
                                                                new InteiroRender(), new RenderizadorTexto(),
                                                                new RenderizadorTexto()},
                                this, this, false, false, false)
        );
    }
    
    @Override
    public void preencherDados(int pk) {
      pais.setId(pk);
      daoPais.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
   
}
