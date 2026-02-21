package Buku;

public class Notbuku {
    private String admin;

    public Notbuku(String admin) {
        this.admin = admin;
    }

    public void cetakStruk(Detail[] buku) {
        System.out.println("\n============ DAFTAR BUKU ============");
        System.out.println("Di data oleh: " + admin);
        for (Detail p : buku) {
            p.infoBuku();  // polymorphism: panggil sesuai subclass
        }
        System.out.println("=====================================\n");
    }
}

