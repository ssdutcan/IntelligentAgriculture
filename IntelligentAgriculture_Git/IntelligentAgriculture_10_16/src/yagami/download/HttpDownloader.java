package yagami.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import yagami.utils.FileUtils;

public class HttpDownloader {
	private URL url = null;
	
	/**����URL�����ļ���ǰ��������ļ����е��������ı��������ķ���ֵ�����ļ����е�����
	 * 1.����һ��URL����
	 * 2.ͨ��URL���󣬴���һ��HttpURLConnection����
	 * 3.�õ�InputStream
	 * 4.��InputStream���ж�ȡ����
	 */
	public String download(String urlStr){
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer= null;
		try {
			//����URL����
			url = new URL(urlStr);
			//����Http����
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			//ʹ��IO����ȡ����
			buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while((line = buffer.readLine()) != null){
				sb.append(line);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public int downFile(String urlStr, String path, String fileName) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			if (fileUtils.isFileExist(fileName, path)) {
				fileUtils.deleteFileFromSD(path, fileName);
			} 
				inputStream = getInputStreamFromUrl(urlStr);
				File resultFile = fileUtils.write2SDFromInput(path,fileName, inputStream);
				if (resultFile == null) {
					return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	public InputStream getInputStreamFromUrl(String urlStr)
		throws MalformedURLException, IOException {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			InputStream inputStream = urlConn.getInputStream();
			return inputStream;
	}
	
}
