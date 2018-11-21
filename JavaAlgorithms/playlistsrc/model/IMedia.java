package model;

import java.util.HashMap;

public interface IMedia {
	
	public MediaType getType();
	public HashMap<String,String> getData();
	public String getMediaFilePath();
	public void setData(HashMap<String,String> data);
}
