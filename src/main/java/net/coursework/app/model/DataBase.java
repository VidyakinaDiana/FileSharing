package net.coursework.app.model;
//write xml
import net.coursework.app.view.Package;
import net.coursework.app.view.User;
import org.w3c.dom.Text;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
//read xml
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
//work with data
import java.util.ArrayList;

public class DataBase {
	private String filename;
	private ArrayList<User> users;
	private ArrayList<Package> packages;

	
	public DataBase(String filename) {
		users = new ArrayList<User>();
		packages = new ArrayList<Package>();

		this.filename = filename;
		File file = new File(filename);
		if (!file.exists()) { //create xml database
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();
				
				Element rootElement = document.createElement("root");
				Element usersElement = document.createElement("users");
				Element packagesElement = document.createElement("packages");
				Element chatsElement = document.createElement("chats");
				
				document.appendChild(rootElement);
				rootElement.appendChild(usersElement);
				rootElement.appendChild(packagesElement);
				rootElement.appendChild(chatsElement);
				
				Transformer t = TransformerFactory.newInstance().newTransformer();
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(filename)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { //xml database is exists. Read xml database
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(file);
				
				Node root = document.getFirstChild();
				
				NodeList data = root.getChildNodes();
				
				for (int i = 0; i < data.getLength(); i++) {
					if (data.item(i).getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					switch (data.item(i).getNodeName()) {
						case "users": { //fill users base
							NodeList usersNode = data.item(i).getChildNodes();
							for (int j = 0; j < usersNode.getLength(); j++) {
								if (usersNode.item(j).getNodeType() != Node.ELEMENT_NODE) {
									continue;
								}
								String id = ((Element)usersNode.item(j)).getElementsByTagName("id").item(0).getTextContent();
								String name = ((Element)usersNode.item(j)).getElementsByTagName("name").item(0).getTextContent();
								String password = ((Element)usersNode.item(j)).getElementsByTagName("password").item(0).getTextContent();
								users.add(new User(Integer.parseInt(id), name, password));
							}
							break;
						}
						case "packages": { //fill packages base
							NodeList packagesNode = data.item(i).getChildNodes();
							for (int j = 0; j < packagesNode.getLength(); j++) {
								if (packagesNode.item(j).getNodeType() != Node.ELEMENT_NODE) {
									continue;
								}
								String id = ((Element)packagesNode.item(j)).getElementsByTagName("id").item(0).getTextContent();
								String path = ((Element)packagesNode.item(j)).getElementsByTagName("path").item(0).getTextContent();
								packages.add(new Package(Integer.parseInt(id), path));
							}
							break;
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveDataBase() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element rootElement = document.createElement("root");
			Element usersElement = document.createElement("users");
			Element packagesElement = document.createElement("packages");
			Element chatsElement = document.createElement("chats");
			
			document.appendChild(rootElement);
			rootElement.appendChild(usersElement);
			rootElement.appendChild(packagesElement);
			rootElement.appendChild(chatsElement);
			
			for (int i = 0; i < users.size(); i++) {
				Element userElement = document.createElement("user");
				Element idElement = document.createElement("id");
				Element nameElement = document.createElement("name");
				Element passwordElement = document.createElement("password");
				
				usersElement.appendChild(userElement);
				
				userElement.appendChild(idElement);
				Text idText = document.createTextNode(Integer.toString(users.get(i).getId()));
				idElement.appendChild(idText);
				
				userElement.appendChild(nameElement);
				Text nameText = document.createTextNode(users.get(i).getName());
				nameElement.appendChild(nameText);
				
				userElement.appendChild(passwordElement);
				Text passwordText = document.createTextNode(users.get(i).getPassword());
				passwordElement.appendChild(passwordText);
			}
			
			for (int i = 0; i < packages.size(); i++) {
				Element packageElement = document.createElement("package");
				Element idElement = document.createElement("id");
				Element pathElement = document.createElement("path");
				
				packagesElement.appendChild(packageElement);
				
				packageElement.appendChild(idElement);
				Text idText = document.createTextNode(Integer.toString(packages.get(i).getId()));
				idElement.appendChild(idText);
				
				packageElement.appendChild(pathElement);
				Text pathText = document.createTextNode(packages.get(i).getPath());
				pathElement.appendChild(pathText);
			}
			

			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(filename)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printLogData() {
		System.out.println("----- users -----");
		for (int i = 0; i < users.size(); i++) {
			System.out.println("----- user -----");
			System.out.println(users.get(i).getId());
			System.out.println(users.get(i).getName());
			System.out.println(users.get(i).getPassword());
		}
		System.out.println("----- packages -----");
		for (int i = 0; i < packages.size(); i++) {
			System.out.println("----- package -----");
			System.out.println(packages.get(i).getId());
			System.out.println(packages.get(i).getPath());
		}

	}
	
	public String getUserById(int id) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == id) {
				return users.get(i).getName();
			}
		}
		return "";
	}
	
	public ArrayList<User> getUsers() {
		return this.users;
	}
	
	public ArrayList<Package> getPackages() {
		return this.packages;
	}
	

}
