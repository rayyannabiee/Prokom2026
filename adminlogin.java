public class adminlogin {
    private admin[] daftarAdmin= new admin[5];
    private admin adminAktif;

    public adminlogin(){
    daftarAdmin[0]= new admin("Rayyan", "raykel2");
    daftarAdmin[1]= new admin("Naufal", "naukel2");
    daftarAdmin[2]= new admin("Bolkiah", "bolkel2");
    daftarAdmin[3]= new admin("Jihan", "jihkel2");
    daftarAdmin[4]= new admin("Verizka", "verkel2");

    }

    public boolean ceklogin(String username, String password){
        for(admin a: daftarAdmin){
            if(a.getusername().equals(username)&& a.getpassword().equals(password)){
                this.adminAktif=a;
                return true;
            }
        }
        return false;
    }

    public admin getadminAktif(){
        return adminAktif;
    }

}
