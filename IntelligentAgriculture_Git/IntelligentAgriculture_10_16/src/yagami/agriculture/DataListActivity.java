package yagami.agriculture;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import yagami.model.DataInfo;
import yagami.xml.DataListContentHandler;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DataListActivity extends ListActivity{
	private List<DataInfo> dataInfos = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_list);
		Intent receiveIntent=getIntent(); 
		String xmlPath=receiveIntent.getStringExtra("xmlPath");
		System.out.println("xmlPath--->"+xmlPath);
		updateListView(xmlPath);
	}
	private String downloadXML(String filePath){
		String result = null;
		StringBuffer sb = new StringBuffer();
		try{
			File myFile = new File(filePath);
			FileInputStream inputStream = new FileInputStream(myFile);
			DataInputStream dataIO = new DataInputStream(inputStream);
			String strLine = null;
			while((strLine = dataIO.readLine())!= null){
        		sb.append(strLine);
        	}
			result = sb.toString();
        	dataIO.close();
        	inputStream.close();
        	
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("xml--->"+result);
		return result;
	}
	
	private List<DataInfo> parse(String xmlStr){
		SAXParserFactory saxParseFactory = SAXParserFactory.newInstance();
		List<DataInfo> infos = new ArrayList<DataInfo>();
		try{
			XMLReader xmlReader = saxParseFactory.newSAXParser().getXMLReader();
			DataListContentHandler dataListContentHandler = new DataListContentHandler(infos);
			xmlReader.setContentHandler(dataListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for(Iterator iterator = infos.iterator(); iterator.hasNext();) {
				DataInfo dataInfo = (DataInfo) iterator.next();
				System.out.println("dataInfo--->"+dataInfo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return infos;
	}
	
	private void updateListView(String filePath) {
		String xml = downloadXML(filePath);
		System.out.println("xml--->"+xml);
		dataInfos = parse(xml);
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for(Iterator iterator = dataInfos.iterator(); iterator.hasNext();){
			DataInfo dataInfo = (DataInfo) iterator.next();
			HashMap<String,String>  map = new HashMap<String,String>();
			map.put("id", dataInfo.getId());
			map.put("time", dataInfo.getTime());
			map.put("CO2", dataInfo.getCO2());
			map.put("temperature", dataInfo.getTemperature());
			map.put("humidity", dataInfo.getHumidity());
			map.put("light", dataInfo.getLight());
			list.add(map);
		}
		MyAdapter listAdapter = new MyAdapter(this, list,
				R.layout.user, new String[] { "id", "time", "CO2"
				, "temperature", "humidity", "light" },
				new int[] { R.id.farm_id, R.id.farm_time,
				R.id.farm_CO2, R.id.farm_temperature,R.id.farm_humidity, R.id.farm_light});
		setListAdapter(listAdapter);
		
	}
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		System.out.println("id----------------" + id);
		System.out.println("position----------" + position);
	}
}
