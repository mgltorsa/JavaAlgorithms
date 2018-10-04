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
import java.io.InputStream;
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
	
	File file = createFile(directory,fileName);
	if (type.equals("image")) {
	    BufferedImage image = getImage(socket.getInputStream());
	    ImageIO.write(image, "png", file);
	} else {

	    downloadFile((int) sizeFile,file);

	}
	socket.getOutputStream().flush();

	System.out.println("finalizo descarga de: " + fileName);

    }

    /**
     * @param directory
     * @param fileName
     * @return
     * @throws IOException 
     */
    private File createFile(String directory, String fileName) throws IOException {
	File file = new File(
		directory + "from-" + socket.getInetAddress().getHostAddress().replace(".", "-") + " " + fileName);
	if (!file.exists()) {

	    file.createNewFile();
	}
	return file;
    }

    /**
     * @throws Exception 
     * 
     */
    private void downloadFile(int sizeFile, File file) throws Exception {
	BufferedOutputStream outputFile = new BufferedOutputStream(new FileOutputStream(file));
	DataInputStream inputFile = new DataInputStream(socket.getInputStream());
	byte[] receivedData = new byte[(int) sizeFile];
	int inByte = 0;
	inByte = inputFile.read(receivedData, 0, (int) sizeFile);
	outputFile.write(receivedData, 0, inByte);
	outputFile.flush();

	outputFile.close();

    }

    /**
     * @param inputStream
     * @return
     * @throws IOException
     */
    private BufferedImage getImage(InputStream inputStream) throws IOException {
	BufferedImage image = ImageIO.read(inputStream);

	return image;
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

	writeTransferInfo(fileName, file, otherInfo);
	String response = waitResponseTransfer();

	if (image == null) {
	    if (response.equals("accepted")) {
		transferFile(file);
	    }
	} else {
	    ImageIO.write(image, "png", socket.getOutputStream());
	}
	socket.getOutputStream().flush();
	System.out.println("Finalizo transferencia a: " + socket.getInetAddress().getHostAddress());
    }

    /**
     * @return
     * @throws IOException
     */
    private String waitResponseTransfer() throws IOException {
	DataInputStream inputResponse = new DataInputStream(socket.getInputStream());
	return inputResponse.readUTF();
    }

    /**
     * @param file
     * @param otherInfo
     * @throws IOException
     */
    private void writeTransferInfo(String fileName, File file, String otherInfo) throws IOException {
	DataOutputStream out = new DataOutputStream(socket.getOutputStream());
	out.writeUTF("transfering:" + file.length() + ":" + otherInfo);
	out.writeUTF(fileName);

    }

    /**
     * @param file
     * @throws IOException
     */
    private void transferFile(File file) throws IOException {
	BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
	BufferedOutputStream fileOutput = new BufferedOutputStream(socket.getOutputStream());

	byte[] data = new byte[(int) file.length()];
	for (int in = fileInput.read(data); in != -1; in = fileInput.read(data)) {
	    fileOutput.write(data, 0, in);
	}
	fileOutput.flush();
	fileInput.close();

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
