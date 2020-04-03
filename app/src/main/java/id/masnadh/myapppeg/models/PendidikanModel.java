package id.masnadh.myapppeg.models;

public class PendidikanModel {

    String namaSek, jenjang, prodi, lulus;

    public PendidikanModel(){}

    public PendidikanModel(String namaSek, String jenjang, String prodi, String lulus) {
        this.namaSek = namaSek;
        this.jenjang = jenjang;
        this.prodi = prodi;
        this.lulus = lulus;
    }

    public String getNamaSek() {
        return namaSek;
    }

    public void setNamaSek(String namaSek) {
        this.namaSek = namaSek;
    }

    public String getJenjang() {
        return jenjang;
    }

    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getLulus() {
        return lulus;
    }

    public void setLulus(String lulus) {
        this.lulus = lulus;
    }
}
