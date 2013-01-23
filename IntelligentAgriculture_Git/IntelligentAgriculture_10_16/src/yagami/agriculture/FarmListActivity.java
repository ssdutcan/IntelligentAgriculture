package yagami.agriculture;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import yagami.agriculture.service.DownloadService;
import yagami.download.HttpDownloader;
import yagami.model.FarmInfo;
import yagami.xml.FarmListContentHandler;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FarmListActivity extends ListActivity {
	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	private List<FarmInfo> farmInfos = null;
//	private String ipv4 = "10.0.0.8";
//	private String ipv4 = "10.10.250.211";
	private String ipv4 = "10.10.250.195";
	private String phoneNumber = "15840891610";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_farm_list);
        updateListView();
    }

	private SimpleAdapter buildSimpleAdapter(List<FarmInfo> farmInfos){
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for(Iterator iterator = farmInfos.iterator(); iterator.hasNext();){
			FarmInfo farmInfo = (FarmInfo) iterator.next();
			HashMap<String,String>  map = new HashMap<String,String>();
			map.put("name", farmInfo.getName());
			list.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.farminfo_item, new String[]{"farm_name"}
		, new int[]{R.id.farm_name});
		return simpleAdapter;
	}
	private void updateListView() {
		String xml = downloadXML("http://"+ipv4+":8080/farm/"+phoneNumber+"/farm.xml");
		farmInfos = parse(xml);
		SimpleAdapter simpleAdapter = buildSimpleAdapter(farmInfos);
		setListAdapter(simpleAdapter);
	}
	private String downloadXML(String urlStr) {
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}
	private List<FarmInfo> parse(String xmlStr){
		SAXParserFactory saxParseFactory = SAXParserFactory.newInstance();
		List<FarmInfo> infos = new ArrayList<FarmInfo>();
		try{
			XMLReader xmlReader = saxParseFactory.newSAXParser().getXMLReader();
			FarmListContentHandler farmListContentHandler = new FarmListContentHandler(infos);
			xmlReader.setContentHandler(farmListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for(Iterator iterator = infos.iterator(); iterator.hasNext();) {
				FarmInfo farmInfo = (FarmInfo) iterator.next();
//				System.out.println(farmInfo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return infos;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		FarmInfo farmInfo = farmInfos.get(position);
		Intent intent = new Intent();
		intent.putExtra("farmInfo", farmInfo);
		intent.setClass(this, DownloadService.class);
		startService(intent);
//		System.out.println("farmInfo--->" + farmInfo);
		super.onListItemClick(l, v, position, id);
	}
	
    
}