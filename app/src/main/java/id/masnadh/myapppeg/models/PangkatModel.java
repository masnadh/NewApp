package id.masnadh.myapppeg.models;

public class PangkatModel {

    String pangkat, gol, jns_pangkat, pejabat_sk, no_sk, tgl_sk, tmt_pangat, status_pan;
    int id;

    public PangkatModel(String pangkat, String gol, String jns_pangkat, String pejabat_sk, String no_sk, String tgl_sk, String tmt_pangat, String status_pan, int id) {
        this.pangkat = pangkat;
        this.gol = gol;
        this.jns_pangkat = jns_pangkat;
        this.pejabat_sk = pejabat_sk;
        this.no_sk = no_sk;
        this.tgl_sk = tgl_sk;
        this.tmt_pangat = tmt_pangat;
        this.status_pan = status_pan;
        this.id = id;
    }

    public String getPangkat() {
        return pangkat;
    }

    public void setPangkat(String pangkat) {
        this.pangkat = pangkat;
    }

    public String getGol() {
        return gol;
    }

    public void setGol(String gol) {
        this.gol = gol;
    }

    public String getJns_pangkat() {
        return jns_pangkat;
    }

    public void setJns_pangkat(String jns_pangkat) {
        this.jns_pangkat = jns_pangkat;
    }

    public String getPejabat_sk() {
        return pejabat_sk;
    }

    public void setPejabat_sk(String pejabat_sk) {
        this.pejabat_sk = pejabat_sk;
    }

    public String getNo_sk() {
        return no_sk;
    }

    public void setNo_sk(String no_sk) {
        this.no_sk = no_sk;
    }

    public String getTgl_sk() {
        return tgl_sk;
    }

    public void setTgl_sk(String tgl_sk) {
        this.tgl_sk = tgl_sk;
    }

    public String getTmt_pangat() {
        return tmt_pangat;
    }

    public void setTmt_pangat(String tmt_pangat) {
        this.tmt_pangat = tmt_pangat;
    }

    public String getStatus_pan() {
        return status_pan;
    }

    public void setStatus_pan(String status_pan) {
        this.status_pan = status_pan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PangkatModel() {

    }
}
