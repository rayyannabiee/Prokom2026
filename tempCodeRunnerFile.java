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

                  
                    break;