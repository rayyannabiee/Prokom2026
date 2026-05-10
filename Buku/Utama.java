package Buku;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utama {

    private static List<Daftarbuku>         daftarBuku       = new ArrayList<>();
    private static Map<String, Ketersediaan> mapKetersediaan  = new LinkedHashMap<>();

    public static void jalankanProgram() {
        tambahBuku(new Daftarbuku(
            "Arah Langkah", "Fiersa Besari",
            "Sastra Perjalanan", "Mediakita", 2018, 3,
            "covers/arah_langkah.jpg",
            "Sebuah catatan perjalanan Fiersa Besari menjelajahi Indonesia " +
            "dari Sabang sampai Merauke. Buku ini berisi refleksi hidup, puisi, " +
            "dan filosofi yang lahir dari setiap langkah perjalanannya."
        ));
        tambahBuku(new Daftarbuku(
            "Senja di Jakarta", "Mochtar Lubis",
            "Novel", "Yayasan Obor", 1970, 2,
            "covers/senja_jakarta.jpg",
            "Novel klasik Indonesia yang menggambarkan korupsi, kemiskinan, " +
            "dan kehidupan kota Jakarta pasca kemerdekaan."
        ));
        tambahBuku(new Daftarbuku(
            "Laskar Pelangi", "Andrea Hirata",
            "Novel", "Bentang", 2005, 5,
            "covers/laskar_pelangi.jpg",
            "Kisah sepuluh anak kampung di Belitung yang berjuang mengenyam " +
            "pendidikan di sekolah yang hampir roboh."
        ));
        tambahBuku(new Daftarbuku(
            "Bumi Manusia", "Pramoedya Ananta Toer",
            "Sejarah", "Lentera", 1980, 1,
            "covers/bumi_manusia.jpg",
            "Novel pertama dari Tetralogi Buru karya Pramoedya. Mengisahkan " +
            "Minke yang berjuang melawan ketidakadilan melalui tulisan dan cinta."
        ));
        tambahBuku(new Daftarbuku(
            "Filosofi Teras", "Henry Manampiring",
            "Pengembangan Diri", "Kompas", 2018, 4,
            "covers/filosofi_teras.jpg",
            "Panduan praktis filsafat Stoa untuk menghadapi kecemasan dan " +
            "emosi negatif di kehidupan modern."
        ));
        tambahBuku(new Daftarbuku(
            "Atomic Habits", "James Clear",
            "Motivasi", "Gramedia", 2018, 0,   // stok 0 → otomatis Kosong
            "covers/atomic_habits.jpg",
            "Panduan membangun kebiasaan baik secara bertahap. Perubahan kecil " +
            "yang konsisten menghasilkan hasil yang luar biasa."
        ));
    }

    private static void tambahBuku(Daftarbuku buku) {
        daftarBuku.add(buku);
        mapKetersediaan.put(
            buku.getJudul(),
            new Ketersediaan(buku.getJudul(), buku.getPenulis(), true, buku.getJumlah())
        );
    }

    public static List<Daftarbuku> getDaftarBuku() { return daftarBuku; }

    public static Ketersediaan getKetersediaan(String judul) {
        return mapKetersediaan.get(judul);
    }

    public static boolean isTersedia(String judul) {
        Ketersediaan k = mapKetersediaan.get(judul);
        return k != null && k.getTersedia();
    }

    public static int getStok(String judul) {
        Ketersediaan k = mapKetersediaan.get(judul);
        return k != null ? k.getStok() : 0;
    }

    public static boolean pinjamBuku(String judul) {
        Ketersediaan k = mapKetersediaan.get(judul);
        if (k == null) return false;
        return k.pinjam();  // return false jika stok sudah 0
    }

    public static void kembalikanBuku(String judul) {
        Ketersediaan k = mapKetersediaan.get(judul);
        if (k != null) k.kembalikan();
    }

    public static void setStokBuku(String judul, int stokBaru) {
        Ketersediaan k = mapKetersediaan.get(judul);
        if (k != null) k.setStok(stokBaru);
    }

    public static void daftarkanKetersediaan(String judul, String penulis, int stok) {
        mapKetersediaan.put(judul, new Ketersediaan(judul, penulis, true, stok));
    }
}