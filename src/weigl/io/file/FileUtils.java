package weigl.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileUtils {

	public static String readFile(String file) throws IOException {
		File f = new File(file);
		return readFile(f);
	}

	private static String readFile(File file) throws IOException {
		return readFile(file, "utf-8");
	}

	private static String readFile(File file, String charset) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, charset));
		StringBuilder sb = new StringBuilder((int) file.length());
		String tmp;
		while((tmp=br.readLine())!=null)
			sb.append(tmp).append('\n');
		
		return sb.toString();
	
	}

}
