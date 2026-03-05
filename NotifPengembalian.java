public class NotifPengembalian extends Notifikasi {

    public NotifPengembalian(String pesan, String tanggal) {
        super(pesan, tanggal);
    }

    @Override
    public void jenisNotifikasi() {
        System.out.println("Jenis : Notifikasi Pengembalian Buku");
    }

    @Override
    public void kirim() {
        System.out.println("Notifikasi pengembalian dikirim.");
    }
}