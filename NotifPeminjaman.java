public class NotifPeminjaman extends Notifikasi {

    public NotifPeminjaman(String pesan, String tanggal) {
        super(pesan, tanggal);
    }

    @Override
    public void jenisNotifikasi() {
        System.out.println("Jenis : Notifikasi Peminjaman Buku");
    }

    @Override
    public void kirim() {
        System.out.println("Notifikasi peminjaman dikirim.");
    }
}