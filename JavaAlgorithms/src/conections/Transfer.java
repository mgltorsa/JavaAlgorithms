/**
 * 
 */
package conections;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;

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
	String[] infoDownload = in.readUTF().split(":");
	long sizeFile = Long.parseLong(infoDownload[1]);
	String type = "";
	if (infoDownload.length > 2) {
	    type = infoDownload[2];
	}
	out.writeUTF("accepted");
	String fileName = in.readUTF();
	File file = new File(directory + "fromServer " + fileName);
	if (!file.exists()) {
	    file.createNewFile();
	}
	if (type.equals("image")) {	    
	    BufferedImage image = ImageIO.read(socket.getInputStream());
	    ImageIO.write(image, "png", file);
	} else {
	    BufferedOutputStream outputFile = new BufferedOutputStream(new FileOutputStream(file));
	    DataInputStream inputFile = new DataInputStream(socket.getInputStream());
	    byte[] receivedData = new byte[(int) sizeFile];
	    int inByte = 0;
	    inByte = inputFile.read(receivedData,0,(int) sizeFile);
	    outputFile.write(receivedData, 0, inByte);
	    outputFile.flush();

	    outputFile.close();
	}
	socket.getOutputStream().flush();
	
	System.out.println("finalizo descarga de: "+fileName);

    }

   
    private BufferedImage getImage(File file) {
	BufferedImage img = null;
	try {
	    return ImageIO.read(file);
	} catch (IOException e) {
	    img = null;
	}
	return img;
    }

    public void transfer(String fileName) throws Exception {
	File file = new File(root + fileName);
	BufferedImage image = getImage(file);
	String otherInfo = "";
	if (image != null) {
	    otherInfo = "image";
	}
	DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	out.writeUTF("downloading:" + file.length() + ":" + otherInfo);
	DataInputStream inputResponse = new DataInputStream(socket.getInputStream());
	out.writeUTF(fileName);

	String res = inputResponse.readUTF();
	if (image == null) {
	    if (res.equals("accepted")) {
		BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream fileOutput = new BufferedOutputStream(socket.getOutputStream());

		byte[] data = new byte[(int) file.length()];
		for (int in = fileInput.read(data); in != -1; in = fileInput.read(data)) {
		    fileOutput.write(data, 0, in);
		}
		fileOutput.flush();
		fileInput.close();
	    }
	} else {
	    ImageIO.write(image, "png", socket.getOutputStream());
	}
	socket.getOutputStream().flush();
	System.out.println("Finalizo transferencia a: "+socket.getInetAddress().getHostAddress());
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
