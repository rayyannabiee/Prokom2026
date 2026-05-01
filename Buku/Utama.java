package Buku;

import java.util.ArrayList;
import java.util.List;

public class Utama {

    private static List<Daftarbuku> daftarBuku = new ArrayList<>();
    private static List<Ketersediaan> daftarKetersediaan = new ArrayList<>();

    public static void jalankanProgram() {
        tambahBuku(new Daftarbuku(
            "Arah Langkah", "Fiersa Besari",
            "Sastra Perjalanan", "Mediakita", 2018, 3,
            "covers/arah_langkah.jpg",
            "Sebuah catatan perjalanan Fiersa Besari menjelajahi " +
            "Indonesia dari Sabang sampai Merauke. Buku ini berisi " +
            "refleksi hidup, puisi, dan filosofi yang lahir dari " +
            "setiap langkah perjalanannya."
        ), true);

        tambahBuku(new Daftarbuku(
            "Laskar Pelangi", "Andrea Hirata",
            "Novel", "Bentang", 2005, 5,
            "covers/laskar_pelangi.jpg",
            "Kisah sepuluh anak kampung di Belitung yang berjuang " +
            "mengenyam pendidikan di sekolah yang hampir roboh. " +
            "Novel inspiratif tentang semangat, persahabatan, dan " +
            "mimpi yang tak pernah padam."
        ), true);

        tambahBuku(new Daftarbuku(
            "Bumi Manusia", "Pramoedya Ananta Toer",
            "Sejarah", "Lentera", 1980, 0,
            "covers/bumi_manusia.jpg",
            "Novel pertama dari Tetralogi Buru karya Pramoedya. " +
            "Mengisahkan Minke, pemuda pribumi di era kolonial Belanda " +
            "yang berjuang melawan ketidakadilan melalui tulisan " +
            "dan cinta."
        ), false);

        tambahBuku(new Daftarbuku(
            "Filosofi Teras", "Henry Manampiring",
            "Pengembangan Diri", "Kompas", 2018, 4,
            "covers/filosofi_teras.jpg",
            "Panduan praktis filsafat Stoa untuk menghadapi " +
            "kecemasan dan emosi negatif di kehidupan modern. " +
            "Ditulis dengan bahasa ringan dan relevan untuk " +
            "pembaca Indonesia."
        ), true);

        tambahBuku(new Daftarbuku(
            "Atomic Habits", "James Clear",
            "Motivasi", "Gramedia", 2018, 0,
            "covers/atomic_habits.jpg",
            "Panduan membangun kebiasaan baik dan menghilangkan " +
            "kebiasaan buruk secara bertahap. James Clear menjelaskan " +
            "bahwa perubahan kecil yang konsisten dapat menghasilkan " +
            "hasil yang luar biasa."
        ), false);

        tambahBuku(new Daftarbuku(
            "Senja di Jakarta", "Mochtar Lubis",
            "Novel", "Yayasan Obor", 1970, 2,
            "covers/senja_jakarta.jpg",
            "Novel klasik Indonesia yang menggambarkan korupsi, " +
            "kemiskinan, dan kehidupan kota Jakarta pasca kemerdekaan. " +
            "Karya berani yang pernah dilarang terbit di masanya."
        ), true);
    }

    private static void tambahBuku(Daftarbuku buku, boolean tersedia) {
        daftarBuku.add(buku);
        daftarKetersediaan.add(new Ketersediaan(buku.getJudul(), buku.getPenulis(), tersedia));
    }

    public static List<Daftarbuku> getDaftarBuku() { return daftarBuku; }

    public static boolean isTersedia(String judul) {
        for (Ketersediaan k : daftarKetersediaan) {
            if (k.getJudul().equals(judul)) return k.getTersedia();
        }
        return false;
    }
}