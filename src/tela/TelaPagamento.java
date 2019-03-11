
package tela;


import componente.MeuCampoData;
import componente.MeuCampoInteiro;
import componente.MeuCampoMonetario;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import pojo.Pagamento;




public class TelaPagamento extends TelaCadastro{
    
    public static TelaPagamento tela;
    public Pagamento pagamento = new Pagamento();
    
    
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, true, false, "Código");
    public MeuCampoData campoData= new MeuCampoData(10, true, "Data" );
    public MeuCampoMonetario campoValor = new MeuCampoMonetario(15, false, false, true, "Valor");
    public MeuCampoMonetario campoDesconto = new MeuCampoMonetario(15, false, false, true, "Desconto");
    public MeuCampoMonetario campoJuros = new MeuCampoMonetario(15, false, false, true, "Juros");
    public MeuCampoMonetario campoMulta = new MeuCampoMonetario(15, false, false, true, "Multa");
    public MeuCampoMonetario campoValorTotal = new MeuCampoMonetario(15, false, false, true, "Valor Total");
    
    
    
     
    public static TelaCadastro getTela() {  
        if (tela == null) {   
            tela = new TelaPagamento();
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
    
   
    
    public TelaPagamento() {
        
        super("Movimento de Pagamento");
        adicionarComponente(1, 1, 1, 1, campoCodigo, ESQUERDA);
        adicionarComponente(2, 1, 1, 1, campoData, ESQUERDA);
        adicionarComponente(3, 1, 1, 1, campoValor,ESQUERDA);
        adicionarComponente(4, 1, 1, 1, campoDesconto, ESQUERDA);
        adicionarComponente(5, 1, 1, 1, campoJuros, ESQUERDA);
        adicionarComponente(6, 1, 1, 1, campoMulta, ESQUERDA);
        adicionarComponente(7, 1, 1, 1, campoValorTotal, ESQUERDA);
        habilitarCampos(false);
        pack();
        
    }

   
}
