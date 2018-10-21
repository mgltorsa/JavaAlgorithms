package DB;

public enum SQLTypes {

    VARCHAR,
    NUMBER,
    DATE;
    
    
    private String info;
    public void setInfo(String info) {
	this.info=info;
    }
    
    public String toString() {
	String top = super.toString()+"("+info+")";
	return top;
	
    }
    
    public static void main(String[] args) {
	SQLTypes c = VARCHAR;
	c.setInfo("a");
	System.out.println(c.toString());
    }
}
