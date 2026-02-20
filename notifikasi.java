class Notifikasi {
    private String pesan;
    private String tanggal;
    private boolean sudahDibaca;

    public Notifikasi(String pesan, String tanggal) {
        this.pesan = pesan;
        this.tanggal = tanggal;
        this.sudahDibaca = false; 
    }

    public void tandaiSudahDibaca() {
        sudahDibaca = true;
    }

    public void tampilkanNotifikasi() {
        System.out.println("=== NOTIFIKASI PERPUSTAKAAN DIGITAL ===");
        System.out.println("Pesan   : " + pesan);
        System.out.println("Tanggal : " + tanggal);
        
        if (sudahDibaca) {
            System.out.println("Status  : Sudah Dibaca");
        } else {
            System.out.println("Status  : Belum Dibaca");
        }
        
        System.out.println();
    }
}