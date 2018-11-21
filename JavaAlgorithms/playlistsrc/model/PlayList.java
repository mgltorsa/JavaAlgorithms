package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PlayList extends Media implements IMedia {

	private LinkedPlayList<IMedia> mediaList;

	public PlayList(String name) {
		super();
		setName(name);
		mediaList = new LinkedPlayList<>();
	}

	public PlayList() {
		this("defPlayList");
	}

	public void addMedia(String filePath) {
		MediaType type = Media.getMediaType(filePath);
		if (type != MediaType.NONE && type != MediaType.M3U && type != MediaType.XSPF) {
			IMedia media = new Media(filePath);
			addMedia(media);
		}
		else {
			addMediasFromPlayList(filePath, type);
		}
	}

	public void addMedia(File file) {
		addMedia(file.getAbsolutePath());
	}

	private void addMedia(IMedia media) {
		mediaList.add(media);
	}

	

	// ARREGLAR, LA IDEA ERA QUE CUANDO SE IMPORTARA UNA PLAYLIST m3u O xspf SE
	// CARGARA
	// TODAS LAS CANCIONES, ESTE METODO ESTA CREANDO UNA PLAYLIST Y LA RETORNA.

	// SE DEJO PARA TESTEAR

	private void addMediasFromPlayList(String playListPath, MediaType type) {
		File playListSrc = new File(playListPath);
		String parent = playListSrc.getParent();
		if (type == MediaType.M3U) {

			try {
				BufferedReader br = new BufferedReader(new FileReader(playListSrc));

				do {
					String path = br.readLine();
					try {
						File f = new File(path);
						if (!f.exists()) {
							f = new File(parent +"/"+ path);
						}

						if (f.exists()) {
							Media media = new Media(f.getAbsolutePath());
							addMedia(media);
						}

					} catch (Exception e) {
						System.out.println("error " + e.getMessage());
					}
				}while(br.ready());
				
				br.close();

			} catch (Exception e) {
				System.out.println(e.getCause() + " : " + e.getMessage());
			}
		}
		
		else if(type==MediaType.XSPF) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				File xspfFile = new File(playListPath);
				Document document = builder.parse(xspfFile);
				NodeList tracks = document.getElementsByTagName("track");
				for (int i = 0; i < tracks.getLength(); i++) {
					Node trackNode = tracks.item(i);
					
					//Title					
					@SuppressWarnings("unused")
					String title = trackNode.getChildNodes().item(0).getTextContent();
					//Location
					String location = trackNode.getChildNodes().item(1).getTextContent();
					
					IMedia m = new Media(location);
					addMedia(m);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	public void XMLExport(String filePath) throws Exception {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = documentFactory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element playList = document.createElement("playlist");
		
		document.appendChild(playList);

		Attr playListVersion = document.createAttribute("version");
		playListVersion.setValue("1");
		Attr playXmlns = document.createAttribute("xmlns");
		playXmlns.setValue("http://xspf.org/ns/0/");
		Attr playXmlnsVlc = document.createAttribute("xmlns:vlc");
		playXmlnsVlc.setValue("http://www.videolan.org/vlc/playlist/ns/0/");

		playList.setAttributeNode(playListVersion);
		playList.setAttributeNode(playXmlns);
		playList.setAttributeNode(playXmlnsVlc);

		Element title = createXmlLabel("title", document, this.toString());

		playList.appendChild(title);

		Element location = createXmlLabel("location", document, getMediaFilePath());

		playList.appendChild(location);

		Element tracklist = createTrackList("trackList", document, getMediaFilePath());

		playList.appendChild(tracklist);

//		Element extension = document.createElement("extension");
//		Attr application = document.createAttribute("application");
//		application.setValue("http://www.videolan.org/vlc/playlist/0");
//
//		playList.appendChild(extension);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		DOMSource domSource = new DOMSource(document);
		// EXAMPLE USING FILE
		String xmlFilePath = filePath;
		File file = new File(xmlFilePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		StreamResult streamResult = new StreamResult(file);
		StreamResult str = new StreamResult(System.out);
		transformer.transform(domSource, streamResult);
		transformer.transform(domSource, str);

		System.out.println(System.getProperty("user.dir"));

	}

	private Element createTrackList(String string, Document document, String playListPath) {
		// IF playList is M3U

		Element trackList = document.createElement("trackList");
		for (IMedia media : mediaList.values()) {
			Element track = document.createElement("track");
			Element title = createXmlLabel("title", document, media.toString());
			Element location = createXmlLabel("location", document, media.getMediaFilePath());

			track.appendChild(title);
			track.appendChild(location);
			trackList.appendChild(track);
		}

		return trackList;
	}

	private Element createXmlLabel(String name, Document document, String content) {
		Element label = document.createElement(name);
		label.setTextContent(content);
		return label;
	}
	
	public String toString() {
		String str = "Title: "+super.toString()+"\n";
		//TODO HACER QUE APAREZCAN LOS ATRIBUTOS
		for(IMedia m : mediaList.values()) {
			str+=m.toString() +":"+m.getMediaFilePath()+":"+m.getType()+"\n";
		}
		return str;
		
	}

	public static void main(String[] args) throws Exception {
		PlayList p = new PlayList("nuevaPlayList");
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int op = fc.showOpenDialog(null);
		if (op == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			p.addMedia(f);
			System.out.println(p.toString());
		}
		
		

	}

}
