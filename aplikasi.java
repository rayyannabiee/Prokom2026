import Buku.Utama;


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
                    System.out.println("\n--- LOGIN ADMIN ---");
                    adminlogin login= new adminlogin();

                    System.out.print("Username:" );
                    String username= s.nextLine();
                    System.out.print("Password:" );
                    String password= s.nextLine();

                    if(login.ceklogin(username, password)){
                        admin a= login.getadminAktif();
                        System.out.println("Login berhasil! Selamat datang "+ a.getusername());
                    }else{
                        System.out.println("Login gagal. Ulangi kembali");
                    }

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


