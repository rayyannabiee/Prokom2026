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

        peminjam Rayyan = new peminjam("Rayan", 18, 3252349);

        System.out.println("== IDENTITAS PEMINJAM==");
        System.out.println("\n nama: " + Rayyan.getnama());
        System.out.println("\n umur: " + Rayyan.getumur());
        System.out.println("\n id: " + Rayyan.getid());
    }
}

