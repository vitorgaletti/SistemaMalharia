package componente;

import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class MeuRadioButton extends JRadioButton implements MeuComponente {

    private boolean podeHabilitar;
    private boolean obrigatorio;
    private String nome;
    private String[] dados;

    public MeuRadioButton(String nome, boolean obrigatorio, boolean podeHabilitar,String[] dados, boolean selected) {
        super(nome, selected);
        this.podeHabilitar = podeHabilitar;
        this.dados = dados;
        this.obrigatorio = obrigatorio;
    }

    @Override
    public void limpar() {

    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(status && podeHabilitar); // habilitar ou desabilitar
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;

    }

    @Override
    public boolean eVazio() {
        return getText().trim().isEmpty();
    }

    @Override
    public void setValor(Object valor) {
        setText("" + valor);
    }

    @Override
    public Object getValor() {
        return "" + getText();

    }

    @Override
    public String getNome() {
         return "";
    }

    public void setLabel(JLabel rotulo) {

    }

    public void setLabelVisible(boolean status) {

    }

    public void recebeFoco() {
        this.requestFocus();
    }

}
