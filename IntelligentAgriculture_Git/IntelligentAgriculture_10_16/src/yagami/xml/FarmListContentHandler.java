package yagami.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import yagami.model.FarmInfo;

public class FarmListContentHandler extends DefaultHandler{
	private List<FarmInfo> infos = null;
	private FarmInfo farmInfo = null;
	private String tagName = null;
	
	public FarmListContentHandler(List<FarmInfo> infos) {
		super();
		this.infos = infos;
	}

	public List<FarmInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<FarmInfo> infos) {
		this.infos = infos;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp = new String(ch, start, length);
		if(tagName.equals("name")) {
			farmInfo.setName(temp);
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
		if(name.equals("farm")) {
			infos.add(farmInfo);
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
		this.tagName = localName;
		if(tagName.equals("farm")) {
			farmInfo = new FarmInfo();
		}
	}

}
