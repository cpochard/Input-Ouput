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
		// Récupère les propriétés du répertoire d'utilisateurs
		// C:/Users/Nom
		String root = System.getProperty("user.dir");
		// On créé un nouvel objet qu'on met dans la variable f
		// File.separator permet de faire un /
		// On le met dans les mêmes propriétés de répertoire que root et on ajoute
		// /MonSousRepertoire
		// C:/Users/Nom/MonSousRepertoire
		File f = new File(root + File.separator + "MonSousRepertoire");
		// Si le fichier f n'existe pas....
		if (!f.exists()) {
			// On crée le répertoire (mkdir)
			f.mkdirs();
			// Ici sous répertoire créé dans
			// C:\Users\Marjorie\Eclipse_Workspace_GitHub/DecouverteIO/MonSousRepertoire
		}
	}

	static void discoverBinary() {
		// On déclare la variable
		FileInputStream fi = null;
		try {
			// On initialise la variable en un nouvel objet avec comme paramètre d'entrée le
			// répertoire
			fi = new FileInputStream("C:\\Users\\Marjorie\\Documents\\test.bin");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// on déclare la variable
		int n = 0;
		// Boucle do / while
		do {
			try {
				// On affecte à la variable n qui va lire le fichier fi
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
			// Fonction pour fermer le flux d'entrée du fichier
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void writeBinaryFile() {
		String msg = "mon message à écrire";
		FileOutputStream os = null;
		try {
			// On créé un nouvel objet de type FileOutputStream
			os = new FileOutputStream("msg.txt", false); // false = écrase le fichier précédent et remplace par"mon
															// message à écrire"
															// true = écrit à la suite
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.write(msg.getBytes()); // Pour écrire, avec chaque caractère qui va en faire une copie en bytes
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.flush(); // On "libère" le fichier qui était en train d'être modifié en byte
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
		//On déclare et on initialise les variables
		BufferedReader br = null;
		String sCurrentLine = null;
		try {
			//On créé un objet qui prend en entrée un nouvel objet
			br = new BufferedReader(new FileReader("msg.txt"));
			//tant que fichier contient une ligne et que cette ligne n'est pas vide, on l'affiche à l'écran
			while ((sCurrentLine = br.readLine()) != null)
				System.out.println(sCurrentLine);
		} catch (IOException e) {
			e.printStackTrace();
		} finally { //dans tous les cas on exécute les instructions suivantes
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
			//On créé un nouvel objet qui prend un entrée le fichier texte à lire
			aFile = new RandomAccessFile ("msg.txt" , "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//On créé une nouvelle variable qui se base sur le aFile créé
		FileChannel inChannel = aFile.getChannel();
		//Buffer est un conteneur, auquel on alloue 1024 caractères
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			//tant qu'il peut lire les données dans le buffer, et que le read renvoie une valeur différente de 0, on lie le contenu du buffer
			while (inChannel.read(buffer) > 0) {
				//On prépare le buffer à la lecture 
				buffer.flip();
				//Va lire chaque caractère du buffer (de 0 à la limite du buffer de 1024)
				for (int i=0 ; i<buffer.limit(); i++) {
					//On récupère ce qu'il y a dans le buffer et on le convertit en caractères
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
