import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PerpustakaanGUI {
    public static void main(String[] args) {

        ImageIcon logo = new ImageIcon("kanata halo.jpg");

        ImageIcon buku = new ImageIcon("buku.png");

        JFrame frame = new JFrame();
        frame.setTitle("Perpustakaan Digital");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setSize(480,800);
        
        frame.setLayout(new BorderLayout());
                
        frame.setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(33,102,255));
        panel.setPreferredSize(new Dimension(480, 80));
        panel.setBorder(new EmptyBorder(10, 20, 10,20));
        
        JLabel judul = new JLabel();
        judul.setText("Perpustakaan Digital");
        judul.setIcon(buku);
        judul.setFont(new Font("Arial", Font.BOLD, 20));
        judul.setForeground(Color.WHITE);
        panel.add(judul, BorderLayout.WEST);
        frame.add(panel, BorderLayout.NORTH);

        JLabel subJudul = new JLabel("Sistem Peminjaman Buku");
        subJudul.setForeground(Color.WHITE);
        subJudul.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(subJudul, BorderLayout.WEST);

        JPanel utama = new JPanel(new BorderLayout());
        utama.setBackground(new Color(245,246,250));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(new EmptyBorder(15,15,10,15));
        JTextField searchBar = new JTextField();
        searchBar.setText("  Cari judul...");
        searchBar.setPreferredSize(new Dimension(0,40));
        searchBar.setBorder(new LineBorder(new Color(220,220,220),1,true));
        searchPanel.add(searchBar);
        utama.add(searchPanel, BorderLayout.NORTH);

        frame.add(utama);
        frame.setVisible(true);

        
        
        


    }
    
}
