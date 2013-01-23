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
        //获取SD卡的路径  
        rootFile = Environment.getExternalStorageDirectory();  
        currentFile = rootFile;  
        //显示文件列表  
        showDir(rootFile);  
    }   
    
    //根据传入的File参数显示该File所在的文件  
    public void showDir(File pathFile){
    	//ArrayList保存文件目录下每个文件条目，HashMap的键保存文件名，值保存文件路径  
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
        
      //使用SimpleAdapter作为ListActivity的适配器  
        SimpleAdapter sa = new SimpleAdapter(
        		//当前类  
                this,   
                //要显示的资源  
                fileList,   
                //ListActivity的布局文件  
                R.layout.list,  
                //要显示的每一列的名称（这里只显示一列）  
                new String[] { "name", "img" },  
                //每一列对应的布局文件  
                new int[] { R.id.file_name, R.id.img });
        setListAdapter(sa); 
    }
    
  //监听返回键，当用户点击返回键时，返回上层目录  
    @Override  
    public void onBackPressed() {  
        if(currentFile.getPath().equals(rootFile.getPath()))  
        super.onBackPressed();  
        else {  
            currentFile=currentFile.getParentFile();  
            showDir(currentFile);  
        }  
    }  
    
  //监听用户选择的文件，若选择的是图片，则显示，若是文件夹，则进入下一层  
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	String currentPath = (fileList.get(position).get("path")).toString();  
        currentFile = new File(currentPath); 
        if (currentFile.isDirectory()) {  
            showDir(currentFile);  
        } else {  
            //如果是图片，则显示（本例只支持jpg，可以自行添加其他格式）  
            if (currentPath.endsWith(".jpg")) {  
                //通过Intent传递图片路径  
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
