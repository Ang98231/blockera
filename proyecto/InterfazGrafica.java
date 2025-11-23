import javax.swing.*;
import java.awt.*;

public class InterfazGrafica extends JFrame {
    private JPanel panelPrincipal;

    public InterfazGrafica() {
        setTitle("SGI Block");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        panelPrincipal = new JPanel(new CardLayout());

        add(panelPrincipal);
        setVisible(true);
    }
    
    public void setPanelPrincipal(JPanel panelPrincipal){
        getContentPane().removeAll();
        this.panelPrincipal=panelPrincipal;
        add(panelPrincipal);  
        revalidate();
        repaint(); 
    }

}
