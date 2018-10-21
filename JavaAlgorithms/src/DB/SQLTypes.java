package DB;

public enum SQLTypes {

    // VARCHAR ES EL TIPO SQL PARA CADENAS O STRINGS
    VARCHAR,

    // NUMBER ES EL TIPO SQL PARA NUMEROS
    NUMBER,
    
    INTEGER,

    // DATE ES EL TIPO SQL PARA FECHAS.
    DATE;

    private String info;

    public void setInfo(String info) {
	this.info = info;
    }

    //AÑADÍ ESTE METODO NUEVO
    public String getStringFormat() {
	String top = toString();
	if (info != null) {
	    top += "(" + info + ")";
	}
	return top;
    }

    public static void main(String[] args) {
	SQLTypes c = VARCHAR;
	c.setInfo("a");
	System.out.println(c.getStringFormat());
    }
}
