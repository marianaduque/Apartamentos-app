package Models;

public class UserModel {
    private String name;
    private String correo;
    private String ciudad;
    private String contraseña;

    private UserModel(){}

    public UserModel(String name, String correo, String ciudad, String contraseña){
        this.name = name;
        this.correo = correo;
        this.ciudad = ciudad;
        this.contraseña = contraseña;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public  String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}



