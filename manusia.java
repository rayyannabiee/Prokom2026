public class manusia {
    private String nama; //enkapsulasi
    private String nim;
  
    //setter
    public manusia(String nama, String nim){ //construktor
        this.nama = nama;
        this.nim = nim;
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
    public void nim(){
        System.out.println("nim: " + getnim());
    }
    public void datapeminjam() {
        System.out.println(getnama() + " nim : " + getnim());
    }
    
}
