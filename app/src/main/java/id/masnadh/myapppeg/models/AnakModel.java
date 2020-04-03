package id.masnadh.myapppeg.models;

public class AnakModel {

    String nikAn, namaAn, tempatAn, tglAn, pendidikanAn, pekerjaanAn, hubunganAn;
    int id;

    public AnakModel() {
    }

    public AnakModel(String nikAn, String namaAn, String tempatAn, String tglAn, String pendidikanAn, String pekerjaanAn, String hubunganAn, int id) {
        this.nikAn = nikAn;
        this.namaAn = namaAn;
        this.tempatAn = tempatAn;
        this.tglAn = tglAn;
        this.pendidikanAn = pendidikanAn;
        this.pekerjaanAn = pekerjaanAn;
        this.hubunganAn = hubunganAn;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public AnakModel(String nikAn, String namaAn, String tempatAn, String tglAn, String pendidikanAn, String pekerjaanAn, String hubunganAn) {
//        this.nikAn = nikAn;
//        this.namaAn = namaAn;
//        this.tempatAn = tempatAn;
//        this.tglAn = tglAn;
//        this.pendidikanAn = pendidikanAn;
//        this.pekerjaanAn = pekerjaanAn;
//        this.hubunganAn = hubunganAn;
//    }

    public String getNikAn() {
        return nikAn;
    }

    public void setNikAn(String nikAn) {
        this.nikAn = nikAn;
    }

    public String getNamaAn() {
        return namaAn;
    }

    public void setNamaAn(String namaAn) {
        this.namaAn = namaAn;
    }

    public String getTempatAn() {
        return tempatAn;
    }

    public void setTempatAn(String tempatAn) {
        this.tempatAn = tempatAn;
    }

    public String getTglAn() {
        return tglAn;
    }

    public void setTglAn(String tglAn) {
        this.tglAn = tglAn;
    }

    public String getPendidikanAn() {
        return pendidikanAn;
    }

    public void setPendidikanAn(String pendidikanAn) {
        this.pendidikanAn = pendidikanAn;
    }

    public String getPekerjaanAn() {
        return pekerjaanAn;
    }

    public void setPekerjaanAn(String pekerjaanAn) {
        this.pekerjaanAn = pekerjaanAn;
    }

    public String getHubunganAn() {
        return hubunganAn;
    }

    public void setHubunganAn(String hubunganAn) {
        this.hubunganAn = hubunganAn;
    }
}
