package Buku;

public class Detail {
    private String judul;
    private String penulis;

    public Detail(String judul,String penulis) {
        this.judul = judul;
        this.penulis = penulis;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void infoBuku() {
        System.out.println("Judul: " + judul + " | Penulis: " + penulis);
    }

}




