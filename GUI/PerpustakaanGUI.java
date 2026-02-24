import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class PerpustakaanGUI {
    public static void main(String[] args) {

        ImageIcon logo = new ImageIcon("kanata halo.jpg");

        ImageIcon buku = new ImageIcon("buku.png");

        Border border = BorderFactory.createLineBorder(Color.blue, 3);

        JLabel label = new JLabel();
        label.setText("Perpustakaan Digital");
        label.setIcon(buku);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setIconTextGap(0);
        label.setBounds(20, 15, 300, 30);

        JLabel subLabel = new JLabel("Sistem Peminjaman Buku");
        subLabel.setForeground(Color.WHITE);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subLabel.setBounds(64, 40, 300, 20); 

        JPanel panel = new JPanel();
        panel.setBackground(new Color(33,102,255));
        panel.setBounds(0,0,900,80);
        label.setVerticalAlignment(JLabel.CENTER);
        panel.setLayout(null);
        
        JFrame frame = new JFrame();
        frame.setTitle("Perpustakaan Digital");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(900,600);
        frame.setVisible(true);
        frame.add(panel);
        panel.add(label);
        panel.add(subLabel);
        frame.setIconImage(logo.getImage());
        
        
        

        
    }
    
}
