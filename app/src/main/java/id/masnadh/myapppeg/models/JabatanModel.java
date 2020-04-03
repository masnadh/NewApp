package id.masnadh.myapppeg.models;

public class JabatanModel {
    String jabatan, eselon, tmt_jabatan, status_jab;
    int id;

    public JabatanModel(String jabatan, String eselon, String tmt_jabatan, String status_jab, int id) {
        this.jabatan = jabatan;
        this.eselon = eselon;
        this.tmt_jabatan = tmt_jabatan;
        this.status_jab = status_jab;
        this.id = id;
    }

    public JabatanModel() {
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getEselon() {
        return eselon;
    }

    public void setEselon(String eselon) {
        this.eselon = eselon;
    }

    public String getTmt_jabatan() {
        return tmt_jabatan;
    }

    public void setTmt_jabatan(String tmt_jabatan) {
        this.tmt_jabatan = tmt_jabatan;
    }

    public String getStatus_jab() {
        return status_jab;
    }

    public void setStatus_jab(String status_jab) {
        this.status_jab = status_jab;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
