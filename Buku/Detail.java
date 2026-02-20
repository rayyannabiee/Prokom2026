package Buku;

public class Detail {
    private String judul;
    private String genre;
    private String penerbit;
    private int tahunterbit;

    public Detail(String judul, String genre, String penerbit, int tahunterbit) {
        this.judul = judul;
        this.genre = genre;
        this.penerbit = penerbit;
        this.tahunterbit = tahunterbit;
    }

    public String getJudul() {
        return judul;
    }

    public String getGenre() {
        return genre;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public int getTahunterbit() {
        return tahunterbit;
    }

    public void infoProduk() {
        System.out.println("Judul: " + judul + " | Genre: " + genre +" | Penerbit: " + penerbit + " | Tahun terbit: " + tahunterbit);
    }
}


