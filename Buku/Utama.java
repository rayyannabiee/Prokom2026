package Buku;

public class Utama {
    public static void main(String[] args) throws Exception{
        Detail b1 = new Daftarbuku("Arah Langkah", "Fiersa Besari", "Sastra Perjalanan", "Mediakita", 2018, 3);
        Detail bk1 = new Ketersediaan("Arah Langkah", "Fiersa Besari", true);

        Detail[] daftar = {b1, bk1};
        
        Notbuku admin = new Notbuku("Mas Pali");
        admin.cetakStruk(daftar);
    }
}
