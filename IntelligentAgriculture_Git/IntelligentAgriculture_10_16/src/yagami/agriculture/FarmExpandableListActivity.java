package yagami.agriculture;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import yagami.agriculture.service.DownloadXMLService;
import yagami.download.HttpDownloader;
import yagami.model.DataInfo;
import yagami.model.FarmInfo;
import yagami.xml.DataListContentHandler;
import yagami.xml.FarmListContentHandler;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

public class FarmExpandableListActivity extends ExpandableListActivity{

	private List<FarmInfo> farmInfos = null;
	List<Map<String,String>> groups = null;
	List<List<Map<String, String>>> childs = null;
//	private String ipv4 = "10.0.0.8";
//	private String ipv4 = "10.10.250.211";
	private String ipv4 = "10.10.250.195";
	private String phoneNumber = "15840891610";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.farmexpandable);
		updateExpandListView();
	}
	
	private void updateExpandListView() {
		String xml_farm = downloadXML("http://"+ipv4+":8080/farm/"+phoneNumber+"/farm.xml");
		farmInfos = parse_FarmInfo(xml_farm);
		SimpleExpandableListAdapter sela = buildSimpleAdapter();
		setListAdapter(sela);
	}

	private SimpleExpandableListAdapter buildSimpleAdapter(){
		groups = new ArrayList<Map<String,String>>();
		childs = new ArrayList<List<Map<String, String>>>();
		
		for(Iterator iterator = farmInfos.iterator(); iterator.hasNext();){
			FarmInfo farmInfo = (FarmInfo) iterator.next();
			HashMap<String,String>  group = new HashMap<String,String>();
			group.put("group", farmInfo.getName());
			System.out.println("group--->"+farmInfo.getName());
			groups.add(group);
			
			List<Map<String,String>> child = new ArrayList<Map<String,String>>();
			Map<String,String> childData1 = new HashMap<String,String>();
	        Map<String,String> childData2 = new HashMap<String,String>();
	        childData1.put("child", "C-T-H-L Information");
	        childData2.put("child", "Download");
	        child.add(childData1);
	        child.add(childData2);
	        childs.add(child);
		}
		SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
				this, groups, R.layout.group, new String[]{"group"},new int[]{R.id.groupTo}, 
				childs, R.layout.child, new String[]{"child"}, new int[]{R.id.childTo}
		);
		return sela;
	}
	private String downloadXML(String urlStr) {
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}

	private List<DataInfo> parse_DataInfo(String xmlStr){
		SAXParserFactory saxParseFactory = SAXParserFactory.newInstance();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		try{
			XMLReader xmlReader = saxParseFactory.newSAXParser().getXMLReader();
			DataListContentHandler dataListContentHandler = new DataListContentHandler(infos);
			xmlReader.setContentHandler(dataListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			
			for(Iterator iterator = infos.iterator(); iterator.hasNext();) {
				DataInfo dataInfo = (DataInfo) iterator.next();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	private List<FarmInfo> parse_FarmInfo(String xmlStr){
		SAXParserFactory saxParseFactory = SAXParserFactory.newInstance();
		List<FarmInfo> infos = new ArrayList<FarmInfo>();
		try{
			XMLReader xmlReader = saxParseFactory.newSAXParser().getXMLReader();
			FarmListContentHandler farmListContentHandler = new FarmListContentHandler(infos);
			xmlReader.setContentHandler(farmListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for(Iterator iterator = infos.iterator(); iterator.hasNext();) {
				FarmInfo farmInfo = (FarmInfo) iterator.next();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		String temp_xml = null;
		List<DataInfo> temp_dataInfos = null;
		DataInfo temp_dataInfo = null;
		FarmInfo temp_farmInfo = null;
		
		if(childPosition==0){
			temp_xml = downloadXML("http://" + ipv4 + ":8080/farm/"+ phoneNumber +"/"+farmInfos.get(groupPosition).getName()+".xml");
			temp_dataInfos = parse_DataInfo(temp_xml);
			for(Iterator iterator = temp_dataInfos.iterator(); iterator.hasNext();) {
				temp_dataInfo = (DataInfo) iterator.next();
			}
			Toast toast = new Toast(this);
			toast = Toast.makeText(this, "Id: "+temp_dataInfo.getId()+ "\nTime: " +temp_dataInfo.getTime() 
					+"\nCO2: "+temp_dataInfo.getCO2()+"\nTemperature: "+temp_dataInfo.getTemperature()
					+"\nHumidity: "+temp_dataInfo.getHumidity()+"\nLight: "+temp_dataInfo.getLight(), Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			
		} 	else if (childPosition==1){
			Toast toast = new Toast(this);
			toast = Toast.makeText(this, "Download Finish", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			
			temp_farmInfo = this.farmInfos.get(groupPosition);
			Intent intent = new Intent();
			intent.putExtra("Information", temp_farmInfo);
			intent.setClass(this, DownloadXMLService.class);
			startService(intent);
		} 
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}
	
}
