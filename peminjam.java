public class peminjam extends manusia {
    public peminjam(String nama, int umur, int id){
        super(nama, umur, id); //MEMANGGIL KONSTRUKTOR HEWAN
    }

    @Override
    public void id(){
        System.out.println(getnama() + " id : 0887532");
    }
    
}