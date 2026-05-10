package Buku;

public class Daftarbuku extends Detail {
    private String genre;
    private String penerbit;
    private int tahunterbit;
    private int jumlah;
    private String imagePath;   
    private String deskripsi;   

    public Daftarbuku(String judul, String penulis, String genre, String penerbit,
                      int tahunterbit, int jumlah, String imagePath, String deskripsi) {
        super(judul, penulis);
        this.genre = genre;
        this.penerbit = penerbit;
        this.tahunterbit = tahunterbit;
        this.jumlah = jumlah;
        this.imagePath = imagePath;
        this.deskripsi = deskripsi;
    }

    public String getGenre()       { return genre; }
    public String getPenerbit()    { return penerbit; }
    public int getTahunterbit()    { return tahunterbit; }
    public int getJumlah()         { return jumlah; }
    public String getImagePath()   { return imagePath; }   // ← baru
    public String getDeskripsi()   { return deskripsi; }   // ← baru

    @Override
    public void infoBuku() {
        System.out.println("|Judul          : " + getJudul());
        System.out.println("|Penulis        : " + getPenulis());
        System.out.println("|Genre          : " + getGenre());
        System.out.println("|Penerbit       : " + getPenerbit());
        System.out.println("|Tahun terbit   : " + getTahunterbit());
        System.out.println("|Jumlah         : " + getJumlah());
        System.out.println("|Image Path     : " + getImagePath());
        System.out.println("|Deskripsi      : " + getDeskripsi());
    }
}