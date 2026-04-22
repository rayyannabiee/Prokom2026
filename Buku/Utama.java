package Buku;
public class Utama {
    public static void jalankanProgram(){

        Detail b1 = new Daftarbuku("Arah Langkah", "Fiersa Besari", "Sastra Perjalanan", "Mediakita", 2018, 3);
        Detail bk1 = new Ketersediaan("Arah Langkah", "Fiersa Besari", true);
        Detail b2 = new Daftarbuku(null, null, null, null, 0, 0);
        Detail bk2 = new Ketersediaan(null, null, false);

        Detail[] daftar1 = {b1, bk1};
        Detail[] daftar2 = {b2, bk2};
        
        Notbuku admin1 = new Notbuku("Mas Pali");
        admin1.cetakDaftar(daftar1);
        admin1.cetakDaftar(daftar2);
    }
}
