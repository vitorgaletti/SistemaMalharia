package componente;

import java.util.List;
import javax.swing.JComboBox;

public class MeuCampoComboBox extends JComboBox implements MeuComponente {

    private boolean obrigatorio;
    private String nome;
    private String[][] dados;
    private boolean podeHabilitar;
    
    

    public MeuCampoComboBox(boolean obrigatorio,boolean podeHabilitar, String[][] dados, String nome) {
        this.obrigatorio = obrigatorio;
        this.dados = dados;
        this.nome = nome;
        this.podeHabilitar = podeHabilitar;
        preencher();
    }

    public void preencher() {
        
        removeAllItems();
        for (int i = 0; i < dados.length; i++) {
            addItem(dados[i][1]);
        }
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
    public boolean eVazio() {
        return getSelectedIndex() < 0;
    }

    @Override
    public void limpar() {
        setSelectedIndex(-1);
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(podeHabilitar && status);
    }

    @Override
    public Object getValor() {
        if (getSelectedIndex() == -1) {
            return "";
        } else {
            return dados[getSelectedIndex()][0];
       }
    }
    @Override
    public void setValor(Object valor) {
        for (int i = 0; i < dados.length; i++) {
            if (dados[i][0].equals((String) valor)) {
                setSelectedIndex(i);
                return;
            }
        }
        setSelectedIndex(-1);
    }

    @Override
    public String getNome() {
        return nome;
    }
}
