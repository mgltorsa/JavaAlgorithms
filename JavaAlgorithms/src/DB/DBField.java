package DB;

public class DBField {

    private String name;
    private SQLTypes type;
    
    public DBField(String name, SQLTypes type,String typeInfo) {
	this.name=name;
	this.type=type;
	this.type.setInfo(typeInfo);
    }
    
    //INVOKÉ AL NUEVO METODO AQUI
    public String toString() {
	//POR LO GENERAL LOS FIELDS EN UNA BASE DE DATOS SON ASI: NombreDeField TipoDeField(LargoDeField);
	//EJEMPLO FIELD NOMBRE = nombre VARCHAR(2) --> VARCHAR(2) QUIERE DECIR UNA CADENA DE 2 CARACTERES MAXIMO
	
	return name+" "+type.getStringFormat();
	
    }
}
