package model;

import java.io.File;
import java.util.HashMap;

public class Media implements IMedia {

	private String name;
	private MediaType type;
	private HashMap<String, String> data;
	private String filePath;

	public Media(String name, String filePath, MediaType type) {
		this.name=name;
		this.filePath=filePath;
		this.type=type;
	}

	public Media(String filePath, MediaType type) {
		this(getFileName(filePath),filePath,type);
	}


	public Media(String filePath) {
		this(filePath, getMediaType(filePath));
	}
	
	public Media() {
		
	}

	public static String getFileName(String filePath) {
		
		return new File(filePath).getName();
	}
	
	public static MediaType getMediaType(String filePath) {

		String fileName = filePath;
		char ch;
		int len;
		if (fileName == null || (len = fileName.length()) == 0 || (ch = fileName.charAt(len - 1)) == '/' || ch == '\\'
				|| // in the case of a directory
				ch == '.') { // in the case of . or ..
			return MediaType.NONE;
		}
		int dotInd = fileName.lastIndexOf('.'),
				sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		if (dotInd <= sepInd) {
			return MediaType.NONE;
		} else {
			String ext = fileName.substring(dotInd + 1).toUpperCase();
			return MediaType.valueOf(ext);
		}

	}

	@Override
	public MediaType getType() {
		return type;
	}

	@Override
	public HashMap<String, String> getData() {
		return data;
	}

	@Override
	public String getMediaFilePath() {
		return filePath;
	}

	public void setName(String name) {
		this.name=name;
	}
	
	@Override
	public void setData(HashMap<String, String> data) {
		this.data=data;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
