package com.cpochard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.omg.CORBA.portable.OutputStream;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//gestionFichier();
		//discoverBinary();
		//writeBinaryFile();
		//readFile();
		readFileBis();
		
	}

	private static void gestionFichier() {
		// R�cup�re les propri�t�s du r�pertoire d'utilisateurs
		// C:/Users/Nom
		String root = System.getProperty("user.dir");
		// On cr�� un nouvel objet qu'on met dans la variable f
		// File.separator permet de faire un /
		// On le met dans les m�mes propri�t�s de r�pertoire que root et on ajoute
		// /MonSousRepertoire
		// C:/Users/Nom/MonSousRepertoire
		File f = new File(root + File.separator + "MonSousRepertoire");
		// Si le fichier f n'existe pas....
		if (!f.exists()) {
			// On cr�e le r�pertoire (mkdir)
			f.mkdirs();
			// Ici sous r�pertoire cr�� dans
			// C:\Users\Marjorie\Eclipse_Workspace_GitHub/DecouverteIO/MonSousRepertoire
		}
	}

	static void discoverBinary() {
		// On d�clare la variable
		FileInputStream fi = null;
		try {
			// On initialise la variable en un nouvel objet avec comme param�tre d'entr�e le
			// r�pertoire
			fi = new FileInputStream("C:\\Users\\Marjorie\\Documents\\test.bin");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// on d�clare la variable
		int n = 0;
		// Boucle do / while
		do {
			try {
				// On affecte � la variable n qui va lire le fichier fi
				n = fi.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(n); // si on veut afficher le contenu du fichier (attention s'affiche en code ASCI)
			// Some stuff
			// Tant qu'il peut lire le program il va le faire, quand fini de lire il renvoie
			// -1
		} while (n != -1);
		try {
			// Fonction pour fermer le flux d'entr�e du fichier
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void writeBinaryFile() {
		String msg = "mon message � �crire";
		FileOutputStream os = null;
		try {
			// On cr�� un nouvel objet de type FileOutputStream
			os = new FileOutputStream("msg.txt", false); // false = �crase le fichier pr�c�dent et remplace par"mon
															// message � �crire"
															// true = �crit � la suite
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.write(msg.getBytes()); // Pour �crire, avec chaque caract�re qui va en faire une copie en bytes
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.flush(); // On "lib�re" le fichier qui �tait en train d'�tre modifi� en byte
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.close(); // On ferme le fichier
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void readFile() {
		//On d�clare et on initialise les variables
		BufferedReader br = null;
		String sCurrentLine = null;
		try {
			//On cr�� un objet qui prend en entr�e un nouvel objet
			br = new BufferedReader(new FileReader("msg.txt"));
			//tant que fichier contient une ligne et que cette ligne n'est pas vide, on l'affiche � l'�cran
			while ((sCurrentLine = br.readLine()) != null)
				System.out.println(sCurrentLine);
		} catch (IOException e) {
			e.printStackTrace();
		} finally { //dans tous les cas on ex�cute les instructions suivantes
			try {
				//si br pas null on le ferme
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	static void readFileBis () {
		RandomAccessFile aFile = null;
		try {
			//On cr�� un nouvel objet qui prend un entr�e le fichier texte � lire
			aFile = new RandomAccessFile ("msg.txt" , "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//On cr�� une nouvelle variable qui se base sur le aFile cr��
		FileChannel inChannel = aFile.getChannel();
		//Buffer est un conteneur, auquel on alloue 1024 caract�res
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			//tant qu'il peut lire les donn�es dans le buffer, et que le read renvoie une valeur diff�rente de 0, on lie le contenu du buffer
			while (inChannel.read(buffer) > 0) {
				//On pr�pare le buffer � la lecture 
				buffer.flip();
				//Va lire chaque caract�re du buffer (de 0 � la limite du buffer de 1024)
				for (int i=0 ; i<buffer.limit(); i++) {
					//On r�cup�re ce qu'il y a dans le buffer et on le convertit en caract�res
					System.out.print((char)buffer.get());
				}
				//On nettoie buffer
				buffer.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//On ferme le buffer
			inChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			aFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
