package componente;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;


public class MeuJDesktopPane extends JDesktopPane {
    private Image imagem;
    
    public MeuJDesktopPane(){
        try{
            imagem = new ImageIcon(getClass().getResource("/imagens/logopoly.jpg")).getImage();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Não foi possivel  ler a imagem do logotipo da empresa");
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Dimension dimension = this.getSize();
        int x = (int) (dimension.getWidth() - imagem.getWidth(this)) / 2;
        int y = (int) (dimension.getHeight() - imagem.getHeight(this)) / 2;
        g.drawImage(imagem, x, y, imagem.getWidth(this), imagem.getHeight(this), this);
        
    }
    
    
    
}