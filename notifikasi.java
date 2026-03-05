import java.util.Scanner;

public abstract class Notifikasi implements KirimNotif {

    private String pesan;
    private String tanggal;

    public Notifikasi(String pesan, String tanggal) {
        this.pesan = pesan;
        this.tanggal = tanggal;
    }

    public void tampilkan() {
        System.out.println("Pesan   : " + pesan);
        System.out.println("Tanggal : " + tanggal);
    }

    public abstract void jenisNotifikasi();

    // METHOD AGAR BISA DIPANGGIL DARI MAIN
    public static void menuNotifikasi(Scanner s) {

        int pilih;

        do {
            System.out.println("\n=== MENU NOTIFIKASI ===");
            System.out.println("1. Notifikasi Peminjaman");
            System.out.println("2. Notifikasi Pengembalian");
            System.out.println("3. Kembali");
            System.out.print("Pilih: ");
            pilih = s.nextInt();

            switch (pilih) {

                case 1:
                    Notifikasi n1 = new NotifPeminjaman(
                            "Buku hampir jatuh tempo",
                            "06-03-2026");

                    n1.jenisNotifikasi();
                    n1.tampilkan();
                    n1.kirim();
                    break;

                case 2:
                    Notifikasi n2 = new NotifPengembalian(
                            "Buku berhasil dikembalikan",
                            "06-03-2026");

                    n2.jenisNotifikasi();
                    n2.tampilkan();
                    n2.kirim();
                    break;

                case 3:
                    System.out.println("Kembali ke menu utama");
                    break;

                default:
                    System.out.println("Pilihan salah");
            }

        } while (pilih != 3);
    }
}