import java.util.*;
public class peminjam extends manusia {
    private int jumlahpinjam;
    private String bataspinjam;
    public peminjam(String nama, String nim, String nomorhp, int jumlahpinjam, String bataspinjam) {
        super(nama, nim, nomorhp);
        this.jumlahpinjam = jumlahpinjam;
        this.bataspinjam = bataspinjam;
       
    }
     @Override
    public void datamanusia() {
            super.datamanusia();
            System.out.println(" jumlah pinjam: " + jumlahpinjam);
            System.out.println(" batas pinjam: " + bataspinjam);
    }
    
    private List<peminjam> daftarpeminjam = new ArrayList<>();

    public boolean tambahpeminjam (peminjam Peminjam){
    
        if (Peminjam.getnama() == null || Peminjam.getnim() == null || Peminjam.getnomorhp() == null){
            System.out.println("data yang anda masukkan tidak lengkap! mohon isi secara lengkap");
            return false;
        }
        if (carinim(Peminjam.getnim()) != null){
            System.out.println("data dengan nim " + Peminjam.getnim() + " sudah ada! mohon masukkan nim yang berbeda");
        } 
        daftarpeminjam.add(Peminjam); 
        System.out.println( Peminjam.getnama() + " berhasil ditambahkan sebagai peminjam");
        return true;
    }
    public peminjam carinim (String nim){
        for (peminjam Peminjam : daftarpeminjam){
            if (Peminjam.getnim().equals(nim)){
                return Peminjam;
            }
        }
        System.out.println("data peminjam dengan nim " + nim + " tidak ditemukan");
        return null;
    }
    public peminjam carinama (String nama){
        for (peminjam Peminjam : daftarpeminjam){
            if (Peminjam.getnama().equals(nama)){
                return Peminjam;
            }
        }
        System.out.println("data peminjam dengan nim " + nama + " tidak ditemukan");
        return null;
    }
    public peminjam carinoHP (String nomorhp){
        for (peminjam Peminjam : daftarpeminjam){
            if (Peminjam.getnomorhp().equals(nomorhp)){
                return Peminjam;
            }
        }
        System.out.println("data peminjam dengan nomor hp " + nomorhp + " tidak ditemukan");
        return null;
    }

    private void dataKelas(){
        tambahpeminjam(new peminjam("Jihan Faraza Melka", "09021282530073", "087817012221", 2, "30-12-2026"));
        tambahpeminjam(new peminjam("Jihan Faraza Melka", "09021282530073", "087817012221", 2, "30-12-2026"));
        tambahpeminjam(new peminjam("Jihan Faraza Melka", "09021282530073", "087817012221", 2, "30-12-2026"));
        tambahpeminjam(new peminjam("Jihan Faraza Melka", "09021282530073", "087817012221", 2, "30-12-2026"));tambahpeminjam(new peminjam("Jihan Faraza Melka", "09021282530073", "087817012221", 2, "30-12-2026"));
    }

}