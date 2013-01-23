package yagami.overlay;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapOverlay extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
	private Context context;
	
	public MapOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}

	public MapOverlay(Drawable defaultMarker,Context context){
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}
	
	public void addOverlay(OverlayItem overlayItem){
		overlayItems.add(overlayItem);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return overlayItems.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return overlayItems.size();
	}

	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		OverlayItem item = overlayItems.get(index);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(item.getTitle());
		builder.setMessage(item.getSnippet());
		Dialog dialog = builder.create();
		dialog.show();
		return true;
	}

}
