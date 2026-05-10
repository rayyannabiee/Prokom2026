package Buku;

public class Ketersediaan extends Detail {
    private boolean tersedia;
    private int stok;

    public Ketersediaan(String judul, String penulis, boolean tersedia, int stok) {
        super(judul, penulis);
        this.stok     = stok;
        this.tersedia = stok > 0; // otomatis: tersedia jika stok > 0
    }

    public boolean getTersedia() { return tersedia; }
    public int     getStok()     { return stok; }

    public boolean pinjam() {
        if (stok <= 0) return false;   // tidak bisa dipinjam
        stok--;
        tersedia = stok > 0;           // tersedia hanya jika masih ada stok
        return true;
    }

    public void kembalikan() {
        stok++;
        tersedia = stok > 0;
    }

    public void setStok(int stok) {
        this.stok     = Math.max(0, stok);
        this.tersedia = this.stok > 0;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    @Override
    public void infoBuku() {
        String status = tersedia ? "Tersedia" : "Kosong";
        System.out.println("|Ketersediaan   : " + status);
        System.out.println("|Stok tersisa   : " + stok);
    }
}