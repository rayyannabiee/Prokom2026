package Buku;

public class Ketersediaan extends Detail {
    private boolean tersedia;

    public Ketersediaan(String judul, String penulis, boolean tersedia){
        super(judul, penulis);
        this.tersedia = tersedia;
    }
    @Override
    public void infoBuku(){
        String status = tersedia ? "Tersedia" : "Kosong";
        System.out.println("|Ketersediaan   : " + status);
    }
}
