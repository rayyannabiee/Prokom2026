import Buku.Utama;


public class aplikasi {
    public static void main(String[] args) {
       Scanner s = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n ===== MENU PERPUSTAKAAN =====");
            System.out.println("1. Buku");
<<<<<<< HEAD
            System.out.println("2. Menu 2");
            System.out.println("3. Menu 3");
            System.out.println("4. Keluar");
=======
            System.out.println("2. Notifikasi");
            System.out.println("3. Admin");
            System.out.println("4. peminjam");
            System.out.println("5. Keluar");
>>>>>>> 4bbcb0b91a5fb5705cd959d3305919e443190e4c
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
                    System.out.println(" LOGIN PEMINJAM ");
                    
                    break;
                case 5:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak tersedia.");
            }

        } while (pilihan != 4);


            int pilihan1;
            
            do {
                System.out.println("\n ===== MENU PERPUSTAKAAN =====");
                System.out.println("1. Buku");
                System.out.println("2. Notifikasi");
                System.out.println("3. Admin");
                System.out.println("4. Keluar");
                System.out.print("Pilih menu: ");
                pilihan1 = s.nextInt();
                
                switch (pilihan1) {
                    case 1:
                        Utama.jalankanProgram();
                        int pilih;
                        do {
                            System.out.println("\n--- MENU BUKU ---");
                            System.out.println("1. Kembali");
                            System.out.print("Pilih: ");
                            pilih = s.nextInt();
                            switch (pilih) {
                                case 1 -> System.out.println("Kembali");
                                default -> System.out.println("Pilihan salah");
                            }
                        } while (pilih != 1);
                        break;
                        
                    case 2:
                        Notifikasi.menuNotifikasi(s);
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
                
            } while (pilihan1 != 4);
        }
    }
    



