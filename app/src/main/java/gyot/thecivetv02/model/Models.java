package gyot.thecivetv02.model;

/**
 * Created by gyot on 06/12/16.
 */

public class Models {

    /**
     * id_menu : 0
     * keterangan : 1
     * id_katagori : 2
     * nm_menu : Ayam geprek
     * harga : 10000
     * img : img/ayam_geprek.jpg
     */

    private String id_menu;
    private String keterangan;
    private String id_katagori;
    private String nm_menu;
    private String harga;
    private String img;

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getId_katagori() {
        return id_katagori;
    }

    public void setId_katagori(String id_katagori) {
        this.id_katagori = id_katagori;
    }

    public String getNm_menu() {
        return nm_menu;
    }

    public void setNm_menu(String nm_menu) {
        this.nm_menu = nm_menu;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
