import Buku.Daftarbuku;
import Buku.Ketersediaan;
import Buku.Utama;
import Buku.admin;
import Buku.adminlogin;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class PerpustakaanGUI2 extends JFrame {

    // ─────────────────────────────────────────────
    //  WARNA (Light Theme asli)
    // ─────────────────────────────────────────────
    private static final Color C_BG        = new Color(243, 244, 246);
    private static final Color C_CARD      = Color.WHITE;
    private static final Color C_AKSEN     = new Color(37, 99, 235);
    private static final Color C_INPUT     = new Color(249, 250, 251);
    private static final Color C_TEKS      = new Color(17, 24, 39);
    private static final Color C_MUTED     = new Color(107, 114, 128);
    private static final Color C_BORDER    = new Color(229, 231, 235);
    private static final Color C_HILITE    = new Color(239, 246, 255);
    private static final Color C_SUCCESS   = new Color(22, 163, 74);
    private static final Color C_DANGER    = new Color(220, 38, 38);
    private static final Color C_WARN_BG   = new Color(254, 249, 195);
    private static final Color C_WARN_FG   = new Color(146, 64, 14);

    // ─────────────────────────────────────────────
    //  STATE
    // ─────────────────────────────────────────────
    private final JPanel     panelUtama;
    private final CardLayout navigasi;
    private JPanel     dashboardContent;
    private CardLayout dashboardLayout;

    private manusia    penggunaAktif;        // mahasiswa yang login
    private admin      adminAktif;           // admin yang login
    private boolean    isAdmin = false;
    private String viewAktif = "BUKU";
    private JPanel sidebarAktif;      

    private String selectedGenre = "Semua";
    private final List<PinjamRecord>      riwayatList    = new ArrayList<>();
    private final adminlogin              adminAuth      = new adminlogin();

    // ─────────────────────────────────────────────
    //  MODEL PINJAM
    // ─────────────────────────────────────────────
    private static class PinjamRecord {
        String     nama, nim;
        Daftarbuku buku;
        boolean    dipinjam          = true;
        boolean    terlambat         = false;  // true jika dikembalikan setelah peringatan
        boolean    peringatanDikirim = false;  // true setelah admin kirim peringatan
        long       tanggalPinjamMs;
        static final long BATAS_MS = 7L * 24 * 60 * 60 * 1000; // 7 hari

        PinjamRecord(String nama, String nim, Daftarbuku buku, String tanggal) {
            this.nama = nama; this.nim = nim;
            this.buku = buku;
            this.tanggalPinjamMs = System.currentTimeMillis();
        }

        boolean sudahMelebihiBatas() {
            return System.currentTimeMillis() - tanggalPinjamMs > BATAS_MS;
        }
    }

    // ─────────────────────────────────────────────
    //  MAIN & KONSTRUKTOR
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PerpustakaanGUI2().setVisible(true));
    }

    public PerpustakaanGUI2() {
        setTitle("Perpustakaan Digital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 860);
        setLocationRelativeTo(null);
        getContentPane().setBackground(C_BG);

        peminjam.dataKelas();
        Utama.jalankanProgram();

        navigasi   = new CardLayout();
        panelUtama = new JPanel(navigasi);
        panelUtama.setOpaque(false);
        panelUtama.add(buatLayarPilihLogin(), "PILIH_LOGIN");
        panelUtama.add(buatLayarLoginMhs(),   "LOGIN_MHS");
        panelUtama.add(buatLayarLoginAdmin(), "LOGIN_ADMIN");
        add(panelUtama);
        navigasi.show(panelUtama, "PILIH_LOGIN");
    }

    // ═════════════════════════════════════════════
    //  LAYAR: PILIH TIPE LOGIN
    // ═════════════════════════════════════════════
    private JPanel buatLayarPilihLogin() {
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setOpaque(false);

        RoundedPanel kartu = new RoundedPanel(28, C_CARD);
        kartu.setPreferredSize(new Dimension(480, 420));
        kartu.setLayout(new BorderLayout());
        kartu.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Top
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("📚");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel judul = new JLabel("Perpustakaan Digital");
        judul.setForeground(C_TEKS);
        judul.setFont(new Font("Inter", Font.BOLD, 26));
        judul.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Pilih tipe akses untuk melanjutkan");
        sub.setForeground(C_MUTED);
        sub.setFont(new Font("Inter", Font.PLAIN, 13));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        top.add(logo);
        top.add(Box.createVerticalStrut(12));
        top.add(judul);
        top.add(Box.createVerticalStrut(6));
        top.add(sub);
        kartu.add(top, BorderLayout.NORTH);

        // Tombol pilihan
        JPanel tombol = new JPanel();
        tombol.setOpaque(false);
        tombol.setLayout(new BoxLayout(tombol, BoxLayout.Y_AXIS));
        tombol.setBorder(new EmptyBorder(40, 0, 0, 0));

        // Card mahasiswa
        JPanel cardMhs = buatKartuPilihan(
            "👤", "Masuk sebagai Mahasiswa",
            "Login menggunakan nama & NIM terdaftar");
        cardMhs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardMhs.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                navigasi.show(panelUtama, "LOGIN_MHS");
            }
            @Override public void mouseEntered(MouseEvent e) {
                cardMhs.setBorder(new LineBorder(C_AKSEN, 2, true));
            }
            @Override public void mouseExited(MouseEvent e) {
                cardMhs.setBorder(new LineBorder(C_BORDER, 1, true));
            }
        });

        // Card admin
        JPanel cardAdmin = buatKartuPilihan(
            "🔐", "Masuk sebagai Admin",
            "Login menggunakan username & password admin");
        cardAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardAdmin.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                navigasi.show(panelUtama, "LOGIN_ADMIN");
            }
            @Override public void mouseEntered(MouseEvent e) {
                cardAdmin.setBorder(new LineBorder(C_AKSEN, 2, true));
            }
            @Override public void mouseExited(MouseEvent e) {
                cardAdmin.setBorder(new LineBorder(C_BORDER, 1, true));
            }
        });

        tombol.add(cardMhs);
        tombol.add(Box.createVerticalStrut(14));
        tombol.add(cardAdmin);
        kartu.add(tombol, BorderLayout.CENTER);

        bg.add(kartu);
        return bg;
    }

    private JPanel buatKartuPilihan(String emoji, String judul, String sub) {
        JPanel p = new JPanel(new BorderLayout(14, 0));
        p.setBackground(C_INPUT);
        p.setBorder(new LineBorder(C_BORDER, 1, true));
        p.setMaximumSize(new Dimension(400, 72));
        p.setPreferredSize(new Dimension(380, 72));
        p.setOpaque(true);

        JLabel ico = new JLabel(emoji);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        ico.setBorder(new EmptyBorder(0, 16, 0, 0));

        JPanel teks = new JPanel(new GridLayout(2, 1, 0, 2));
        teks.setOpaque(false);
        teks.setBorder(new EmptyBorder(12, 0, 12, 16));
        JLabel lJudul = new JLabel(judul);
        lJudul.setForeground(C_TEKS);
        lJudul.setFont(new Font("Inter", Font.BOLD, 14));
        JLabel lSub = new JLabel(sub);
        lSub.setForeground(C_MUTED);
        lSub.setFont(new Font("Inter", Font.PLAIN, 11));
        teks.add(lJudul);
        teks.add(lSub);

        JLabel arrow = new JLabel("›");
        arrow.setForeground(C_MUTED);
        arrow.setFont(new Font("Inter", Font.BOLD, 22));
        arrow.setBorder(new EmptyBorder(0, 0, 0, 16));

        p.add(ico,   BorderLayout.WEST);
        p.add(teks,  BorderLayout.CENTER);
        p.add(arrow, BorderLayout.EAST);
        return p;
    }

    // ═════════════════════════════════════════════
    //  LAYAR: LOGIN MAHASISWA
    // ═════════════════════════════════════════════
    private JPanel buatLayarLoginMhs() {
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setOpaque(false);

        RoundedPanel kartu = new RoundedPanel(28, C_CARD);
        kartu.setPreferredSize(new Dimension(440, 440));
        kartu.setLayout(new BorderLayout());
        kartu.setBorder(new EmptyBorder(42, 42, 42, 42));
        

        JPanel top = buatHeaderForm("👤", "Login Mahasiswa", "Masukkan nama & NIM terdaftar");
        kartu.add(top, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(28, 0, 0, 0));

        float align = Component.LEFT_ALIGNMENT;

        JLabel lblNama = buatLabelForm("NAMA LENGKAP");
        lblNama.setAlignmentX(align);
        form.add(lblNama);

        form.add(Box.createVerticalStrut(7));

        JTextField txtNama = buatInput("Masukkan nama lengkap...");
        tambahPlaceholder(txtNama, "Masukkan nama lengkap...");
        txtNama.setAlignmentX(align);
        txtNama.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        form.add(txtNama);

        form.add(Box.createVerticalStrut(18));

        // NIM
        JLabel lblNim = buatLabelForm("NIM");
        lblNim.setAlignmentX(align);
        form.add(lblNim);

        form.add(Box.createVerticalStrut(7));

        JTextField txtNim = buatInput("Masukkan NIM...");
        tambahPlaceholder(txtNim, "Masukkan NIM...");
        txtNim.setAlignmentX(align);
        txtNim.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        form.add(txtNim);

        form.add(Box.createVerticalStrut(28));

        BrandButton btnLogin = new BrandButton("Masuk");
        btnLogin.setMaximumSize(new Dimension(400, 46));
        btnLogin.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            String nim  = txtNim.getText().trim();
            if (nama.isEmpty() || nama.equals("Masukkan nama lengkap...") ||
                nim.isEmpty()  || nim.equals("Masukkan NIM...")) {
                JOptionPane.showMessageDialog(this, "Isi nama & NIM terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            manusia user = peminjam.login(nama, nim);
            if (user != null) {
                penggunaAktif = user;
                isAdmin = false;
                masukDashboard();
            } else {
                // Pesan error dihapus sesuai permintaan
            }
        });
        form.add(btnLogin);
        form.add(Box.createVerticalStrut(12));

        JButton btnBack = buatTombolSecondary("← Kembali");
        btnBack.addActionListener(e -> navigasi.show(panelUtama, "PILIH_LOGIN"));
        form.add(btnBack);

        kartu.add(form, BorderLayout.CENTER);
        bg.add(kartu);
        return bg;
    }

    // ═════════════════════════════════════════════
    //  LAYAR: LOGIN ADMIN
    // ═════════════════════════════════════════════
    private JPanel buatLayarLoginAdmin() {
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setOpaque(false);

        RoundedPanel kartu = new RoundedPanel(28, C_CARD);
        kartu.setPreferredSize(new Dimension(440, 440));
        kartu.setLayout(new BorderLayout());
        kartu.setBorder(new EmptyBorder(42, 42, 42, 42));

        JPanel top = buatHeaderForm("🔐", "Login Admin", "Khusus pengelola perpustakaan");
        kartu.add(top, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(28, 0, 0, 0 ));
        form.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = buatLabelForm("USERNAME");
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(lblUser);
        form.add(Box.createVerticalStrut(7));
        JTextField txtUser = buatInput("Masukkan username...");
        txtUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        tambahPlaceholder(txtUser, "Masukkan username...");
        form.add(txtUser);
        form.add(Box.createVerticalStrut(18));

        JLabel lblPass = buatLabelForm("PASSWORD");
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(lblPass);
        form.add(Box.createVerticalStrut(7));
        JPasswordField txtPass = new JPasswordField("Masukkan password...");
        txtPass.setMaximumSize(new Dimension(400, 46));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPass.setBackground(C_INPUT);
        txtPass.setForeground(C_MUTED);
        txtPass.setCaretColor(C_TEKS);
        txtPass.setFont(new Font("Inter", Font.PLAIN, 14));
        txtPass.setEchoChar((char) 0);
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(C_BORDER, 1, true), new EmptyBorder(0, 14, 0, 14)));
        tambahPlaceholderPassword(txtPass, "Masukkan password...");
        form.add(txtPass);
        form.add(Box.createVerticalStrut(24));

        JPanel hint = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        hint.setOpaque(false);
        hint.setMaximumSize(new Dimension(400, 30));
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblHint = new JLabel("Contoh: username = Pali, password = palkel2");
        lblHint.setForeground(C_MUTED);
        lblHint.setFont(new Font("Inter", Font.ITALIC, 10));
        hint.add(lblHint);
        form.add(hint);
        form.add(Box.createVerticalStrut(12));

        BrandButton btnLogin = new BrandButton("Masuk sebagai Admin");
        btnLogin.setMaximumSize(new Dimension(400, 46));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();
            if (adminAuth.ceklogin(user, pass)) {
                adminAktif = adminAuth.getadminAktif();
                isAdmin    = true;
                penggunaAktif = null;
                masukDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah!",
                    "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
        form.add(btnLogin);
        form.add(Box.createVerticalStrut(12));

        JButton btnBack = buatTombolSecondary("← Kembali");
        btnBack.setMaximumSize(new Dimension(400, 46));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> navigasi.show(panelUtama, "PILIH_LOGIN"));
        form.add(btnBack);

        kartu.add(form, BorderLayout.CENTER);
        bg.add(kartu, new GridBagConstraints(0, 0, 1, 1, 1, 1,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
        return bg;
    }

    private void masukDashboard() {
        // Hapus dashboard lama jika ada
        for (int i = 0; i < panelUtama.getComponentCount(); i++) {
            if ("DASHBOARD".equals(panelUtama.getComponent(i).getName())) {
                panelUtama.remove(i); break;
            }
        }
        viewAktif = "BUKU"; // set sebelum buatDashboard agar sidebar terbaca benar
        JPanel db = buatDashboard();
        db.setName("DASHBOARD");
        panelUtama.add(db, "DASHBOARD");
        navigasi.show(panelUtama, "DASHBOARD");
        panelUtama.revalidate();
        panelUtama.repaint();
    }

    // ═════════════════════════════════════════════
    //  DASHBOARD UTAMA
    // ═════════════════════════════════════════════
    private JPanel buatDashboard() {
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setOpaque(false);
        dashboard.add(buatSidebar(), BorderLayout.WEST);

        dashboardLayout  = new CardLayout();
        dashboardContent = new JPanel(dashboardLayout);
        dashboardContent.setOpaque(false);

        dashboardContent.add(buatViewBuku(),    "BUKU");
        dashboardContent.add(buatViewRiwayat(), "RIWAYAT");
        if (isAdmin) {
            dashboardContent.add(buatViewNotifAdmin(),   "NOTIFIKASI");
            dashboardContent.add(buatViewKelolaAdmin(),  "KELOLA");
        }

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setOpaque(false);
        mainArea.add(buatHeader(), BorderLayout.NORTH);
        mainArea.add(dashboardContent, BorderLayout.CENTER);

        dashboard.add(mainArea, BorderLayout.CENTER);
        return dashboard;
    }

    // ─────────────────────────────────────────────
    //  SIDEBAR
    // ─────────────────────────────────────────────
    private JPanel buatSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(C_CARD);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, C_BORDER));

        // Logo
        JPanel logoArea = new JPanel(new FlowLayout(FlowLayout.LEFT, 22, 28));
        logoArea.setOpaque(false);
        JLabel ikoLogo = new JLabel("📚");
        ikoLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        JLabel teksLogo = new JLabel("Perpustakaan");
        teksLogo.setForeground(C_TEKS);
        teksLogo.setFont(new Font("Inter", Font.BOLD, 17));
        logoArea.add(ikoLogo);
        logoArea.add(teksLogo);
        sidebar.add(logoArea, BorderLayout.NORTH);

        // Menu
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setOpaque(false);
        menu.setBorder(new EmptyBorder(0, 14, 14, 14));

        JLabel lblMenu = new JLabel("MENU UTAMA");
        lblMenu.setForeground(C_MUTED);
        lblMenu.setFont(new Font("Inter", Font.BOLD, 9));
        lblMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMenu.setBorder(new EmptyBorder(0, 6, 10, 0));
        menu.add(lblMenu);

        menu.add(buatNavBtn("📖", "Daftar Buku",  "BUKU",        viewAktif));
        menu.add(Box.createVerticalStrut(4));
        menu.add(buatNavBtn("📋", "Riwayat",       "RIWAYAT",     viewAktif));

        if (isAdmin) {
            menu.add(Box.createVerticalStrut(4));
            menu.add(buatNavBtn("🔔", "Log Notifikasi", "NOTIFIKASI", viewAktif));
            menu.add(Box.createVerticalStrut(4));
            menu.add(buatNavBtn("⚙️", "Kelola Buku",    "KELOLA",     viewAktif));
        }
        sidebar.add(menu, BorderLayout.CENTER);

        // Profil bawah
        String nama, sub;
        if (isAdmin) {
            nama = adminAktif.getusername();
            sub  = "Administrator";
        } else {
            nama = penggunaAktif.getnama();
            sub  = "NIM: " + penggunaAktif.getnim();
        }

        RoundedPanel profil = new RoundedPanel(14, C_HILITE);
        profil.setPreferredSize(new Dimension(220, 68));
        profil.setLayout(new BorderLayout(10, 0));
        profil.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel ikoUser = new JLabel(isAdmin ? "🔐" : "👤");
        ikoUser.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        JPanel teksArea = new JPanel(new GridLayout(2, 1, 0, 2));
        teksArea.setOpaque(false);
        JLabel namaLbl = new JLabel(nama.length() > 18 ? nama.substring(0, 18) + "…" : nama);
        namaLbl.setForeground(C_TEKS);
        namaLbl.setFont(new Font("Inter", Font.BOLD, 12));
        JLabel subLbl = new JLabel(sub);
        subLbl.setForeground(C_MUTED);
        subLbl.setFont(new Font("Inter", Font.PLAIN, 10));
        teksArea.add(namaLbl);
        teksArea.add(subLbl);

        profil.add(ikoUser,  BorderLayout.WEST);
        profil.add(teksArea, BorderLayout.CENTER);

        JPanel btm = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 14));
        btm.setOpaque(false);
        btm.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));
        btm.add(profil);
        sidebar.add(btm, BorderLayout.SOUTH);
        sidebarAktif = sidebar; 
        return sidebar;
    }

    private JButton buatNavBtn(String iko, String teks, String cardId, String currentAktif) {
    JButton btn = new JButton(iko + "  " + teks);
    btn.setAlignmentX(Component.LEFT_ALIGNMENT);
    btn.setMaximumSize(new Dimension(222, 42));
    btn.setFocusPainted(false);
    btn.setFont(new Font("Inter", Font.BOLD, 12));
    btn.setHorizontalAlignment(SwingConstants.LEFT);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.setName(cardId);

    boolean aktif = cardId.equals(currentAktif);
    if (aktif) {
        btn.setBackground(C_HILITE);
        btn.setForeground(C_AKSEN);
        btn.setOpaque(true);
        btn.setBorder(new LineBorder(C_AKSEN, 1, true));
    } else {
        btn.setContentAreaFilled(false);
        btn.setForeground(C_MUTED);
        btn.setBorder(new EmptyBorder(0, 8, 0, 8));
    }

    btn.addActionListener(e -> {
        viewAktif = cardId;
        dashboardLayout.show(dashboardContent, cardId);
        refreshSidebar();
    });
    return btn;
}

    private void refreshSidebar() {
    if (sidebarAktif == null) return;
    for (Component c : sidebarAktif.getComponents()) {
        if (c instanceof Container container) {
            for (Component child : container.getComponents()) {
                if (child instanceof JButton btn && child.getName() != null) {
                    String cardId = btn.getName();
                    boolean aktif = cardId.equals(viewAktif);
                    updateButtonStyle(btn, aktif);
                }
            }
        }
    }
    sidebarAktif.repaint();
}

    private void updateButtonStyle(JButton btn, boolean aktif) {
    if (aktif) {
        btn.setBackground(C_HILITE);
        btn.setForeground(C_AKSEN);
        btn.setOpaque(true);
        btn.setBorder(new LineBorder(C_AKSEN, 1, true));
        btn.setContentAreaFilled(true);
    } else {
        btn.setBackground(null);
        btn.setForeground(C_MUTED);
        btn.setOpaque(false);
        btn.setBorder(new EmptyBorder(0, 8, 0, 8));
        btn.setContentAreaFilled(false);
    }
}

    // ─────────────────────────────────────────────
    //  HEADER
    // ─────────────────────────────────────────────
    private JPanel buatHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_CARD);
        header.setPreferredSize(new Dimension(0, 72));
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, C_BORDER),
            new EmptyBorder(0, 28, 0, 36)));

        String nama = isAdmin ? adminAktif.getusername() : penggunaAktif.getnama();
        JLabel sapaan = new JLabel("Halo, " + nama + " 👋");
        sapaan.setForeground(C_TEKS);
        sapaan.setFont(new Font("Inter", Font.BOLD, 20));

        JButton btnLogout = new JButton("Ganti Akun  →");
        btnLogout.setForeground(C_MUTED);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorder(null);
        btnLogout.setFont(new Font("Inter", Font.BOLD, 12));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            penggunaAktif = null; adminAktif = null; isAdmin = false;
            navigasi.show(panelUtama, "PILIH_LOGIN");
        });

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        left.add(sapaan);

        header.add(left,       BorderLayout.WEST);
        header.add(btnLogout,  BorderLayout.EAST);
        return header;
    }

    // ═════════════════════════════════════════════
    //  VIEW: DAFTAR BUKU
    // ═════════════════════════════════════════════
    private JPanel buatViewBuku() {
        JPanel view = new JPanel(new BorderLayout());
        view.setOpaque(false);

        // Filter genre
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        filterBar.setBackground(C_CARD);
        filterBar.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, C_BORDER),
            new EmptyBorder(12, 24, 12, 24)));

        JLabel lblFilter = new JLabel("Genre:");
        lblFilter.setForeground(C_MUTED);
        lblFilter.setFont(new Font("Inter", Font.BOLD, 11));
        filterBar.add(lblFilter);

        String[] genres = {"Semua", "Sastra Perjalanan", "Novel", "Sejarah", "Pengembangan Diri", "Motivasi"};
        for (String g : genres) {
            JButton btnG = buatChip(g, selectedGenre.equals(g));
            btnG.addActionListener(e -> {
                selectedGenre = g;
                for (Component c : filterBar.getComponents())
                    if (c instanceof JButton b) {
                        boolean aktif = b.getText().equals(g);
                        b.setBackground(aktif ? C_AKSEN : C_INPUT);
                        b.setForeground(aktif ? Color.WHITE : C_MUTED);
                    }
                refreshGridBuku();
            });
            filterBar.add(btnG);
        }
        view.add(filterBar, BorderLayout.NORTH);

        // Grid
        JPanel grid = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        grid.setOpaque(false);
        grid.setName("GRID_BUKU");
        grid.setBorder(new EmptyBorder(20, 20, 20, 20));
        isiGridBuku(grid);

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        scroll.getHorizontalScrollBar().setUnitIncrement(20);
        view.add(scroll, BorderLayout.CENTER);
        return view;
    }


    private JButton buatChip(String teks, boolean aktif) {
        JButton btn = new JButton(teks);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Inter", Font.BOLD, 11));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(aktif ? C_AKSEN : C_INPUT);
        btn.setForeground(aktif ? Color.WHITE : C_MUTED);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(aktif ? C_AKSEN : C_BORDER, 1, true),
            new EmptyBorder(4, 12, 4, 12)));
        return btn;
    }

    private void isiGridBuku(JPanel panel) {
        panel.removeAll();
        List<Daftarbuku> filtered = Utama.getDaftarBuku().stream()
            .filter(b -> selectedGenre.equals("Semua") || b.getGenre().equals(selectedGenre))
            .collect(Collectors.toList());
        for (Daftarbuku b : filtered)
            panel.add(new KartuBuku(b));
        panel.revalidate();
        panel.repaint();
    }

    private void refreshGridBuku() {
        JPanel grid = cariByName(dashboardContent, "GRID_BUKU");
        if (grid != null) isiGridBuku(grid);
    }

    // ═════════════════════════════════════════════
    //  VIEW: RIWAYAT
    // ═════════════════════════════════════════════
    private JPanel buatViewRiwayat() {
        JPanel view = new JPanel(new BorderLayout());
        view.setOpaque(false);
        view.setBorder(new EmptyBorder(28, 36, 36, 36));
        view.setName("RIWAYAT_VIEW");

        JLabel title = new JLabel("Riwayat Peminjaman");
        title.setForeground(C_TEKS);
        title.setFont(new Font("Inter", Font.BOLD, 20));
        view.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBorder(new EmptyBorder(18, 0, 0, 0));
        isiRiwayat(list);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        view.add(scroll, BorderLayout.CENTER);
        return view;
    }

    private void isiRiwayat(JPanel panel) {
        panel.removeAll();
        String nama = isAdmin ? "" : penggunaAktif.getnama();
        String nim  = isAdmin ? "" : penggunaAktif.getnim();

        List<PinjamRecord> history = riwayatList.stream()
            .filter(r -> isAdmin || (r.nama.equals(nama) && r.nim.equals(nim)))
            .collect(Collectors.toList());

        if (history.isEmpty()) {
            JPanel emptyState = new JPanel(new GridBagLayout());
            emptyState.setOpaque(false);
            emptyState.setPreferredSize(new Dimension(0, 200));
            JLabel lbl = new JLabel("Belum ada riwayat peminjaman  📭");
            lbl.setForeground(C_MUTED);
            lbl.setFont(new Font("Inter", Font.PLAIN, 15));
            emptyState.add(lbl);
            panel.add(emptyState);
        } else {
            for (PinjamRecord r : history) {
                // Tombol merah HANYA jika admin sudah kirim peringatan
                boolean sudahDiperingatkan = r.peringatanDikirim;

                RoundedPanel item = new RoundedPanel(12, C_CARD);
                item.setLayout(new BorderLayout(12, 0));
                item.setMaximumSize(new Dimension(1000, 76));
                item.setBorder(new EmptyBorder(14, 20, 14, 16));

                // Strip kiri
                Color stripColor = r.dipinjam
                    ? (sudahDiperingatkan ? C_DANGER : C_AKSEN)
                    : (r.terlambat ? C_DANGER : C_SUCCESS);
                JPanel strip = new JPanel();
                strip.setPreferredSize(new Dimension(4, 0));
                strip.setBackground(stripColor);
                item.add(strip, BorderLayout.WEST);

                JLabel bNama = new JLabel(r.buku.getJudul() + "  ·  " + r.buku.getPenulis());
                bNama.setForeground(C_TEKS);
                bNama.setFont(new Font("Inter", Font.BOLD, 13));

                // Status label
                String statusTeks;
                Color  statusColor;
                if (r.dipinjam) {
                    statusTeks  = sudahDiperingatkan
                        ? "⚠  PERINGATAN — Segera kembalikan buku!"
                        : "SEDANG DIPINJAM";
                    statusColor = sudahDiperingatkan ? C_DANGER : C_AKSEN;
                } else {
                    statusTeks  = r.terlambat
                        ? "Sudah dikembalikan — Terlambat"
                        : "Sudah dikembalikan";
                    statusColor = r.terlambat ? C_DANGER : C_SUCCESS;
                }
                JLabel statusLbl = new JLabel(statusTeks);
                statusLbl.setForeground(statusColor);
                statusLbl.setFont(new Font("Inter", Font.BOLD, 10));

                JPanel kiri = new JPanel(new GridLayout(2, 1, 0, 4));
                kiri.setOpaque(false);
                kiri.add(bNama);
                kiri.add(statusLbl);
                item.add(kiri, BorderLayout.CENTER);

                if (r.dipinjam) {
                    // Warna tombol: merah jika sudah diperingatkan, biru jika belum
                    JButton retBtn = new JButton("Kembalikan");
                    retBtn.setForeground(Color.WHITE);
                    retBtn.setFocusPainted(false);
                    retBtn.setFont(new Font("Inter", Font.BOLD, 10));
                    retBtn.setBorder(new EmptyBorder(6, 10, 6, 10));
                    retBtn.setPreferredSize(new Dimension(110, 34));
                    retBtn.setBackground(sudahDiperingatkan ? C_DANGER : C_AKSEN);

                    retBtn.addActionListener(e -> {
                        r.dipinjam  = false;
                        r.terlambat = r.peringatanDikirim; // terlambat jika sudah diperingatkan
                        Utama.kembalikanBuku(r.buku.getJudul());

                        if (r.terlambat) {
                            // Popup khusus jika terlambat
                            JDialog popTerlambat = new JDialog(this, "Pengembalian Terlambat", true);
                            popTerlambat.setSize(380, 350);
                            popTerlambat.setLocationRelativeTo(this);
                            popTerlambat.getContentPane().setBackground(C_CARD);
                            popTerlambat.setLayout(new BorderLayout());

                            JPanel hdrT = new JPanel(new BorderLayout());
                            hdrT.setBackground(C_DANGER);
                            hdrT.setBorder(new EmptyBorder(14, 20, 14, 20));
                            JLabel hTeks = new JLabel("⚠  Buku Terlambat Dikembalikan");
                            hTeks.setForeground(Color.WHITE);
                            hTeks.setFont(new Font("Inter", Font.BOLD, 14));
                            hdrT.add(hTeks);
                            popTerlambat.add(hdrT, BorderLayout.NORTH);

                            JPanel bodyT = new JPanel();
                            bodyT.setOpaque(false);
                            bodyT.setLayout(new BoxLayout(bodyT, BoxLayout.Y_AXIS));
                            bodyT.setBorder(new EmptyBorder(20, 24, 20, 24));

                            JLabel lBuku = new JLabel("Buku: " + r.buku.getJudul());
                            lBuku.setForeground(C_TEKS);
                            lBuku.setFont(new Font("Inter", Font.BOLD, 13));
                            lBuku.setAlignmentX(Component.LEFT_ALIGNMENT);

                            JLabel lInfo = new JLabel("Buku ini dikembalikan setelah peringatan admin.");
                            lInfo.setForeground(C_MUTED);
                            lInfo.setFont(new Font("Inter", Font.PLAIN, 12));
                            lInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

                            JLabel lKet = new JLabel("Status: Dikembalikan — Terlambat");
                            lKet.setForeground(C_DANGER);
                            lKet.setFont(new Font("Inter", Font.BOLD, 11));
                            lKet.setAlignmentX(Component.LEFT_ALIGNMENT);

                            bodyT.add(lBuku);
                            bodyT.add(Box.createVerticalStrut(8));
                            bodyT.add(lInfo);
                            bodyT.add(Box.createVerticalStrut(8));
                            bodyT.add(lKet);
                            popTerlambat.add(bodyT, BorderLayout.CENTER);

                            JPanel btnT = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 10));
                            btnT.setBackground(C_INPUT);
                            btnT.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));
                            JButton ok = new BrandButton("OK, Mengerti");
                            ok.setPreferredSize(new Dimension(120, 36));
                            ok.addActionListener(ev -> popTerlambat.dispose());
                            btnT.add(ok);
                            popTerlambat.add(btnT, BorderLayout.SOUTH);

                            popTerlambat.setVisible(true);
                        }

                        refreshViewRiwayat();
                        refreshNotifPanel();
                        refreshGridBuku();
                        refreshTabelKelola();
                    });
                    item.add(retBtn, BorderLayout.EAST);
                }

                panel.add(item);
                panel.add(Box.createVerticalStrut(8));
            }
        }
        panel.revalidate();
        panel.repaint();
    }
 
    private void refreshViewRiwayat() {
        JPanel view = cariByName(dashboardContent, "RIWAYAT_VIEW");
        if (view != null) {
            JScrollPane sc = (JScrollPane) view.getComponent(1);
            isiRiwayat((JPanel) sc.getViewport().getView());
        }
    }

    // ═════════════════════════════════════════════
    //  VIEW: NOTIFIKASI ADMIN
    // ═════════════════════════════════════════════
    private JPanel buatViewNotifAdmin() {
        JPanel view = new JPanel(new BorderLayout());
        view.setOpaque(false);
        view.setBorder(new EmptyBorder(28, 36, 36, 36));
        view.setName("NOTIF_VIEW");
 
        JLabel title = new JLabel("Log Aktivitas Peminjaman");
        title.setForeground(C_TEKS);
        title.setFont(new Font("Inter", Font.BOLD, 20));
        view.add(title, BorderLayout.NORTH);
 
        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setName("NOTIF_LIST");
        list.setBorder(new EmptyBorder(18, 0, 0, 0));
        refreshNotifAdmin(list);
 
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        view.add(scroll, BorderLayout.CENTER);
        return view;
    }
 
    private void refreshNotifAdmin(JPanel panel) {
        panel.removeAll();
        if (riwayatList.isEmpty()) {
            JLabel empty = new JLabel("Belum ada aktivitas  📭");
            empty.setForeground(C_MUTED);
            empty.setFont(new Font("Inter", Font.PLAIN, 14));
            panel.add(empty);
        }
        for (PinjamRecord r : riwayatList) {
            boolean lewat = r.dipinjam && r.sudahMelebihiBatas();
            RoundedPanel card = new RoundedPanel(10, C_CARD);
            card.setLayout(new BorderLayout(10, 0));
            card.setMaximumSize(new Dimension(1000, 62));
            card.setBorder(new EmptyBorder(10, 16, 10, 16));

            JPanel dot = new JPanel();
            dot.setPreferredSize(new Dimension(4, 0));
            dot.setBackground(r.dipinjam ? (lewat ? C_DANGER : C_AKSEN) : C_SUCCESS);
            card.add(dot, BorderLayout.WEST);

            String msg = "<html><b>" + r.nama + "</b> <span style='color:#6b7280'>(" + r.nim + ")</span> "
                + (r.dipinjam ? "meminjam" : "mengembalikan")
                + (lewat ? " <span style='color:#dc2626'>[MELEWATI BATAS]</span>" : "")
                + " &nbsp;<b>" + r.buku.getJudul() + "</b></html>";
            JLabel lbl = new JLabel(msg);
            lbl.setForeground(C_TEKS);
            lbl.setFont(new Font("Inter", Font.PLAIN, 13));
            card.add(lbl, BorderLayout.CENTER);

            // Tombol kirim peringatan jika masih dipinjam
            if (r.dipinjam) {
                String labelBtn = r.peringatanDikirim
                    ? "🔔 Peringatan Terkirim"
                    : "🔔 Kirim Peringatan";
                Color  warnaBg  = r.peringatanDikirim ? C_MUTED : C_WARN_FG;

                JButton btnWarn = new JButton(labelBtn);
                btnWarn.setBackground(warnaBg);
                btnWarn.setForeground(Color.WHITE);
                btnWarn.setFocusPainted(false);
                btnWarn.setFont(new Font("Inter", Font.BOLD, 10));
                btnWarn.setBorder(new EmptyBorder(4, 10, 4, 10));
                btnWarn.setEnabled(!r.peringatanDikirim); // nonaktif jika sudah pernah dikirim

                btnWarn.addActionListener(e -> {
                    r.peringatanDikirim = true; // tandai sudah diperingatkan
                    JOptionPane.showMessageDialog(this,
                        "Peringatan dikirim ke:\n" + r.nama + " (" + r.nim + ")\n\n" +
                        "Buku \"" + r.buku.getJudul() + "\" harus segera dikembalikan!\n" +
                        "Tombol kembalikan mahasiswa kini berwarna merah.",
                        "Peringatan Terkirim ✅", JOptionPane.WARNING_MESSAGE);
                    // Refresh notif (perbarui warna tombol) dan riwayat mahasiswa
                    refreshNotifPanel();
                    refreshViewRiwayat();
                });
                card.add(btnWarn, BorderLayout.EAST);
            }

            panel.add(card);
            panel.add(Box.createVerticalStrut(6));
        }
        panel.revalidate();
        panel.repaint();
    }
 
    private void refreshNotifPanel() {
        JPanel list = cariByName(dashboardContent, "NOTIF_LIST");
        if (list != null) refreshNotifAdmin(list);
    }

    // ═════════════════════════════════════════════
    //  VIEW: KELOLA BUKU (ADMIN)
    // ═════════════════════════════════════════════
    private JPanel buatViewKelolaAdmin() {
        JPanel view = new JPanel(new BorderLayout(0, 0));
        view.setOpaque(false);
        view.setBorder(new EmptyBorder(28, 36, 36, 36));
        view.setName("KELOLA_VIEW");

        // Header kelola
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel title = new JLabel("Manajemen Koleksi Buku");
        title.setForeground(C_TEKS);
        title.setFont(new Font("Inter", Font.BOLD, 20));
        top.add(title, BorderLayout.WEST);

        BrandButton btnTambah = new BrandButton("＋  Tambah Buku");
        btnTambah.setPreferredSize(new Dimension(150, 38));
        btnTambah.addActionListener(e -> tampilDialogTambahBuku());
        top.add(btnTambah, BorderLayout.EAST);
        view.add(top, BorderLayout.NORTH);

        // Tabel
        String[] cols = {"Judul", "Penulis", "Genre", "Penerbit", "Tahun", "Stok", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        isiTabelBuku(model);

        JTable table = new JTable(model);
        table.setBackground(C_CARD);
        table.setForeground(C_TEKS);
        table.setGridColor(C_BORDER);
        table.setRowHeight(44);
        table.setSelectionBackground(C_HILITE);
        table.setShowVerticalLines(false);
        table.setFont(new Font("Inter", Font.PLAIN, 13));
        table.getTableHeader().setBackground(C_INPUT);
        table.getTableHeader().setForeground(C_TEKS);
        table.getTableHeader().setFont(new Font("Inter", Font.BOLD, 12));
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, C_BORDER));

        // Render status kolom dengan warna
        table.getColumnModel().getColumn(6).setCellRenderer((tbl, value, sel, foc, row, col) -> {
            JLabel lbl = new JLabel(value != null ? value.toString() : "");
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setFont(new Font("Inter", Font.BOLD, 11));
            boolean tersedia = "Tersedia".equals(value);
            lbl.setForeground(tersedia ? C_SUCCESS : C_DANGER);
            lbl.setBackground(sel ? C_HILITE : C_CARD);
            return lbl;
        });

        table.setName("TABEL_BUKU");

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(C_BORDER));
        scroll.getViewport().setBackground(C_CARD);
        view.add(scroll, BorderLayout.CENTER);

        // Panel aksi di bawah tabel
        JPanel aksiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        aksiPanel.setOpaque(false);

        BrandButton btnEdit = new BrandButton("✏️  Edit Ketersediaan");
        btnEdit.setPreferredSize(new Dimension(185, 38));
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih buku dari tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String judul = model.getValueAt(row, 0).toString();
            tampilDialogEditBuku(judul, model, row);
        });

        JButton btnHapus = buatTombolSecondary("🗑️  Hapus Buku");
        btnHapus.setPreferredSize(new Dimension(140, 38));
        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih buku terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String judul = model.getValueAt(row, 0).toString();
            int konfirm = JOptionPane.showConfirmDialog(this,
                "Hapus buku \"" + judul + "\" dari daftar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirm == JOptionPane.YES_OPTION) {
                Utama.getDaftarBuku().removeIf(b -> b.getJudul().equals(judul));
                isiTabelBuku(model);
                refreshGridBuku();
            }
        });

        aksiPanel.add(btnEdit);
        aksiPanel.add(btnHapus);
        view.add(aksiPanel, BorderLayout.SOUTH);

        return view;
    }

    private void isiTabelBuku(DefaultTableModel model) {
        model.setRowCount(0);
        for (Daftarbuku b : Utama.getDaftarBuku()) {
            boolean tersedia = Utama.isTersedia(b.getJudul());
            int stokSisa     = Utama.getStok(b.getJudul()); // stok real-time
            model.addRow(new Object[]{
                b.getJudul(), b.getPenulis(), b.getGenre(),
                b.getPenerbit(), b.getTahunterbit(), stokSisa, // ← stokSisa bukan b.getJumlah()
                tersedia ? "Tersedia" : "Kosong"
            });
        }
    }

    private void refreshTabelKelola() {
        JPanel kv = cariByName(dashboardContent, "KELOLA_VIEW");
         if (kv == null) return;
        JScrollPane sc  = (JScrollPane) kv.getComponent(1);
        JTable tbl = (JTable) sc.getViewport().getView();
        isiTabelBuku((DefaultTableModel) tbl.getModel());
    }

    // ─────────────────────────────────────────────
    //  DIALOG: TAMBAH BUKU
    // ─────────────────────────────────────────────
    private void tampilDialogTambahBuku() {
        JDialog d = new JDialog(this, "Tambah Buku Baru", true);
        d.setSize(460, 560);
        d.setLocationRelativeTo(this);
        d.getContentPane().setBackground(C_CARD);
        d.setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_AKSEN);
        header.setBorder(new EmptyBorder(18, 24, 18, 24));
        JLabel judul = new JLabel("＋  Tambah Buku Baru");
        judul.setForeground(Color.WHITE);
        judul.setFont(new Font("Inter", Font.BOLD, 16));
        header.add(judul);
        d.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(new EmptyBorder(24, 28, 20, 28));

        JTextField tJ  = buatInputDialog("Judul buku");
        JTextField tP  = buatInputDialog("Nama penulis");
        JTextField tG  = buatInputDialog("Genre / kategori");
        JTextField tPn = buatInputDialog("Nama penerbit");
        JTextField tTh = buatInputDialog("Tahun terbit (angka)");
        JTextField tS  = buatInputDialog("Jumlah stok (angka)");

        String[]   labels = {"JUDUL", "PENULIS", "GENRE", "PENERBIT", "TAHUN TERBIT", "STOK"};
        JTextField[] inputs = {tJ, tP, tG, tPn, tTh, tS};
        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setForeground(C_MUTED);
            lbl.setFont(new Font("Inter", Font.BOLD, 10));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            form.add(lbl);
            form.add(Box.createVerticalStrut(6));
            form.add(inputs[i]);
            if (i < labels.length - 1) form.add(Box.createVerticalStrut(14));
        }

        // Status awal
        form.add(Box.createVerticalStrut(14));
        JLabel lblStatus = new JLabel("STATUS AWAL");
        lblStatus.setForeground(C_MUTED);
        lblStatus.setFont(new Font("Inter", Font.BOLD, 10));
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(lblStatus);
        form.add(Box.createVerticalStrut(7));

        JPanel radioRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        radioRow.setOpaque(false);
        radioRow.setMaximumSize(new Dimension(400, 30));
        ButtonGroup bg = new ButtonGroup();
        JRadioButton rbTersedia = new JRadioButton("Tersedia"); rbTersedia.setSelected(true);
        JRadioButton rbKosong   = new JRadioButton("Kosong");
        rbTersedia.setFont(new Font("Inter", Font.PLAIN, 13)); rbTersedia.setOpaque(false);
        rbKosong.setFont(new Font("Inter", Font.PLAIN, 13));   rbKosong.setOpaque(false);
        bg.add(rbTersedia); bg.add(rbKosong);
        radioRow.add(rbTersedia); radioRow.add(rbKosong);
        form.add(radioRow);

        d.add(form, BorderLayout.CENTER);

        // Tombol bawah
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        btnRow.setBackground(C_INPUT);
        btnRow.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));

        JButton batal = buatTombolSecondary("Batal");
        batal.setPreferredSize(new Dimension(90, 38));
        batal.addActionListener(e -> d.dispose());

        BrandButton simpan = new BrandButton("Simpan");
        simpan.setPreferredSize(new Dimension(110, 38));
        simpan.addActionListener(e -> {
            try {
                String j  = tJ.getText().trim(),  p  = tP.getText().trim(),
                       g  = tG.getText().trim(),  pn = tPn.getText().trim();
                int tahun = Integer.parseInt(tTh.getText().trim());
                int stok  = Integer.parseInt(tS.getText().trim());
                if (j.isEmpty() || p.isEmpty() || g.isEmpty()) {
                    JOptionPane.showMessageDialog(d, "Judul, penulis & genre wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Daftarbuku baru = new Daftarbuku(j, p, g, pn, tahun, stok, "covers/default.jpg",
                    "Deskripsi belum tersedia untuk buku ini.");
                Utama.getDaftarBuku().add(baru);              // ← tambahkan ke daftar buku
                Utama.daftarkanKetersediaan(j, p, stok);      // stok > 0 → otomatis Tersedia
                refreshGridBuku();
                refreshTabelKelola();
                JOptionPane.showMessageDialog(d, "Buku \"" + j + "\" berhasil ditambahkan!");
                d.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(d, "Tahun dan stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
 
        btnRow.add(batal);
        btnRow.add(simpan);
        d.add(btnRow, BorderLayout.SOUTH);
        d.setVisible(true);
    }
    
    // ─────────────────────────────────────────────
    //  DIALOG: EDIT KETERSEDIAAN & STATUS PINJAM
    // ─────────────────────────────────────────────
    private void tampilDialogEditBuku(String judul, DefaultTableModel model, int row) {
        Daftarbuku buku = Utama.getDaftarBuku().stream()
            .filter(b -> b.getJudul().equals(judul)).findFirst().orElse(null);
        if (buku == null) return;
 
        Ketersediaan ket      = Utama.getKetersediaan(judul);
        boolean statusSekarang = ket != null && ket.getTersedia();
        int     stokSekarang   = ket != null ? ket.getStok() : 0;
 
        JDialog d = new JDialog(this, "Edit Buku", true);
        d.setSize(440, 440);
        d.setLocationRelativeTo(this);
        d.getContentPane().setBackground(C_CARD);
        d.setLayout(new BorderLayout());
 
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_AKSEN);
        header.setBorder(new EmptyBorder(18, 24, 18, 24));
        JLabel hJudul = new JLabel("✏️  Edit Buku");
        hJudul.setForeground(Color.WHITE);
        hJudul.setFont(new Font("Inter", Font.BOLD, 16));
        header.add(hJudul);
        d.add(header, BorderLayout.NORTH);
 
        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(24, 28, 20, 28));
 
        // Info buku (read-only)
        RoundedPanel infoBuku = new RoundedPanel(10, C_INPUT);
        infoBuku.setLayout(new GridLayout(4, 1, 0, 4));
        infoBuku.setBorder(new EmptyBorder(12, 16, 12, 16));
        infoBuku.setMaximumSize(new Dimension(400, 100));
        infoBuku.add(infoRow("Judul",       buku.getJudul()));
        infoBuku.add(infoRow("Penulis",     buku.getPenulis()));
        infoBuku.add(infoRow("Genre",       buku.getGenre()));
        infoBuku.add(infoRow("Stok saat ini", stokSekarang + " eksemplar"));
        body.add(infoBuku);
        body.add(Box.createVerticalStrut(22));
 
        // ── Edit stok manual ──
        JLabel lblStok = new JLabel("EDIT STOK BUKU");
        lblStok.setForeground(C_MUTED);
        lblStok.setFont(new Font("Inter", Font.BOLD, 10));
        lblStok.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(lblStok);
        body.add(Box.createVerticalStrut(8));
 
        JPanel stokRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        stokRow.setOpaque(false);
        stokRow.setMaximumSize(new Dimension(400, 40));
 
        JButton btnMinus = new JButton("−");
        btnMinus.setFont(new Font("Inter", Font.BOLD, 16));
        btnMinus.setPreferredSize(new Dimension(36, 36));
        btnMinus.setFocusPainted(false);
        btnMinus.setBackground(C_INPUT);
        btnMinus.setBorder(new LineBorder(C_BORDER, 1, true));
 
        JLabel lblStokVal = new JLabel(String.valueOf(stokSekarang));
        lblStokVal.setFont(new Font("Inter", Font.BOLD, 16));
        lblStokVal.setForeground(C_TEKS);
        lblStokVal.setPreferredSize(new Dimension(40, 36));
        lblStokVal.setHorizontalAlignment(SwingConstants.CENTER);
 
        JButton btnPlus = new JButton("＋");
        btnPlus.setFont(new Font("Inter", Font.BOLD, 14));
        btnPlus.setPreferredSize(new Dimension(36, 36));
        btnPlus.setFocusPainted(false);
        btnPlus.setBackground(C_INPUT);
        btnPlus.setBorder(new LineBorder(C_BORDER, 1, true));
 
        // Simpan stok sementara di array agar bisa diakses lambda
        int[] stokTemp = {stokSekarang};

 
        stokRow.add(btnMinus);
        stokRow.add(lblStokVal);
        stokRow.add(btnPlus);
        body.add(stokRow);
        body.add(Box.createVerticalStrut(18));
 
        // ── Edit ketersediaan ──
        JLabel lblKet = new JLabel("KETERSEDIAAN BUKU");
        lblKet.setForeground(C_MUTED);
        lblKet.setFont(new Font("Inter", Font.BOLD, 10));
        lblKet.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(lblKet);
        body.add(Box.createVerticalStrut(8));
 
        ButtonGroup bgKet = new ButtonGroup();
        JRadioButton rbTersedia = new JRadioButton("✅  Tersedia — Buku dapat dipinjam");
        JRadioButton rbKosong   = new JRadioButton("❌  Kosong — Semua stok habis dipinjam");
        rbTersedia.setSelected(statusSekarang);
        rbKosong.setSelected(!statusSekarang);
        bgKet.add(rbTersedia);
        bgKet.add(rbKosong);
        btnMinus.addActionListener(e -> {
            if (stokTemp[0] > 0) {
                stokTemp[0]--;
                lblStokVal.setText(String.valueOf(stokTemp[0]));
                // Update radio otomatis
                rbTersedia.setSelected(stokTemp[0] > 0);
                rbKosong.setSelected(stokTemp[0] == 0);
            }
        });
        btnPlus.addActionListener(e -> {
            stokTemp[0]++;
            lblStokVal.setText(String.valueOf(stokTemp[0]));
            rbTersedia.setSelected(true);
            rbKosong.setSelected(false);
        });

        for (JRadioButton rb : new JRadioButton[]{rbTersedia, rbKosong}) {
            rb.setOpaque(false);
            rb.setFont(new Font("Inter", Font.PLAIN, 13));
            rb.setForeground(C_TEKS);
            rb.setAlignmentX(Component.LEFT_ALIGNMENT);
            rb.setMaximumSize(new Dimension(400, 32));
            // bgKet.add(rb) DIHAPUS — sudah ditambahkan sebelum loop
            body.add(rb);
            body.add(Box.createVerticalStrut(6));
        }
 
        body.add(Box.createVerticalStrut(18));
 
        // ── Edit status pinjam (paksa kembalikan semua) ──
        JLabel lblPinjam = new JLabel("MANAJEMEN PEMINJAMAN");
        lblPinjam.setForeground(C_MUTED);
        lblPinjam.setFont(new Font("Inter", Font.BOLD, 10));
        lblPinjam.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(lblPinjam);
        body.add(Box.createVerticalStrut(8));
 
        long jmlPinjam = riwayatList.stream()
            .filter(r -> r.buku.getJudul().equals(judul) && r.dipinjam).count();
 
        RoundedPanel infoPinjam = new RoundedPanel(8, jmlPinjam > 0 ? C_WARN_BG : C_HILITE);
        infoPinjam.setLayout(new BorderLayout(10, 0));
        infoPinjam.setBorder(new EmptyBorder(10, 14, 10, 14));
        infoPinjam.setMaximumSize(new Dimension(400, 50));
 
        JLabel lblPinjamInfo = new JLabel("<html><b>" + jmlPinjam + " peminjam aktif</b> untuk buku ini</html>");
        lblPinjamInfo.setForeground(jmlPinjam > 0 ? C_WARN_FG : C_SUCCESS);
        lblPinjamInfo.setFont(new Font("Inter", Font.PLAIN, 12));
        infoPinjam.add(lblPinjamInfo, BorderLayout.CENTER);
 
        if (jmlPinjam > 0) {
            JButton btnPaksa = new JButton("Kembalikan Semua");
            btnPaksa.setFont(new Font("Inter", Font.BOLD, 10));
            btnPaksa.setBackground(C_WARN_FG);
            btnPaksa.setForeground(Color.WHITE);
            btnPaksa.setFocusPainted(false);
            btnPaksa.setBorder(new EmptyBorder(4, 10, 4, 10));
            btnPaksa.addActionListener(e -> {
                riwayatList.stream()
                    .filter(r -> r.buku.getJudul().equals(judul) && r.dipinjam)
                    .forEach(r -> {
                        r.dipinjam = false;
                        Utama.kembalikanBuku(judul); // stok +1 tiap pengembalian
                    });
                JOptionPane.showMessageDialog(d,
                    "Semua peminjaman buku \"" + judul + "\" telah dikembalikan.\n" +
                    "Stok kini: " + Utama.getStok(judul));
                d.dispose();
                refreshViewRiwayat();
                refreshNotifPanel();
                refreshGridBuku();
                refreshTabelKelola();
            });
            infoPinjam.add(btnPaksa, BorderLayout.EAST);
        }
        body.add(infoPinjam);
 
        d.add(body, BorderLayout.CENTER);
 
        // Tombol bawah
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        btnRow.setBackground(C_INPUT);
        btnRow.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));
 
        JButton batal = buatTombolSecondary("Batal");
        batal.setPreferredSize(new Dimension(90, 38));
        batal.addActionListener(e -> d.dispose());
 
        BrandButton simpan = new BrandButton("Simpan Perubahan");
        simpan.setPreferredSize(new Dimension(160, 38));
        simpan.addActionListener(e -> {
            // Jika stok > 0 paksa tersedia, jika 0 paksa kosong (override radio)
            boolean tersediaBaru = stokTemp[0] > 0 ? rbTersedia.isSelected() : false;
            Utama.setStokBuku(judul, stokTemp[0]);
            // Jika admin paksa kosong meski stok > 0, tandai manual
            if (stokTemp[0] > 0 && rbKosong.isSelected())
                Utama.getKetersediaan(judul).setTersedia(false);
 
            model.setValueAt(stokTemp[0],            row, 5);
            model.setValueAt(tersediaBaru ? "Tersedia" : "Kosong", row, 6);
            refreshGridBuku();
            JOptionPane.showMessageDialog(d,
                "Buku \"" + judul + "\" diperbarui.\n" +
                "Stok: " + stokTemp[0] + "  |  Status: " + (tersediaBaru ? "Tersedia" : "Kosong"));
            d.dispose();
        });
 
        btnRow.add(batal);
        btnRow.add(simpan);
        d.add(btnRow, BorderLayout.SOUTH);
        d.setVisible(true);
    }
 
    private JLabel infoRow(String label, String nilai) {
        JLabel l = new JLabel("<html><span style='color:#6b7280'>" + label + ":  </span><b>" + nilai + "</b></html>");
        l.setFont(new Font("Inter", Font.PLAIN, 12));
        return l;
    }

    // ═════════════════════════════════════════════
    //  INNER CLASS: KARTU BUKU
    // ═════════════════════════════════════════════
    private class KartuBuku extends JPanel {
        KartuBuku(Daftarbuku buku) {
            setPreferredSize(new Dimension(205, 318));
            setBackground(C_CARD);
            setLayout(new BorderLayout());
            setBorder(new LineBorder(C_BORDER, 1, true));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            boolean tersedia = Utama.isTersedia(buku.getJudul());
            int stokSisa = Utama.getStok(buku.getJudul());

            // Cover
            JPanel cover = new JPanel(new GridBagLayout());
            cover.setPreferredSize(new Dimension(0, 152));
            cover.setBackground(C_INPUT);

            java.io.File fileCover = new java.io.File(buku.getImagePath());
            if (fileCover.exists()) {
                try {
                    java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(fileCover);
                    Image sc = img.getScaledInstance(205, 152, Image.SCALE_SMOOTH);
                    cover.add(new JLabel(new ImageIcon(sc)));
                } catch (IOException ex) { cover.add(fallback(buku)); }
            } else { cover.add(fallback(buku)); }

            // Badge status di atas cover
            JPanel coverWrap = new JPanel(new BorderLayout());
            coverWrap.setOpaque(false);
            coverWrap.setPreferredSize(new Dimension(0, 160));
            coverWrap.add(cover, BorderLayout.CENTER);

            JLabel badge = new JLabel(tersedia ? "  Tersedia  " : "  Kosong  ");
            badge.setFont(new Font("Inter", Font.BOLD, 9));
            badge.setForeground(Color.WHITE);
            badge.setOpaque(true);
            badge.setBackground(tersedia ? C_SUCCESS : C_DANGER);
            badge.setHorizontalAlignment(SwingConstants.CENTER);
            badge.setPreferredSize(new Dimension(0, 20));
            coverWrap.add(badge, BorderLayout.SOUTH);

            // Info
            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setOpaque(false);
            info.setBorder(new EmptyBorder(10, 12, 12, 12));

            JLabel lJudul = new JLabel(buku.getJudul().length() > 22 ?
                buku.getJudul().substring(0, 22) + "…" : buku.getJudul());
            lJudul.setForeground(C_TEKS);
            lJudul.setFont(new Font("Inter", Font.BOLD, 13));

            JLabel lPenulis = new JLabel(buku.getPenulis());
            lPenulis.setForeground(C_MUTED);
            lPenulis.setFont(new Font("Inter", Font.PLAIN, 11));

            JLabel lGenre = new JLabel(buku.getGenre());
            lGenre.setForeground(C_AKSEN);
            lGenre.setFont(new Font("Inter", Font.BOLD, 9));

            info.add(lJudul);
            info.add(Box.createVerticalStrut(3));
            info.add(lPenulis);
            info.add(Box.createVerticalStrut(5));
            info.add(lGenre);
            info.add(Box.createVerticalStrut(10));

            if (!isAdmin) {
                BrandButton btnPinjam = new BrandButton(tersedia ? "Pinjam" : "Tidak Tersedia");
                btnPinjam.setEnabled(tersedia);
                btnPinjam.addActionListener(e -> {
                    // Cek apakah mahasiswa ini masih meminjam buku yang sama
                    boolean sudahPinjam = riwayatList.stream().anyMatch(r ->
                        r.nama.equals(penggunaAktif.getnama()) &&
                        r.nim.equals(penggunaAktif.getnim()) &&
                        r.buku.getJudul().equals(buku.getJudul()) &&
                        r.dipinjam);
                    if (sudahPinjam) {
                        JOptionPane.showMessageDialog(this,
                            "Kamu masih meminjam buku ini!\nKembalikan dulu sebelum meminjam lagi.",
                            "Tidak Bisa Meminjam", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    boolean berhasil = Utama.pinjamBuku(buku.getJudul());
                    if (berhasil) {
                        riwayatList.add(new PinjamRecord(
                            penggunaAktif.getnama(), penggunaAktif.getnim(), buku, "2026-05-09"));
                        JOptionPane.showMessageDialog(this,
                            "\"" + buku.getJudul() + "\" berhasil dipinjam!\n" +
                            "Stok tersisa: " + Utama.getStok(buku.getJudul()));
                        refreshGridBuku();
                        refreshViewRiwayat();
                        refreshNotifPanel();
                        refreshTabelKelola();
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Maaf, stok buku ini sudah habis!", "Gagal", JOptionPane.WARNING_MESSAGE);
                    }
                });
                info.add(btnPinjam);
            } else {
                JLabel stok = new JLabel("Stok: " + stokSisa + " eks.");
                stok.setForeground(stokSisa > 0 ? C_SUCCESS : C_DANGER);
                stok.setFont(new Font("Inter", Font.BOLD, 11));
                info.add(stok);
            }

            add(coverWrap, BorderLayout.NORTH);
            add(info,      BorderLayout.CENTER);

            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    tampilPopupBuku(buku, Utama.isTersedia(buku.getJudul()));
                }
                @Override public void mouseEntered(MouseEvent e) {
                    setBorder(new LineBorder(C_AKSEN, 2, true));
                }
                @Override public void mouseExited(MouseEvent e) {
                    setBorder(new LineBorder(C_BORDER, 1, true));
                }
            });
        }

        private JLabel fallback(Daftarbuku b) {
            JLabel l = new JLabel("<html><center><span style='font-size:32px'>"
                + b.getJudul().substring(0, 1)
                + "</span><br><small>" + b.getGenre() + "</small></center></html>");
            l.setForeground(C_BORDER);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            return l;
        }
    }

    // ═════════════════════════════════════════════
    //  POPUP DETAIL BUKU
    // ═════════════════════════════════════════════
    private void tampilPopupBuku(Daftarbuku buku, boolean tersedia) {
        JDialog popup = new JDialog(this, buku.getJudul(), true);
        popup.setSize(510, 560);
        popup.setLocationRelativeTo(this);
        popup.getContentPane().setBackground(C_CARD);
        popup.setLayout(new BorderLayout());

        // Header popup
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(tersedia ? C_AKSEN : C_MUTED);
        hdr.setBorder(new EmptyBorder(14, 20, 14, 20));
        JLabel hTitle = new JLabel("📖  Detail Buku");
        hTitle.setForeground(Color.WHITE);
        hTitle.setFont(new Font("Inter", Font.BOLD, 15));
        hdr.add(hTitle);
        popup.add(hdr, BorderLayout.NORTH);

        JPanel isi = new JPanel(new BorderLayout(0, 14));
        isi.setBackground(C_CARD);
        isi.setBorder(new EmptyBorder(22, 24, 18, 24));

        // Cover
        JPanel coverWrap = new JPanel(new BorderLayout());
        coverWrap.setBackground(C_INPUT);
        coverWrap.setPreferredSize(new Dimension(0, 190));
        java.io.File f = new java.io.File(buku.getImagePath());
        if (f.exists()) {
            try {
                java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(f);
                Image sc = img.getScaledInstance(135, 190, Image.SCALE_SMOOTH);
                JLabel lc = new JLabel(new ImageIcon(sc));
                lc.setHorizontalAlignment(SwingConstants.CENTER);
                coverWrap.add(lc, BorderLayout.CENTER);
            } catch (IOException ex) { coverWrap.add(new JLabel("(cover)", SwingConstants.CENTER)); }
        } else {
            JLabel ph = new JLabel("<html><center><big><b>" + buku.getJudul().substring(0, 1) +
                "</b></big><br>" + buku.getGenre() + "</center></html>", SwingConstants.CENTER);
            ph.setForeground(C_MUTED);
            coverWrap.add(ph, BorderLayout.CENTER);
        }

        // Detail rows — stok diambil real-time dari Utama
        int stokPopup = Utama.getStok(buku.getJudul());
        JPanel detail = new JPanel(new GridLayout(5, 1, 0, 6));
        detail.setBackground(C_CARD);
        detail.add(infoRow(" Judul",    buku.getJudul()));
        detail.add(infoRow(" Penulis",  buku.getPenulis()));
        detail.add(infoRow(" Genre",    buku.getGenre()));
        detail.add(infoRow(" Penerbit", buku.getPenerbit() + "  (" + buku.getTahunterbit() + ")"));
        detail.add(infoRow(" Stok",     stokPopup + " eksemplar"));  // ← real-time

        // Deskripsi
        JTextArea desk = new JTextArea(buku.getDeskripsi());
        desk.setWrapStyleWord(true); desk.setLineWrap(true);
        desk.setEditable(false); 
        desk.setFocusable(false);
        desk.setBackground(C_INPUT);
        desk.setForeground(C_TEKS);
        desk.setFont(new Font("Inter", Font.PLAIN, 12));
        desk.setBorder(new EmptyBorder(10, 12, 10, 12));
        JScrollPane sDesk = new JScrollPane(desk);
        sDesk.setBorder(new LineBorder(C_BORDER));
        sDesk.setPreferredSize(new Dimension(0, 85));

        // Tombol bawah
        JButton batal = buatTombolSecondary("Tutup");
        batal.setPreferredSize(new Dimension(90, 38));
        batal.addActionListener(e -> popup.dispose());

        BrandButton pinjam = new BrandButton(tersedia ? "Pinjam Buku" : "Tidak Tersedia");
        pinjam.setEnabled(tersedia && !isAdmin);
        pinjam.setPreferredSize(new Dimension(140, 38));
        pinjam.addActionListener(e -> {
            // Cek duplikat pinjam
            boolean sudahPinjam = !isAdmin && riwayatList.stream().anyMatch(r ->
                r.nama.equals(penggunaAktif.getnama()) &&
                r.nim.equals(penggunaAktif.getnim()) &&
                r.buku.getJudul().equals(buku.getJudul()) &&
                r.dipinjam);
            if (sudahPinjam) {
                JOptionPane.showMessageDialog(popup,
                    "Kamu masih meminjam buku ini!\nKembalikan dulu sebelum meminjam lagi.",
                    "Tidak Bisa Meminjam", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean berhasil = Utama.pinjamBuku(buku.getJudul());
            if (berhasil) {
                riwayatList.add(new PinjamRecord(
                    penggunaAktif.getnama(), penggunaAktif.getnim(), buku, "2026-05-09"));
                JOptionPane.showMessageDialog(popup,
                    "\"" + buku.getJudul() + "\" berhasil dipinjam!\n" +
                    "Stok tersisa: " + Utama.getStok(buku.getJudul()));
                popup.dispose();
                refreshGridBuku();
                refreshViewRiwayat();
                refreshNotifPanel();
                refreshTabelKelola();
            } else {
                JOptionPane.showMessageDialog(popup,
                    "Maaf, stok buku ini sudah habis!", "Gagal", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setOpaque(false);
        btnRow.add(batal); btnRow.add(pinjam);

        JPanel bawah = new JPanel(new BorderLayout(0, 10));
        bawah.setBackground(C_CARD);
        bawah.add(sDesk,   BorderLayout.CENTER);
        bawah.add(btnRow,  BorderLayout.SOUTH);

        isi.add(coverWrap, BorderLayout.NORTH);
        isi.add(detail,    BorderLayout.CENTER);
        isi.add(bawah,     BorderLayout.SOUTH);
        popup.add(isi);
        popup.setVisible(true);
    }

    // ═════════════════════════════════════════════
    //  HELPER UI
    // ═════════════════════════════════════════════
    private static void tambahPlaceholder(JTextField f, String ph) {
        f.setText(ph); f.setForeground(C_MUTED);
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (f.getText().equals(ph)) { f.setText(""); f.setForeground(C_TEKS); }
            }
            @Override public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setText(ph); f.setForeground(C_MUTED); }
            }
        });
    }

    private static void tambahPlaceholderPassword(JPasswordField f, String ph) {
        f.setText(ph); f.setForeground(C_MUTED); f.setEchoChar((char) 0);
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (new String(f.getPassword()).equals(ph)) {
                    f.setText(""); f.setForeground(C_TEKS); f.setEchoChar('•');
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (f.getPassword().length == 0) {
                    f.setText(ph); f.setForeground(C_MUTED); f.setEchoChar((char) 0);
                }
            }
        });
    }

    private static JTextField buatInput(String hint) {
        JTextField f = new JTextField(hint);
        f.setMaximumSize(new Dimension(400, 45));
        f.setBackground(C_INPUT); f.setForeground(C_MUTED);
        f.setCaretColor(C_TEKS);
        f.setFont(new Font("Inter", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(C_BORDER, 1, true), new EmptyBorder(0, 14, 0, 14)));
        return f;
    }

    private static JTextField buatInputDialog(String hint) {
        JTextField f = new JTextField();
        f.setMaximumSize(new Dimension(400, 42));
        f.setBackground(C_INPUT); f.setForeground(C_MUTED);
        f.setCaretColor(C_TEKS);
        f.setFont(new Font("Inter", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(C_BORDER, 1, true), new EmptyBorder(0, 12, 0, 12)));
        tambahPlaceholder(f, hint);
        return f;
    }

    private static JLabel buatLabelForm(String teks) {
        JLabel l = new JLabel(teks);
        l.setForeground(C_MUTED);
        l.setFont(new Font("Inter", Font.BOLD, 10));
        return l;
    }

    private static JButton buatTombolSecondary(String teks) {
        JButton btn = new JButton(teks);
        btn.setBackground(C_INPUT);
        btn.setForeground(C_MUTED);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(C_BORDER, 1, true));
        btn.setFont(new Font("Inter", Font.BOLD, 11));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private static JPanel buatHeaderForm(String iko, String judul, String sub) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel lIko = new JLabel(iko);
        lIko.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lIko.setAlignmentX(Component.CENTER_ALIGNMENT);
        lIko.setBorder(new EmptyBorder(14, 0, 0, 0)); // Tambah margin atas agar ikon tidak terpotong
        JLabel lJudul = new JLabel(judul);
        lJudul.setForeground(C_TEKS);
        lJudul.setFont(new Font("Inter", Font.BOLD, 24));
        lJudul.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lSub = new JLabel(sub);
        lSub.setForeground(C_MUTED);
        lSub.setFont(new Font("Inter", Font.PLAIN, 12));
        lSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(lIko);
        p.add(Box.createVerticalStrut(8));
        p.add(lJudul);
        p.add(Box.createVerticalStrut(4));
        p.add(lSub);
        return p;
    }

    private JPanel cariByName(Container c, String name) {
        for (Component comp : c.getComponents()) {
            if (name.equals(comp.getName())) return (JPanel) comp;
            if (comp instanceof Container container) {
                JPanel r = cariByName(container, name);
                if (r != null) return r;
            }
        }
        return null;
    }

    // ═════════════════════════════════════════════
    //  INNER CLASS: RoundedPanel
    // ═════════════════════════════════════════════
    static class RoundedPanel extends JPanel {
        private final int r; private final Color c;
        RoundedPanel(int r, Color c) { this.r = r; this.c = c; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c); g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);
            g2.dispose(); super.paintComponent(g);
        }
    }

    // ═════════════════════════════════════════════
    //  INNER CLASS: BrandButton
    // ═════════════════════════════════════════════
    static class BrandButton extends JButton {
        BrandButton(String t) {
            super(t);
            setContentAreaFilled(false); setBorderPainted(false);
            setFocusPainted(false); setForeground(Color.WHITE);
            setFont(new Font("Inter", Font.BOLD, 11));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isEnabled() ? C_AKSEN : C_BORDER);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2.dispose(); super.paintComponent(g);
        }
    }

    // ═════════════════════════════════════════════
    //  INNER CLASS: WrapLayout (grid responsif)
    // ═════════════════════════════════════════════
    static class WrapLayout extends FlowLayout {
        WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }
        @Override public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }
        @Override public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }
        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;
                int hgap = getHgap(), vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - insets.left - insets.right - hgap * 2;
                int x = 0, y = insets.top + vgap, rowH = 0;
                for (int i = 0; i < target.getComponentCount(); i++) {
                    Component c = target.getComponent(i);
                    if (!c.isVisible()) continue;
                    Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                    if (x != 0 && x + d.width > maxWidth) { y += rowH + vgap; x = 0; rowH = 0; }
                    x += d.width + hgap;
                    rowH = Math.max(rowH, d.height);
                }
                return new Dimension(targetWidth, y + rowH + vgap + insets.bottom);
            }
        }
    }
}