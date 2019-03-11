
package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoCheckBox;
import componente.MeuCampoInteiro;
import componente.MeuCampoMonetario;
import componente.MeuCampoTexto;
import dao.DaoCor;
import dao.DaoProdutoAcabado;
import dao.DaoModelo;
import dao.DaoTamanho;
import java.math.BigDecimal;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.ParametrosConsulta;
import pojo.ProdutoAcabado;
import rendererizador.InteiroRender;
import rendererizador.MonetarioRender;
import rendererizador.RenderizadorQtde;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;


public class TelaCadastroProdutoAcabado extends TelaCadastro{
    
    public static TelaCadastroProdutoAcabado tela;
    public ProdutoAcabado produtoacabado = new ProdutoAcabado();
    public DaoProdutoAcabado daoProdutoAcabado = new DaoProdutoAcabado(produtoacabado);
    
    
    public ParametrosConsulta parametrosConsulta =
            new ParametrosConsulta("Consulta de Modelo",
                                   DaoModelo.SQL_PESQUISAR_ATIVOS,
                                   new String[]{"Código", "Nome", "Ativo"},
                                   new FiltroPesquisa[] { new FiltroPesquisa("CÓDIGO", "ID", String.class),
                                                          new FiltroPesquisa("NOME", "NOME", String.class),
                                                          new FiltroPesquisa("ATIVO", "ATIVO", String.class)
                                   }, 
                                   new DefaultTableCellRenderer[] { 
                                                                    new InteiroRender(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto()},
                                   this, this, false, false, false
    );
       
    public ParametrosConsulta parametrosConsultaCor =
            new ParametrosConsulta("Consulta de Cor",
                                   DaoCor.SQL_PESQUISAR_ATIVOS,
                                   new String[]{"Código", "Nome", "Ativo"},
                                   new FiltroPesquisa[] { new FiltroPesquisa("CÓDIGO", "ID", String.class),
                                                          new FiltroPesquisa("NOME", "NOME", String.class),
                                                          new FiltroPesquisa("ATIVO", "ATIVO", String.class)
                                   }, 
                                   new DefaultTableCellRenderer[] { 
                                                                    new InteiroRender(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto()},
                                   this, this, false, false, false
    );
    
    public ParametrosConsulta parametrosConsultaTamanho =
            new ParametrosConsulta("Consulta de Tamanho",
                                   DaoTamanho.SQL_PESQUISAR_ATIVOS,
                                   new String[]{"Código", "Tamanho", "Ativo"},
                                   new FiltroPesquisa[] { new FiltroPesquisa("CÓDIGO", "ID", String.class),
                                                          new FiltroPesquisa("TAMANHO", "TAMANHO", String.class),
                                                          new FiltroPesquisa("ATIVO", "ATIVO", String.class)
                                   }, 
                                   new DefaultTableCellRenderer[] { 
                                                                    new InteiroRender(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto()},
                                   this, this, false, false, false
    );
   
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoDescricao = new MeuCampoTexto(20, 50, false, "Descrição" );
    public MeuCampoInteiro campoQuantidade = new MeuCampoInteiro(5,true, false,"Quantidade" );
    public MeuCampoMonetario campoValor= new MeuCampoMonetario(15, false, true, true, "Valor");
    public MeuCampoBuscar campoModelo = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroModelo.class, DaoModelo.SQL_COMBOBOX, parametrosConsulta, false, true, "Modelo");
    public MeuCampoBuscar campoCor = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroCor.class, DaoCor.SQL_COMBOBOX, parametrosConsultaCor, false, true, "Cor");
    public MeuCampoBuscar campoTamanho = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroTamanho.class, DaoTamanho.SQL_COMBOBOX, parametrosConsultaTamanho, false, true, "Tamanho");
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroProdutoAcabado();
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
    
    public TelaCadastroProdutoAcabado() {
        
        super("Cadastro de Produto Acabado");
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoDescricao,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoQuantidade,ESQUERDA);
        adicionarComponente(4, 3, 1, 1, campoValor,ESQUERDA);
        adicionarComponente(7, 1, 1, 1, campoModelo,ESQUERDA);
        adicionarComponente(8, 1, 1, 1, campoCor,ESQUERDA);
        adicionarComponente(9, 1, 1, 1, campoTamanho,ESQUERDA);
        adicionarComponente(10, 1, 1, 1, campoAtivo,ESQUERDA);
        adicionarComponente(11, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
       
        
    }
    
    public void setPersistencia() {
      produtoacabado.setId((int) campoCodigo.getValor());
      produtoacabado.setDescricao((String) campoDescricao.getValor());
      produtoacabado.setQuantidade((int) campoQuantidade.getValor());
      produtoacabado.setValor(campoValor.getValor());
      produtoacabado.getModelo().setId((int) campoModelo.getValor());
      produtoacabado.getCor().setId((int) campoCor.getValor());
      produtoacabado.getTamanho().setId((int) campoTamanho.getValor());
      produtoacabado.setAtivo((boolean) campoAtivo.getValor());
             
   }
   
    public void getPersistencia() {
      campoCodigo.setValor(produtoacabado.getId());
      campoDescricao.setValor(produtoacabado.getDescricao());
      campoQuantidade.setValor(produtoacabado.getQuantidade());
      campoValor.setValor(produtoacabado.getValor());
      campoModelo.setValor(produtoacabado.getModelo().getId());
      campoCor.setValor(produtoacabado.getCor().getId());
      campoTamanho.setValor(produtoacabado.getTamanho().getId());
      campoAtivo.setValor(produtoacabado.isAtivo());
     
   }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoProdutoAcabado.incluir();
   }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoProdutoAcabado.alterar();       
   }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoProdutoAcabado.excluir();       
   }

    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       produtoacabado.setId(pk);
       daoProdutoAcabado.consultar();
       getPersistencia();
    }
   
    @Override
    public void consultar() {
       
            super.consultar();
            new TelaConsulta(parametrosConsulta = new ParametrosConsulta("Consulta de Produto Acabado",
            DaoProdutoAcabado.SQL_PESQUISAR,
            new String[] {"Código", "Descrição", "Quantidade", "Valor", "Ativo", "Modelo" , "Cor", "Tamanho"},
            new FiltroPesquisa[] {
                         new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                         new FiltroPesquisa("DESCRICAO", "PRODUTOACABADO_DESCRICAO", String.class),
                         new FiltroPesquisa("MODELO", "PRODUTOACABADO_MODELO", String.class),
                         new FiltroPesquisa("COR", "PRODUTOACABADO_COR", String.class),
                         new FiltroPesquisa("TAMANHO", "PRODUTOACABADO_TAMANHO", String.class),
            
        },
                                        new DefaultTableCellRenderer[] { 
                                                                        new InteiroRender(), new RenderizadorTexto(),
                                                                        new RenderizadorQtde(), new MonetarioRender(), new RenderizadorTexto(), new RenderizadorTexto(),new RenderizadorTexto(),new RenderizadorTexto(),},
                                   this, this, false, false, false));
    
        
    }   
    @Override
    public void preencherDados(int pk) {
      produtoacabado.setId(pk);
      daoProdutoAcabado.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
   
   
    public void atualizaCamposTotal(JComponent quemChamou) {
       
        BigDecimal quantidade = BigDecimal.valueOf(new Double((int) campoQuantidade.getValor()));
        BigDecimal valor = (BigDecimal) campoValor.getValor();
        if (quemChamou != null && valor.compareTo(BigDecimal.ZERO) == -1) {
            JOptionPane.showMessageDialog(null, "Valor negativo, verifique!!!");
            quemChamou.requestFocus();
        } 
    }
  
  
}
