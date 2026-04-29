import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import Buku.admin;
import Buku.adminlogin;
public class PerpustakaanGUI2 {

    private static final Color WARNA_BG = new Color(243, 244, 246);
    private static final Color WARNA_KARTU = Color.WHITE;
    private static final Color WARNA_AKSEN = new Color(37, 99, 235);
    private static final Color WARNA_INPUT = new Color(249, 250, 251);
    private static final Color WARNA_TEKS = new Color(17, 24, 39);
    private static final Color WARNA_SUB_TEKS = new Color(107, 114, 128);
    private static final Color WARNA_BORDER = new Color(229, 231, 235);

    private static JFrame frame;
    private static JPanel panelUtama;
    private static CardLayout navigasi;

    protected static manusia penggunaAktif;
    private static adminlogin adminSystem = new adminlogin();
    private static admin adminAktif;

    public static void main(String[] args) {
        frame = new JFrame("Perpustakaan Digital");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);

        navigasi = new CardLayout();
        panelUtama = new JPanel(navigasi);
        panelUtama.add(buatHalamanLogin(), "HALAMAN_LOGIN");

        frame.add(panelUtama);
        frame.setVisible(true);

        peminjam.dataKelas();
        Buku.Utama.jalankanProgram();
    }
    private static void tambahPlaceholder(JTextField field, String placeholder) {
    field.setText(placeholder);
    field.setForeground(WARNA_SUB_TEKS);

    field.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            // Saat diklik, hapus placeholder
            if (field.getText().equals(placeholder)) {
                field.setText("");
                field.setForeground(WARNA_TEKS);
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            // Saat diklik tempat lain & field kosong, tampilkan placeholder lagi
            if (field.getText().isEmpty()) {
                field.setText(placeholder);
                field.setForeground(WARNA_SUB_TEKS);
            }
        }
    });
}
    private static void tambahPlaceholderPassword(JPasswordField field, String placeholder) {
    field.setText(placeholder);
    field.setForeground(WARNA_SUB_TEKS);
    field.setEchoChar((char) 0); // tampilkan teks placeholder dulu

    field.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            if (String.valueOf(field.getPassword()).equals(placeholder)) {
                field.setText("");
                field.setForeground(WARNA_TEKS);
                field.setEchoChar('•'); // aktifkan karakter sensor
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            if (field.getPassword().length == 0) {
                field.setText(placeholder);
                field.setForeground(WARNA_SUB_TEKS);
                field.setEchoChar((char) 0); // tampilkan teks placeholder lagi
            }
        }
    });
}
    private static JPanel buatHalamanLogin() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(WARNA_BG);

        JPanel kartuLogin = new JPanel();
        kartuLogin.setBackground(WARNA_KARTU);
        kartuLogin.setPreferredSize(new Dimension(350, 450));
        kartuLogin.setLayout(new BoxLayout(kartuLogin, BoxLayout.Y_AXIS));
        kartuLogin.setBorder(new LineBorder(WARNA_BORDER, 1));

        JLabel judul = new JLabel("Welcome Back");
        judul.setForeground(WARNA_TEKS);
        judul.setFont(new Font("Arial", Font.BOLD, 24));
        judul.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtUser = new JTextField();
        tambahPlaceholder(txtUser, "Username");
        txtUser.setBackground(WARNA_INPUT);
        txtUser.setForeground(WARNA_SUB_TEKS);
        txtUser.setCaretColor(WARNA_TEKS);
        txtUser.setMaximumSize(new Dimension(280, 40));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(WARNA_BORDER), 
            new EmptyBorder(0, 10, 0, 10)
        ));

        JPasswordField txtPass = new JPasswordField();
        tambahPlaceholderPassword(txtPass, "Password");
        txtPass.setBackground(WARNA_INPUT);
        txtPass.setForeground(WARNA_SUB_TEKS);
        txtPass.setCaretColor(WARNA_TEKS);
        txtPass.setMaximumSize(new Dimension(280, 40));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(WARNA_BORDER), 
            new EmptyBorder(0, 10, 0, 10)
        ));

        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(WARNA_AKSEN);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setMaximumSize(new Dimension(280, 40));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(e -> { 
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Isi username & password!");
        return;
        }

        if (adminSystem.ceklogin(username, password)) {
        adminAktif = adminSystem.getadminAktif();

        JOptionPane.showMessageDialog(null, 
            "Login berhasil sebagai ADMIN: " + adminAktif.getusername());

        
        panelUtama.add(buatHalamanDashboard(), "HALAMAN_DASHBOARD");
        navigasi.show(panelUtama, "HALAMAN_DASHBOARD");
        panelUtama.revalidate();
        panelUtama.repaint();
        return;
        }

        penggunaAktif = peminjam.login(username, password);

        if (penggunaAktif != null ) {
        JOptionPane.showMessageDialog(null, 
            "Login berhasil sebagai " + penggunaAktif.getnama() + "!");

        panelUtama.add(buatHalamanDashboard(), "HALAMAN_DASHBOARD");
        navigasi.show(panelUtama, "HALAMAN_DASHBOARD");
        panelUtama.revalidate();
        panelUtama.repaint();

        } else {
        JOptionPane.showMessageDialog(null, "Login gagal!");
        }
        });

        kartuLogin.add(Box.createVerticalStrut(50));
        kartuLogin.add(judul);
        kartuLogin.add(Box.createVerticalStrut(40));
        kartuLogin.add(txtUser);
        kartuLogin.add(Box.createVerticalStrut(15));
        kartuLogin.add(txtPass);
        kartuLogin.add(Box.createVerticalStrut(30));
        kartuLogin.add(btnLogin);

        loginPanel.add(kartuLogin);
        return loginPanel;
    }

    private static JPanel buatHalamanDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(WARNA_BG);

        String nama;
        String nim;

        if (adminAktif != null) {
        nama = adminAktif.getusername();
        nim = "ADMIN";
        } else if (penggunaAktif != null) {
        nama = penggunaAktif.getnama();
        nim = penggunaAktif.getnim();
        } else {
        nama = "Guest";
        nim = "-";
        }

        JPanel sidebar = new JPanel();
        sidebar.setBackground(WARNA_KARTU);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new MatteBorder(0, 0, 0, 1, WARNA_BORDER));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(WARNA_KARTU);
        logoPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        logoPanel.setMaximumSize(new Dimension(280, 100));
        
        JLabel iconLogo = new JLabel("||||", SwingConstants.CENTER); 
        iconLogo.setOpaque(true);
        iconLogo.setBackground(WARNA_AKSEN);
        iconLogo.setForeground(Color.WHITE);
        iconLogo.setPreferredSize(new Dimension(40, 40));
        iconLogo.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel teksLogo = new JLabel(" Perpustakaan Digital");
        teksLogo.setForeground(WARNA_TEKS);
        teksLogo.setFont(new Font("Arial", Font.BOLD, 22));
        
        logoPanel.add(iconLogo);
        logoPanel.add(teksLogo);

        JLabel subMenu = new JLabel("MENU UTAMA");
        subMenu.setForeground(WARNA_SUB_TEKS);
        subMenu.setFont(new Font("Arial", Font.BOLD, 12));
        subMenu.setBorder(new EmptyBorder(10, 25, 10, 25));

        JPanel itemBuku = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemBuku.setBackground(new Color(239, 246, 255)); 
        itemBuku.setMaximumSize(new Dimension(240, 45));
        itemBuku.setBorder(new LineBorder(WARNA_AKSEN, 1, true));
        
        JLabel teksBuku = new JLabel(" Daftar Buku");
        teksBuku.setForeground(WARNA_AKSEN);
        teksBuku.setFont(new Font("Arial", Font.BOLD, 15));
        itemBuku.add(teksBuku);

        JPanel itemNotif = new JPanel(new BorderLayout());
        itemNotif.setBackground(WARNA_KARTU);
        itemNotif.setMaximumSize(new Dimension(240, 45));
        itemNotif.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel teksNotif = new JLabel(" Notifikasi");
        teksNotif.setForeground(WARNA_SUB_TEKS);
        teksNotif.setFont(new Font("Arial", Font.PLAIN, 15));
        
        JLabel badge = new JLabel("2", SwingConstants.CENTER);
        badge.setForeground(Color.WHITE);
        badge.setBackground(WARNA_AKSEN);
        badge.setOpaque(true);
        badge.setPreferredSize(new Dimension(20, 20));
        badge.setFont(new Font("Arial", Font.BOLD, 11));
        
        itemNotif.add(teksNotif, BorderLayout.WEST);
        itemNotif.add(badge, BorderLayout.EAST);

        JPanel profilBawah = new JPanel(new BorderLayout());
        profilBawah.setBackground(WARNA_KARTU);
        profilBawah.setBorder(new EmptyBorder(15, 20, 15, 20));
        profilBawah.setMaximumSize(new Dimension(280, 80));
        
        JLabel lingkaranMB = new JLabel("MB", SwingConstants.CENTER);
        lingkaranMB.setOpaque(true);
        lingkaranMB.setBackground(WARNA_AKSEN);
        lingkaranMB.setForeground(Color.WHITE);
        lingkaranMB.setPreferredSize(new Dimension(45, 45));
        lingkaranMB.setFont(new Font("Arial", Font.BOLD, 14));
        
        JPanel teksProfil = new JPanel(new GridLayout(2, 1));
        teksProfil.setBackground(WARNA_KARTU);
        teksProfil.setBorder(new EmptyBorder(0, 12, 0, 0));
        
        JLabel namaUser = new JLabel(nama);
        namaUser.setForeground(WARNA_TEKS);
        namaUser.setFont(new Font("Arial", Font.BOLD, 13));
        
        JLabel nimUser = new JLabel(nim);
        nimUser.setForeground(WARNA_SUB_TEKS);
        nimUser.setFont(new Font("Arial", Font.PLAIN, 11));
        
        teksProfil.add(namaUser);
        teksProfil.add(nimUser);
        
        JLabel iconLogout = new JLabel("->"); 
        iconLogout.setForeground(WARNA_SUB_TEKS);
        iconLogout.setFont(new Font("Arial", Font.BOLD, 16));

        profilBawah.add(lingkaranMB, BorderLayout.WEST);
        profilBawah.add(teksProfil, BorderLayout.CENTER);
        profilBawah.add(iconLogout, BorderLayout.EAST);

        sidebar.add(logoPanel);
        sidebar.add(subMenu);
        sidebar.add(itemBuku);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(itemNotif);
        sidebar.add(Box.createVerticalGlue()); 
        sidebar.add(new JSeparator(JSeparator.HORIZONTAL)); 
        sidebar.add(profilBawah);

        JPanel konten = new JPanel(new BorderLayout());
        konten.setBackground(WARNA_BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WARNA_BG);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel sapaan = new JLabel("helo " + penggunaAktif.getnama() + "!");
        sapaan.setFont(new Font("Arial", Font.BOLD, 22));
        sapaan.setForeground(WARNA_TEKS);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
        penggunaAktif = null;
        adminAktif = null;
        navigasi.show(panelUtama, "HALAMAN_LOGIN");
        });

        header.add(sapaan, BorderLayout.WEST);
        header.add(btnLogout, BorderLayout.EAST);

        JPanel gridBuku = new JPanel(new GridLayout(0, 3, 20, 20));
        gridBuku.setBackground(WARNA_BG);
        gridBuku.setBorder(new EmptyBorder(0, 30, 30, 30));

        for (Buku.Daftarbuku buku : Buku.Utama.getDaftarBuku()) {
            boolean tersedia = Buku.Utama.isTersedia(buku.getJudul());
            gridBuku.add(buatKartuBuku(buku, tersedia));
}

        JScrollPane scroll = new JScrollPane(gridBuku);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(WARNA_BG);

        konten.add(header, BorderLayout.NORTH);
        konten.add(scroll, BorderLayout.CENTER);

        dashboard.add(sidebar, BorderLayout.WEST);
        dashboard.add(konten, BorderLayout.CENTER);

        return dashboard;
    }

    private static JPanel buatKartuBuku(Buku.Daftarbuku buku, boolean tersedia) {
    JPanel kartu = new JPanel(new BorderLayout());
    kartu.setBackground(WARNA_KARTU);
    kartu.setBorder(new LineBorder(WARNA_BORDER, 1));

    JPanel areaGambar = new JPanel();
    areaGambar.setBackground(new Color(243, 244, 246));
    areaGambar.setPreferredSize(new Dimension(0, 120));
    areaGambar.add(new JLabel("📖 " + buku.getGenre()));

    JPanel info = new JPanel(new GridLayout(5, 1));
    info.setBackground(WARNA_KARTU);
    info.setBorder(new EmptyBorder(10, 10, 10, 10));

    JLabel lJudul    = new JLabel(buku.getJudul());
    lJudul.setForeground(WARNA_TEKS);
    lJudul.setFont(new Font("Arial", Font.BOLD, 13));

    JLabel lPenulis  = new JLabel(buku.getPenulis());
    lPenulis.setForeground(WARNA_SUB_TEKS);

    JLabel lTahun    = new JLabel("Terbit: " + buku.getTahunterbit());
    lTahun.setForeground(WARNA_SUB_TEKS);
    lTahun.setFont(new Font("Arial", Font.PLAIN, 11));

    // Warna badge status ketersediaan
    JLabel lStatus   = new JLabel(tersedia ? "● Tersedia" : "● Kosong");
    lStatus.setForeground(tersedia ? new Color(22, 163, 74) : new Color(220, 38, 38));
    lStatus.setFont(new Font("Arial", Font.BOLD, 11));

    JButton btnPinjam = new JButton(tersedia ? "Pinjam" : "Tidak Tersedia");
    btnPinjam.setBackground(tersedia ? WARNA_AKSEN : WARNA_SUB_TEKS);
    btnPinjam.setForeground(Color.WHITE);
    btnPinjam.setFocusPainted(false);
    btnPinjam.setEnabled(tersedia); // nonaktifkan tombol jika kosong

    info.add(lJudul);
    info.add(lPenulis);
    info.add(lTahun);
    info.add(lStatus);
    info.add(btnPinjam);

    kartu.add(areaGambar, BorderLayout.NORTH);
    kartu.add(info, BorderLayout.CENTER);

    return kartu;
    }
}