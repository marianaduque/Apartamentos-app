package Models;

public class ApartmentsModel {
    private String ciudad;
    private String pais;
    private String direccion;
    private String valor;
    private  String resena;
    private String idPropietario;

    public ApartmentsModel() {
    }

    public ApartmentsModel(String ciudad, String pais, String direccion, String valor, String resena, String idPropietario) {
        this.ciudad = ciudad;
        this.pais = pais;
        this.direccion = direccion;
        this.valor = valor;
        this.resena = resena;
        this.idPropietario = idPropietario;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getResena() {
        return resena;
    }

    public void setResena(String resena) {
        this.resena = resena;
    }

    public String getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(String idPropietario) {
        this.idPropietario = idPropietario;
    }
}
