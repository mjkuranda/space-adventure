package cmpnts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Reader {

	public Reader() {
	}
	
//	public String[] readLanguage(String shortcutLang, String[] lang) {
//		File file = new File("res/dat/lang/" + shortcutLang + ".txt");
//		BufferedReader br = null;
//		
//		List<String> strs = new LinkedList<String>();
//		
//		try {
//		br = new BufferedReader(new FileReader(file));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		if(br != null) {
//			String str = null;
//
//			while(str != null) {
//				try {
//					str = br.readLine();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				strs.add(str);
//			}
//		}
//		
//		// defining the length of array
//		lang = new String[strs.size()];
//		
//		// saving the lines to the table
//		for(int l = 0; l < strs.size(); l++) {
//			lang[l] = strs.get(l);
//			System.out.println("Lang: " + lang[l]);
//		}
//		System.out.println("Size: " + strs.size());
//		
//		return lang;
//	}
	
	public String[] readLanguage(String shortcutLang, String[] lang) {
		File file = new File("res/dat/lang/" + shortcutLang + ".txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<String> strs = new LinkedList<String>();
		
		while(sc.hasNextLine()) {
			strs.add(sc.nextLine());
		}
		
		// defining the length of array
		lang = new String[strs.size()];
		
		// saving the lines to the table
		for(int l = 0; l < strs.size(); l++) {
			lang[l] = strs.get(l);
		}
		
		return lang;
	}
	
	public String[] prescribeLines(String[] s1) {
		String[] s2 = new String[s1.length];
		for(int l = 0; l < s1.length; l++) {
			String s = s1[l];
			s2[l] = s;
		}
		return s2;
	}
	
	public String[] readPlayers() {
		String[] strs = new String[30];
		
		File file = new File("res/dat/highscore.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringTokenizer token;
		int e = 0;
		
		while(scan.hasNextLine()) {
			token = new StringTokenizer(scan.nextLine(), " ");
			while(e != 30 && token.hasMoreElements()) {
				strs[e] = token.nextToken();
				e++;
			}
		}
		
		scan.close();
		
		return strs;
	}

}
