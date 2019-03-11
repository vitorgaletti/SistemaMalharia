package componente;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFormattedTextField;

public class MeuCampoFormatado2 extends JFormattedTextField implements MeuComponente, FocusListener {

    private boolean obrigatorio;
    private String nome;
    private boolean podeHabilitar;

    public MeuCampoFormatado2(int colunas, boolean obrigatorio, String nome, boolean podeHabilitar) {
        setColumns(colunas);
        this.obrigatorio = obrigatorio;
        this.nome = nome;
        this.podeHabilitar = podeHabilitar;

    }

    @Override
    public void limpar() {
        setText("");
    }

    @Override
    public boolean eVazio() {
        return getText().trim().isEmpty();
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
    public String getNome() {
        return nome;
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(status && podeHabilitar);
    }

    @Override
    public void focusGained(FocusEvent fe) {
        setBackground(Color.white);
        setCaretPosition(0);
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if (eObrigatorio() && eVazio()) {
            setBackground(Color.orange);
        } else {
            setBackground(Color.white);
        }
        if (!eValido()) {
            setBackground(Color.orange);
        }
    }

    @Override
    public Object getValor() {
        return getText();
    }

    @Override
    public void setValor(Object valor) {
        setText("" + valor);
    }
}