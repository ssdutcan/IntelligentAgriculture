package yagami.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import yagami.model.DataInfo;

public class DataListContentHandler extends DefaultHandler{
	private List<DataInfo> infos = null;
	private DataInfo dataInfo = null;
	private String tagName = null;
	
	public DataListContentHandler(List<DataInfo> infos) {
		super();
		this.infos = infos;
	}

	public List<DataInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<DataInfo> infos) {
		this.infos = infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		String temp = new String(ch, start, length);
		if(tagName.equals("id")) {
			dataInfo.setId(temp);
		} else if(tagName.equals("time")){
			dataInfo.setTime(temp);
		} else if(tagName.equals("CO2")){
			dataInfo.setCO2(temp);
		} else if(tagName.equals("temperature")){
			dataInfo.setTemperature(temp);
		} else if(tagName.equals("humidity")){
			dataInfo.setHumidity(temp);
		} else if(tagName.equals("light")){
			dataInfo.setLight(temp);
		} 
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		// TODO Auto-generated method stub
		if(name.equals("farm")) {
			infos.add(dataInfo);
//			System.out.println("this info: "+dataInfo);
		}
		tagName = "";
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
//		System.out.println("start--->");
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		this.tagName = localName;
		if(tagName.equals("farm")) {
			dataInfo = new DataInfo();
		}
	}
	
	
	
	
}
