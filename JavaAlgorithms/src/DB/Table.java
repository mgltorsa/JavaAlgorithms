package DB;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.derby.jdbc.EmbeddedDriver;

public class Table {

    private PrimaryKey[] keys;
    private ForeignKey[] foreigns;
    private DBField[] fields;
    private String name;

    public Table(String name, DBField... fields) {
	this(name, null, null, fields);
    }

    public Table(String name, PrimaryKey[] keys, ForeignKey[] foreigns, DBField... fields) {
	this.name = name;
	if (keys != null) {
	    setPrimaryKeys(keys);
	}
	if (foreigns != null) {
	    setForeignKeys(foreigns);
	}
	this.fields = fields;
    }

    public void setPrimaryKeys(PrimaryKey[] keys) {
	this.keys = keys;
	if (keys.length > 1) {
//	    alterTable("ADD CONSTRAINT PK_" + name + " PRIMARY KEY ", keys.toStringArray());
	} else if (keys.length == 1) {
//	    alterTable("ADD PRIMARY KEY ", keys.toStringArray());
	}
    }

    private void alterTable(String alterCommand, String[] key) {
	String alterSql = "ALTER TABLE " + name + " ( ";
	for (int i = 0; i < key.length - 1; i++) {
	}

	EmbeddedDataSource a = new EmbeddedDataSource();
	EmbeddedDriver b = new EmbeddedDriver();

    }

    private void setForeignKeys(ForeignKey[] foreigns) {
	this.foreigns = foreigns;

    }
    
    public static void main(String[] args) {
	System.out.println("test");
	String name = "Shakugan no Shana III  ";
	for (int i = 1; i <= 24; i++) {
	    if(i<10) {
		    System.out.println(name+"0"+i+".mp4");

	    }else {
	    System.out.println(name+i+".mp4");}
	}
    }

}
