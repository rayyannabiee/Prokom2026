public class aplikasi {
    public static void main(String[] args) {
        buku bumi= new buku();
        buku Pulang = new buku();

        Pulang.setgenre("action");
        Pulang.setpenulis("Tere Liye");
        
        System.out.println("penulis buku Pulang:" + Pulang.getpenulis());
        System.out.println("genre buku Pulang:" + Pulang.getgenre());
        
        bumi.setgenre("fantasy");
        bumi.setpenulis("Tere Liye");
        

        System.out.println("penulis buku Bumi:" + bumi.getpenulis());
        System.out.println("penulis buku Bumi:" + bumi.getgenre());

        
        
    }
}

