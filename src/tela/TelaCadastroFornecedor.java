
package tela;

import componente.MeuCampoBuscar;
import componente.MeuCampoCEP;
import componente.MeuCampoCNPJ;
import componente.MeuCampoCheckBox;
import componente.MeuCampoInteiro;
import componente.MeuCampoFormatado;
import componente.MeuCampoTelefone;
import componente.MeuCampoTexto;
import dao.DaoFornecedor;
import dao.DaoCidade;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import pojo.Fornecedor;
import pojo.ParametrosConsulta;
import rendererizador.InteiroRender;
import rendererizador.RenderizadorTexto;
import util.FiltroPesquisa;



public class TelaCadastroFornecedor extends TelaCadastro {
    
    public static TelaCadastroFornecedor tela;
    public Fornecedor fornecedor = new Fornecedor();
    public DaoFornecedor daoFornecedor = new DaoFornecedor(fornecedor);
    
    public ParametrosConsulta parametrosConsulta =
            new ParametrosConsulta("Consulta de Cidade",
                                   DaoCidade.SQL_PESQUISAR_ATIVOS,
                                   new String[]{"Código", "Nome", "Sigla", "Ativo", "Estado"},
                                   new FiltroPesquisa[] { new FiltroPesquisa("CÓDIGO", "ID", String.class),
                                                          new FiltroPesquisa("NOME", "CIDADE_NOME", String.class),
                                                          new FiltroPesquisa("ESTADO", "ESTADO_NOME", String.class),
                     
                                   }, 
                                   new DefaultTableCellRenderer[] { new InteiroRender(), new RenderizadorTexto(),
                                                                    new RenderizadorTexto(), new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, false, false, false
     );
   
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(20, 50, false, "Nome" );
    public MeuCampoTexto campoRazaoSocial = new MeuCampoTexto(20, 50, true, "Razão Social" );
    public MeuCampoTexto campoNomeFantasia = new MeuCampoTexto(20, 50, false, "Nome Fantasia" );
    public MeuCampoCNPJ campoCnpj = new MeuCampoCNPJ(18,false,"CNPJ" );
    public MeuCampoTelefone campoFone1 = new MeuCampoTelefone(14, false, "Fone1");
    public MeuCampoTelefone campoFone2 = new MeuCampoTelefone(14, false , "Fone2");
    public MeuCampoFormatado campoEndereco = new MeuCampoFormatado(20, 50,false,"Endereço");
    public MeuCampoFormatado campoNumero = new MeuCampoFormatado(10, 20, false,"Número");
    public MeuCampoCEP campoCep = new MeuCampoCEP(10, false, "CEP");
    public MeuCampoTexto campoBairro = new MeuCampoTexto(20,50,false,"Bairro");
    public MeuCampoTexto campoComplemento = new MeuCampoTexto(20,20,false,"Complemento");
    public MeuCampoFormatado campoEmail = new MeuCampoFormatado(20,50,false,"E-mail");
    public MeuCampoBuscar campoCidade = new MeuCampoBuscar(TelaConsulta.class, TelaCadastroCidade.class, DaoCidade.SQL_COMBOBOX, parametrosConsulta, true, true, "Cidade");
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox(true, true, "Ativo");
    
    public JLabel frase = new JLabel("<html>Campo Obrigatório <font color=red>*</font><body>");
    
     public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaCadastroFornecedor();
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
    
    
    public TelaCadastroFornecedor() {
        
        super("Cadastro de Fornecedor");
              
        adicionarComponente(1, 1, 1, 1, campoCodigo,ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoNome,ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoRazaoSocial,ESQUERDA);
        adicionarComponente(3, 4, 1, 1, campoNomeFantasia,ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoCnpj,ESQUERDA);
        adicionarComponente(7, 1, 1, 1, campoFone1,ESQUERDA);
        adicionarComponente(7, 4, 1, 1, campoFone2,ESQUERDA);
        adicionarComponente(9, 1, 1, 1, campoEndereco,ESQUERDA);
        adicionarComponente(9, 4, 1, 1, campoNumero,ESQUERDA);
        adicionarComponente(11, 1, 1, 1, campoCep,ESQUERDA);
        adicionarComponente(11, 4, 1, 1, campoBairro,ESQUERDA);
        adicionarComponente(12, 1, 1, 1, campoComplemento,ESQUERDA);
        adicionarComponente(13, 1, 1, 1, campoEmail,ESQUERDA);
        adicionarComponente(14, 1, 1, 1, campoCidade,ESQUERDA);
        adicionarComponente(15, 1, 1, 1, campoAtivo,ESQUERDA);
        adicionarComponente(16, 2, 1, 1, frase,ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }
    
    
    
    public void setPersistencia() {
      fornecedor.setId((int) campoCodigo.getValor());
      fornecedor.setNome((String) campoNome.getValor());
      fornecedor.setRazaosocial((String) campoRazaoSocial.getValor());
      fornecedor.setNomefantasia((String) campoNomeFantasia.getValor());
      fornecedor.setCnpj((String) campoCnpj.getValor());
      fornecedor.setFone1((String) campoFone1.getValor());
      fornecedor.setFone2((String) campoFone2.getValor());
      fornecedor.setEndereco((String) campoEndereco.getValor());
      fornecedor.setNumero((String) campoNumero.getValor());
      fornecedor.setCep((String) campoCep.getValor());
      fornecedor.setBairro((String) campoBairro.getValor());
      fornecedor.setComplemento((String) campoComplemento.getValor());
      fornecedor.setEmail((String) campoEmail.getValor());
      fornecedor.getCidade().setId((int) campoCidade.getValor());
      fornecedor.setAtivo((boolean) campoAtivo.getValor());
             
    }
  
    public void getPersistencia() {
      campoCodigo.setValor(fornecedor.getId());
      campoNome.setValor(fornecedor.getNome());
      campoRazaoSocial.setValor(fornecedor.getRazaosocial());
      campoNomeFantasia.setValor(fornecedor.getNomefantasia());
      campoCnpj.setValor(fornecedor.getCnpj());
      campoFone1.setValor(fornecedor.getFone1());
      campoFone2.setValor(fornecedor.getFone2());
      campoEndereco.setValor(fornecedor.getEndereco());
      campoNumero.setValor(fornecedor.getNumero());
      campoCep.setValor(fornecedor.getCep());
      campoBairro.setValor(fornecedor.getBairro());
      campoComplemento.setValor(fornecedor.getComplemento());
      campoEmail.setValor(fornecedor.getEmail());
      campoCidade.setValor(fornecedor.getCidade().getId());
      campoAtivo.setValor(fornecedor.isAtivo());  
     
    }
   
    @Override
    public boolean incluirBD() {
      setPersistencia();
      getPersistencia(); 
      return daoFornecedor.incluir();
   }
   
    @Override
    public boolean alterarBD() {
      setPersistencia();
      return daoFornecedor.alterar();       
    }
   
    @Override
    public boolean excluirBD() {
      setPersistencia();
      return daoFornecedor.excluir();       
    }
    
    @Override
    public void consultarBD(int pk) {
       super.consultarBD(pk);
       fornecedor.setId(pk);
       daoFornecedor.consultar();
       getPersistencia();
    }
   
    @Override
    public void consultar() {
       
            super.consultar();
            new TelaConsulta((parametrosConsulta) = new ParametrosConsulta("Consulta de Fornecedor",
            DaoFornecedor.SQL_PESQUISAR,
            new String[] {"Código", "Razão Social", "CNPJ", "Ativo", "Cidade"},
            new FiltroPesquisa[] {
                                 new FiltroPesquisa("CÓDIGO", "ID", Integer.class),
                                 new FiltroPesquisa("RAZAO SOCIAL", "FORNECEDOR_RAZAOSOCIAL", String.class),
                                 new FiltroPesquisa("CNPJ", "FORNECEDOR_CNPJ", String.class),
                                 new FiltroPesquisa("ATIVO", "ATIVO", String.class),
                                 new FiltroPesquisa("CIDADE", "FORNECEDOR_CIDADE", String.class),
            
        },
                                             new DefaultTableCellRenderer[] { new InteiroRender(), new RenderizadorTexto(),
                                             new RenderizadorTexto(), new RenderizadorTexto(), new RenderizadorTexto()},
                                   this, this, false, false, false)
        );
        
    }
  
    @Override
    public void preencherDados(int pk) {
      fornecedor.setId(pk);
      daoFornecedor.consultar();
      getPersistencia();
      super.preencherDados(pk);
   }
   
     
}
