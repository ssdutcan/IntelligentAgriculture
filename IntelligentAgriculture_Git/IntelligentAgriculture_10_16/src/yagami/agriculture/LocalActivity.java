package yagami.agriculture;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import yagami.download.HttpDownloader;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class LocalActivity extends ListActivity{
	private File rootFile = null;  
    private File currentFile = null;  
    private ArrayList<HashMap<String, Object>> fileList = null;  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_main);
        //��ȡSD����·��  
        rootFile = Environment.getExternalStorageDirectory();  
        currentFile = rootFile;  
        //��ʾ�ļ��б�  
        showDir(rootFile);  
    }   
    
    //���ݴ����File������ʾ��File���ڵ��ļ�  
    public void showDir(File pathFile){
    	//ArrayList�����ļ�Ŀ¼��ÿ���ļ���Ŀ��HashMap�ļ������ļ�����ֵ�����ļ�·��  
        fileList = new ArrayList<HashMap<String, Object>>();
        if (pathFile != null && pathFile.exists()) {
        	File[] files = pathFile.listFiles();

        	for (File f : files) {
        		if((!f.getName().startsWith("."))&&(!f.getName().equals("DCIM"))&&(!f.getName().equals("LOST.DIR"))){
        			System.out.println(f.getName());
        			HashMap<String, Object> map1 = new HashMap<String, Object>();
        			HashMap<String, Object> map2 = new HashMap<String, Object>();
                    map1.put("name", f.getName());  
                    map1.put("path", f.getPath());
                    map1.put("img", R.drawable.folder);
                    fileList.add(map1);
                   
        		}
        	}
        }
        
      //ʹ��SimpleAdapter��ΪListActivity��������  
        SimpleAdapter sa = new SimpleAdapter(
        		//��ǰ��  
                this,   
                //Ҫ��ʾ����Դ  
                fileList,   
                //ListActivity�Ĳ����ļ�  
                R.layout.list,  
                //Ҫ��ʾ��ÿһ�е����ƣ�����ֻ��ʾһ�У�  
                new String[] { "name", "img" },  
                //ÿһ�ж�Ӧ�Ĳ����ļ�  
                new int[] { R.id.file_name, R.id.img });
        setListAdapter(sa); 
    }
    
  //�������ؼ������û�������ؼ�ʱ�������ϲ�Ŀ¼  
    @Override  
    public void onBackPressed() {  
        if(currentFile.getPath().equals(rootFile.getPath()))  
        super.onBackPressed();  
        else {  
            currentFile=currentFile.getParentFile();  
            showDir(currentFile);  
        }  
    }  
    
  //�����û�ѡ����ļ�����ѡ�����ͼƬ������ʾ�������ļ��У��������һ��  
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	String currentPath = (fileList.get(position).get("path")).toString();  
        currentFile = new File(currentPath); 
        if (currentFile.isDirectory()) {  
            showDir(currentFile);  
        } else {  
            //�����ͼƬ������ʾ������ֻ֧��jpg�������������������ʽ��  
            if (currentPath.endsWith(".jpg")) {  
                //ͨ��Intent����ͼƬ·��  
                Intent intent = new Intent();  
                intent.putExtra("picPath", currentPath);  
                intent.setClass(this, ImageViewerActivity.class);  
                this.startActivity(intent);  
            } else if(currentPath.endsWith(".xml")) {
            	Intent intent = new Intent();
            	intent.putExtra("xmlPath", currentPath);
            	intent.setClass(this, DataListActivity.class);
            	this.startActivity(intent);
            }
        }  
    }
    
    private String downloadXML(String urlStr) {
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}
}
