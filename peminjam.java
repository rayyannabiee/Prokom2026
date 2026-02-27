public class peminjam extends manusia {
    public peminjam(String nama, String nim){
        super(nama, nim); 
    }

    @Override
    public void datapeminjam()
    {
        System.out.println(getnama() + " nim : " + getnim());
    }

    
    
}