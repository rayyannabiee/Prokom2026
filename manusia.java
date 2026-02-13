public class manusia {
    private String nama; //enkapsulasi
    private int umur;
    private int id;
    //setter
    public manusia(String nama, int umur, int id){ //construktor
        this.nama = nama;
        this.umur = umur;
        this.id = id;
    }            
    public void setumur(int umur){
        this.umur = umur;
    }
     public void setid(int id){
        this.id = id;
    }
    public String getnama(){
        return nama;
    }
    public int getumur(){
        return umur;
    }
    public int getid(){
        return id;
    }
    public void id(){
        System.out.println("id peminjam: ");
    }
    
}
