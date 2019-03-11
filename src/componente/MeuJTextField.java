package componente;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MeuJTextField extends JTextField implements FocusListener, MeuComponente2{

    private boolean obrigatorio;
    private boolean podeHabilitar;
    public String nome;
    public int colunas;

    public MeuJTextField(int colunas, boolean podeHabilitar, boolean obrigatorio, String nome) {
        super(colunas);
        
        this.podeHabilitar = podeHabilitar;
        this.obrigatorio = obrigatorio;
        this.nome = nome;
        addFocusListener(this);
        focusLost(null);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!eVazio()) {
                            setBorder(BorderFactory.createLineBorder(Color.WHITE));
                        } 
                        if (eVazio() && eObrigatorio()) {
                            setBorder(BorderFactory.createLineBorder(Color.WHITE));
                        }
                    }
                });
            }
        });
    }

    public String getDica() {
        return nome;
    }

    public void setDica(String nome) {
        this.nome = nome;
    }

    public void limpar() {
        setText("");
    }

    public void habilitar(boolean status) {
        setEnabled(podeHabilitar && status);
    }

    public boolean eObrigatorio() {
        return obrigatorio;
    }

    public boolean eVazio() {
        return getText().trim().isEmpty();
    }

    public boolean eValido() {
        return true;
    }

    @Override
    public void focusGained(FocusEvent fe) {
        Color azulBrilhante = new Color(192 ,217, 217); 
        setBackground(azulBrilhante);
        int lenght = getText().length();
        setCaretPosition(lenght);
        if (eObrigatorio() && eVazio()) {
            setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.WHITE)); 
        }     
    }

    @Override
    public void focusLost(FocusEvent fe) {
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
        if (eObrigatorio()) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
        }
    }
    
     public void setValor(String valor) {
        setText(valor);
    }

    @Override
    public String getNome() {
       return nome;
    }
}

  
