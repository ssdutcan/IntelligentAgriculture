package yagami.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import yagami.model.MarketInfo;

public class MarketListContentHandler extends DefaultHandler{
	private List<MarketInfo> infos = null;
	private MarketInfo marketInfo= null;
	private String tagName = null;
	
	public MarketListContentHandler(List<MarketInfo> infos) {
		super();
		this.infos = infos;
	}

	public List<MarketInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<MarketInfo> infos) {
		this.infos = infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		String temp = new String(ch, start, length);
		if(tagName.equals("id")) {
			marketInfo.setId(temp);
		}else if(tagName.equals("market.name")) {
			marketInfo.setMarketName(temp);
		}else if(tagName.equals("market.crop")) {
			marketInfo.setMarketCrop(temp);
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
		if(name.equals("resource")) {
			infos.add(marketInfo);
		}
		tagName = "";
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		this.tagName = localName;
		if(tagName.equals("resource")) {
			marketInfo = new MarketInfo();
		}
	}
	
	
	
	
}
