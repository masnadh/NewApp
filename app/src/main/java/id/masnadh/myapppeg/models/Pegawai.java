package id.masnadh.myapppeg.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Pegawai {

    private String id;
    private String nuptk;
    private String statPeg;
    private String ktp;
    private String nama;
    private String nip;
    private String tempatLahir;
    private String tangalLahir;
    private String agama;
    private String jk;
    private String golda;
    private String nikah;
    private String alamat;
    private String telepon;
    private String email;
    private String foto;

    public Pegawai(){}

    public Pegawai(String id, String nuptk, String statPeg, String ktp, String nama, String nip, String tempatLahir, String tangalLahir, String agama, String jk, String golda, String nikah, String alamat, String telepon, String email, String foto) {
        this.id = id;
        this.nuptk = nuptk;
        this.statPeg = statPeg;
        this.ktp = ktp;
        this.nama = nama;
        this.nip = nip;
        this.tempatLahir = tempatLahir;
        this.tangalLahir = tangalLahir;
        this.agama = agama;
        this.jk = jk;
        this.golda = golda;
        this.nikah = nikah;
        this.alamat = alamat;
        this.telepon = telepon;
        this.email = email;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNuptk() {
        return nuptk;
    }

    public void setNuptk(String nuptk) {
        this.nuptk = nuptk;
    }

    public String getStatPeg() {
        return statPeg;
    }

    public void setStatPeg(String statPeg) {
        this.statPeg = statPeg;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTangalLahir() {
        return tangalLahir;
    }

    public void setTangalLahir(String tangalLahir) {
        this.tangalLahir = tangalLahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getGolda() {
        return golda;
    }

    public void setGolda(String golda) {
        this.golda = golda;
    }

    public String getNikah() {
        return nikah;
    }

    public void setNikah(String nikah) {
        this.nikah = nikah;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    //    public Pegawai(String id, String nama, String nip, String tempatLahir, String tangalLahir, String agama, String jk, String golda, String nikah, String alamat, String telepon, String email, String foto) {
//        this.id = id;
//        this.nama = nama;
//        this.nip = nip;
//        this.tempatLahir = tempatLahir;
//        this.tangalLahir = tangalLahir;
//        this.agama = agama;
//        this.jk = jk;
//        this.golda = golda;
//        this.nikah = nikah;
//        this.alamat = alamat;
//        this.telepon = telepon;
//        this.email = email;
//        this.foto = foto;
//    }
//
//    public Pegawai(String id, String nama, String nip, String tempat_lhr, String tgl_lhr) {
//
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getNama() {
//        return nama;
//    }
//
//    public void setNama(String nama) {
//        this.nama = nama;
//    }
//
//    public String getNip() {
//        return nip;
//    }
//
//    public void setNip(String nip) {
//        this.nip = nip;
//    }
//
//    public String getTempatLahir() {
//        return tempatLahir;
//    }
//
//    public void setTempatLahir(String tempatLahir) {
//        this.tempatLahir = tempatLahir;
//    }
//
//    public String getTangalLahir() {
//        return tangalLahir;
//    }
//
//    public void setTangalLahir(String tangalLahir) {
//        this.tangalLahir = tangalLahir;
//    }
//
//    public String getAgama() {
//        return agama;
//    }
//
//    public void setAgama(String agama) {
//        this.agama = agama;
//    }
//
//    public String getJk() {
//        return jk;
//    }
//
//    public void setJk(String jk) {
//        this.jk = jk;
//    }
//
//    public String getGolda() {
//        return golda;
//    }
//
//    public void setGolda(String golda) {
//        this.golda = golda;
//    }
//
//    public String getNikah() {
//        return nikah;
//    }
//
//    public void setNikah(String nikah) {
//        this.nikah = nikah;
//    }
//
//    public String getAlamat() {
//        return alamat;
//    }
//
//    public void setAlamat(String alamat) {
//        this.alamat = alamat;
//    }
//
//    public String getTelepon() {
//        return telepon;
//    }
//
//    public void setTelepon(String telepon) {
//        this.telepon = telepon;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getFoto() {
//        return foto;
//    }
//
//    public void setFoto(String foto) {
//        this.foto = foto;
//    }

}
