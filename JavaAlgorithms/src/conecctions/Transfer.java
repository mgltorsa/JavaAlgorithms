/**
 * 
 */
package conecctions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

/**
 * @author Miguel
 *
 */
public class Transfer {

    public static String root = "./data/";

    private Socket socket;

    /**
     * @param socket
     * @throws Exception
     */
    public Transfer(Socket socket) throws Exception {
	this.socket = socket;
    }

    public void download() throws Exception {
	download(root);
    }

    public void download(String directory) throws Exception {
	DataInputStream in = new DataInputStream(socket.getInputStream());
	DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	long sizeFile = Long.parseLong(in.readUTF().split(":")[1]);
	out.writeUTF("accepted");
	String fileName = in.readUTF();
	File file = new File(directory + "fromServer" + fileName);
	if (!file.exists()) {
	    file.createNewFile();
	}
	BufferedOutputStream outputFile = new BufferedOutputStream(new FileOutputStream(file));
	DataInputStream inputFile = new DataInputStream(socket.getInputStream());
	byte[] receivedData = new byte[(int) sizeFile];

	int inByte = 0;
	inByte = inputFile.read(receivedData);
	outputFile.write(receivedData, 0, inByte);
	outputFile.flush();

	outputFile.close();
	System.out.println("finish download");

    }

    public void transfer(String fileName) throws Exception {
	File file = new File(root + fileName);
	DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	out.writeUTF("downloading:" + file.length());
	DataInputStream inputResponse = new DataInputStream(socket.getInputStream());
	String res = inputResponse.readUTF();
	if (res.equals("accepted")) {
	    out.writeUTF(fileName);
	    BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
	    BufferedOutputStream fileOutput = new BufferedOutputStream(socket.getOutputStream());

	    byte[] data = new byte[(int) file.length()];
	    for (int in = fileInput.read(data); in != -1; in = fileInput.read(data)) {
		fileOutput.write(data, 0, in);
	    }
	    fileOutput.flush();
	    fileInput.close();
	}
    }

    /**
     * @param string
     * @return
     */
    public static boolean existFile(String path) {
	File file = new File(root + path);
	return file.exists();
    }

}
