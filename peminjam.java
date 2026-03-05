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
        tambahpeminjam(new peminjam("Achmad Daniel Albar", "09021282530078", "082182297479", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Aditiah Okta Romadhon", "09021282530088", "082268583450", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Adrina Ginata Dinda Harjanti", "09021282530104", "082178552858", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Alfinandra Rashyid Pratama", "090212825115", "085266657473", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Aqilah Rafif Masrian", "09021282530080", "085267889639", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Chanda putri zahira", "09021282530110", "085268054815", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Dhea Aurellia", "09021282530075", "081368969955", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Duhairillah", "09021282530092", "089604451010", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Ega Asliya Putri", "09021282530098", "087747472112", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Endry Julyan Putra", "09021282530089,", "08995268346", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Jihan Faraza Melka", "09021282530073", "087817012221", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Laudia Zahratus Sita", "09021282530085", "082176137528", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("M. Arya Putra Satria"  , "09021282530077", "087815580274", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("M. Bolkiah Ayesha Alfarizi Asyari" , "09021282530106", "081327290869", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("M. Nabil Imtiyaz", "09021282530116,", "082372335225", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("M. Rayyan Alfarabi A." , "09021282530096", "0812-7834-3489", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Muhammad Ajhar Rizqi Hidayatullah", "09021282530101", "085357510504", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Muhammad Farhan Hidayat", "09021282530097", "082361996073", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Muhammad Raffa Al Ra'uf", "09021282530112", "082289413445", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Muhammad Rasyid Ridho", "09021282530095", "082162335558", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Muhammad Rochman", "09021282530079",  "082179337100", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Noufal Izdihar" ," 09021282530093", "081271026897", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Pijar Yefa Tri Putra" ,"09021282530108", "083116870745", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Alfinandra Rashyid Pratama", "090212825115", "085266657473", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Aqilah Rafif Masrian", "09021282530080", "085267889639", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Chanda putri zahira", "09021282530110", "085268054815", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Dhea Aurellia", "09021282530075", "081368969955", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Duhairillah", "09021282530092", "089604451010", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Ega Asliya Putri", "09021282530098", "087747472112", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Endry Julyan Putra", "09021282530089,", "08995268346", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Jihan Faraza Melka", "09021282530073", "087817012221", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("Laudia Zahratus Sita", "09021282530085", "082176137528", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("M. Arya Putra Satria"  , "09021282530077", "087815580274", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("M. Bolkiah Ayesha Alfarizi Asyari" , "09021282530106", "081327290869", 0, "30-12-2026"));
        tambahpeminjam(new peminjam("M. Nabil Imtiyaz", "09021282530116,", "082372335225", 0, "30-12-2026"));
    }

}