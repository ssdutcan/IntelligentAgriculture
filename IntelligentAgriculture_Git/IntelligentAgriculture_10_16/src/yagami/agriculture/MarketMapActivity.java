package yagami.agriculture;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import yagami.download.HttpDownloader;
import yagami.model.MarketInfo;
import yagami.overlay.MapOverlay;
import yagami.xml.MarketListContentHandler;



import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MarketMapActivity extends MapActivity{
	private List<MarketInfo> marketInfos = null;
	private List<OverlayItem> overlayitems = new ArrayList<OverlayItem>();
	private List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
	private GeoPoint geoPoint1;
	private GeoPoint geoPoint2;
	private GeoPoint geoPoint3;
	private GeoPoint geoPoint4;
	private MapController mapController;
//	private String ipv4 = "10.0.0.8";
//	private String ipv4 = "10.10.250.211";
	private String ipv4 = "10.10.250.195";
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.market_map);
		updateListView();
		MapView mapView = (MapView)findViewById(R.id.mapViewId);
		mapView.setBuiltInZoomControls(true);
		
		List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = getResources().getDrawable(R.drawable.star);
        MapOverlay mapOverlay = new MapOverlay(drawable, this);
        
        geoPoint1 = new GeoPoint(39010000, 121000000);
        geoPoint2 = new GeoPoint(34010000, 130100000);
		geoPoint3 = new GeoPoint(29010000, 111000000);
		geoPoint4 = new GeoPoint(44010000, 126100000);
		geoPoints.add(geoPoint1);
		geoPoints.add(geoPoint2);
		geoPoints.add(geoPoint3);
		geoPoints.add(geoPoint4);
		
		
		for(int i=0; i<4; i++) {
			OverlayItem overlayitem = new OverlayItem(geoPoints.get(i), marketInfos.get(i).getMarketName(),marketInfos.get(i).getMarketCrop());
			mapOverlay.addOverlay(overlayitem);
		}
        
        mapOverlays.add(mapOverlay);
        
        mapController = mapView.getController();
        mapController.animateTo(geoPoint1);
		mapController.setZoom(5);
	}
	public void updateListView(){
		String xml = downloadXML("http://"+ ipv4 +":8080/market/resources.xml");
		marketInfos = parse(xml);
	}
	
	private String downloadXML(String urlStr) {
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}
	
	public List<MarketInfo> parse(String xmlStr){
		SAXParserFactory saxParseFactory = SAXParserFactory.newInstance();
		List<MarketInfo> infos = new ArrayList<MarketInfo>();
		try{
			XMLReader xmlReader = saxParseFactory.newSAXParser().getXMLReader();
			MarketListContentHandler marketListContentHandler = new MarketListContentHandler(infos);
			xmlReader.setContentHandler(marketListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for(Iterator iterator = infos.iterator(); iterator.hasNext();) {
				MarketInfo marketInfo = (MarketInfo) iterator.next();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
