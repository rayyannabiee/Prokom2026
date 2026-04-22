import java.util.Scanner;
import Buku.Utama;
import Buku.adminlogin;
import Buku.admin;

public class aplikasi {
    public static void main(String[] args) {
       Scanner s = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n ===== MENU PERPUSTAKAAN =====");
            System.out.println("1. Buku");
            System.out.println("2. Menu 2");
            System.out.println("3. Menu 3");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = s.nextInt();
            s.nextLine();

            switch (pilihan) {
                case 1:
                    Utama.jalankanProgram();
                    int pilih;
                    do {
                        System.out.println("\n--- MENU BUKU ---");
                        System.out.println("1. Kembali");
                        System.out.print("Pilih: ");
                        pilih = s.nextInt();
                        switch (pilih) {
                            case 1:
                            System.out.println("Kembali");
                            break;
                            default:
                            System.out.println("Pilihan salah");
                    }
                    } while (pilih != 1);
                    

                case 2:
                    System.out.println("isi ya teman");
                    break;

                case 3:
                    datakelas
                    System.out.println("Isi ya teman");
                    break;

                case 4:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak tersedia.");
            }

        } while (pilihan != 4);

        s.close();
    }
}


