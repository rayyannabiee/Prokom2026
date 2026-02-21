package Buku;

public class Daftarbuku extends Detail{
    private String genre;
    private String penerbit;
    private int tahunterbit;
    private int jumlah;

    public Daftarbuku(String judul, String penulis,String genre, String penerbit, int tahunterbit, int jumlah) {
        super(judul, penulis);
        this.genre = genre;
        this.penerbit = penerbit;
        this.tahunterbit = tahunterbit;
        this.jumlah = jumlah;
    }
    public String getGenre(){
        return genre;
    }
    public String getPenerbit(){
        return penerbit;
    }
    public int getTahunterbit(){
        return tahunterbit;
    }
    public int getJumlah(){
        return jumlah;
    }

    @Override
    public void infoBuku(){
        System.out.println("|Judul          : " + getJudul());
        System.out.println("|Penulis        : " + getPenulis());
        System.out.println("|Genre          : " + getGenre());
        System.out.println("|Penerbit       : " + getPenerbit()); 
        System.out.println("|Tahun terbit   : " + getTahunterbit());
        System.out.println("|Jumlah         : " + getJumlah());
    }
}


