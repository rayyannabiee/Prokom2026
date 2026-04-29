package Buku;

import java.util.ArrayList;
import java.util.List;

public class Utama {

    // Simpan daftar buku secara statis agar bisa diakses GUI
    private static List<Daftarbuku> daftarBuku = new ArrayList<>();
    private static List<Ketersediaan> daftarKetersediaan = new ArrayList<>();

    public static void jalankanProgram() {
        // Tambah data buku
        tambahBuku(new Daftarbuku("Arah Langkah",     "Fiersa Besari",   "Sastra Perjalanan", "Mediakita",    2018, 3), true);
        tambahBuku(new Daftarbuku("Senja di Jakarta",  "Mochtar Lubis",   "Novel",             "Yayasan Obor", 1970, 2), true);
        tambahBuku(new Daftarbuku("Laskar Pelangi",    "Andrea Hirata",   "Novel",             "Bentang",      2005, 5), true);
        tambahBuku(new Daftarbuku("Bumi Manusia",      "Pramoedya A.T.",  "Sejarah",           "Lentera",      1980, 1), false);
        tambahBuku(new Daftarbuku("Filosofi Teras",    "Henry Manampiring","Pengembangan Diri", "Kompas",       2018, 4), true);
        tambahBuku(new Daftarbuku("Atomic Habits",     "James Clear",     "Motivasi",          "Gramedia",     2018, 3), false);
    }

    private static void tambahBuku(Daftarbuku buku, boolean tersedia) {
        daftarBuku.add(buku);
        daftarKetersediaan.add(new Ketersediaan(buku.getJudul(), buku.getPenulis(), tersedia));
    }

    // Getter untuk dipakai GUI
    public static List<Daftarbuku> getDaftarBuku() {
        return daftarBuku;
    }

    public static boolean isTersedia(String judul) {
        for (Ketersediaan k : daftarKetersediaan) {
            if (k.getJudul().equals(judul)) {
                // akses via method yang sudah ada di Ketersediaan
                return k.getTersedia();
            }
        }
        return false;
    }
}