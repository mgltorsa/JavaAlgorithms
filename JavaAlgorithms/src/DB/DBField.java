package DB;

public class DBField {

    private String name;
    private SQLTypes type;
    
    public DBField(String name, SQLTypes type,String typeInfo) {
	this.name=name;
	this.type=type;
	this.type.setInfo(typeInfo);
    }
    
    public String toString() {
	return name+" "+type.toString();
	
    }
}
