package cmpnts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

	public Writer() {
	}
	
	public void resetHighScore() {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File("res/dat/highscore.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int p = 0; p < 10; p++) {
			try {
				fw.write("------- 0 01.01.1970\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveAll(String[] strings) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File("res/dat/highscore.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int p = 0; p < 10; p++) {
			try {
				fw.write(strings[p * 3] + "\n");
				fw.write(strings[p * 3 + 1] + "\n");
				fw.write(strings[p * 3 + 2] + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
