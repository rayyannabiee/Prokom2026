public class manusia {
    private String nama; //enkapsulasi
    private String nim;
    private String nomorhp;
    
  
    //setter
    public manusia(String nama, String nim, String nomorhp){ //construktor
        this.nama = nama;
        this.nim = nim;
        this.nomorhp = nomorhp;
    }            
    public void setnim(String nim){
        this.nim = nim;
    }
    public String getnama(){
        return nama;
    }
    public String getnim(){
        return nim;
    }
    public String getnomorhp(){
        return nomorhp;
    }

    public void datamanusia() {
        System.out.println( " nama: " + getnama());
        System.out.println(" nim: " + getnim());
        System.out.println(" nomor hp: " + getnomorhp());
    }
    
}
