package yagami.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
	private String SDCardRoot;
	
	public FileUtils() {
		this.SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		
	}
	
	public File createFileInSDCard(String fileName, String dir) throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		System.out.println("file--->"+file);
		file.createNewFile();
		return file;
	}
	public String getSDPATH() {
		return SDCardRoot;
	}
	public File createSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		System.out.println(dirFile.mkdirs());
		return dirFile;
	}
	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + fileName);
		return file.exists();
	}
	public void deleteFileFromSD (String path, String fileName){
		File file = new File(SDCardRoot + path +fileName);
		if(file.exists()){
			file.delete();
		} else {
			System.out.println("File not exist");
		}
	}
	public File write2SDFromInput(String path, String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try{
			createSDDir(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			byte buffer [] = new byte[4 * 1024];
			int temp;
			while((temp = input.read(buffer)) != -1){
				output.write(buffer, 0 ,temp);
			}
			output.flush();
		} catch (Exception e){
			e.printStackTrace();
		} 
		finally {
			try{
				output.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		} 
		return file;
	}
	
}
