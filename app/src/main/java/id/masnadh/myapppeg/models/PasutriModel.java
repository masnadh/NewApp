package id.masnadh.myapppeg.models;

public class PasutriModel {

    String nikPas, namaPas, tempatPas, tglPas, pendidikanPas, pekerjaanPas, hubunganPas;

    public PasutriModel(){}

    public PasutriModel(String nikPas, String namaPas, String tempatPas, String tglPas, String pendidikanPas, String pekerjaanPas, String hubunganPas) {
        this.nikPas = nikPas;
        this.namaPas = namaPas;
        this.tempatPas = tempatPas;
        this.tglPas = tglPas;
        this.pendidikanPas = pendidikanPas;
        this.pekerjaanPas = pekerjaanPas;
        this.hubunganPas = hubunganPas;
    }

    public String getNikPas() {
        return nikPas;
    }

    public void setNikPas(String nikPas) {
        this.nikPas = nikPas;
    }

    public String getNamaPas() {
        return namaPas;
    }

    public void setNamaPas(String namaPas) {
        this.namaPas = namaPas;
    }

    public String getTempatPas() {
        return tempatPas;
    }

    public void setTempatPas(String tempatPas) {
        this.tempatPas = tempatPas;
    }

    public String getTglPas() {
        return tglPas;
    }

    public void setTglPas(String tglPas) {
        this.tglPas = tglPas;
    }

    public String getPendidikanPas() {
        return pendidikanPas;
    }

    public void setPendidikanPas(String pendidikanPas) {
        this.pendidikanPas = pendidikanPas;
    }

    public String getPekerjaanPas() {
        return pekerjaanPas;
    }

    public void setPekerjaanPas(String pekerjaanPas) {
        this.pekerjaanPas = pekerjaanPas;
    }

    public String getHubunganPas() {
        return hubunganPas;
    }

    public void setHubunganPas(String hubunganPas) {
        this.hubunganPas = hubunganPas;
    }
}
