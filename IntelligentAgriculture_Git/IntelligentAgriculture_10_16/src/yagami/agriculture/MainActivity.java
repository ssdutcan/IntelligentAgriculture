package yagami.agriculture;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TabHost tabHost = getTabHost();
		Resources res = getResources();
		
		Intent expandableIntent = new Intent();
		expandableIntent.setClass(this, FarmExpandableListActivity.class);
		TabHost.TabSpec expandableSpec = tabHost.newTabSpec("Farm");
		expandableSpec.setIndicator("Farm", res.getDrawable(R.drawable.farm));
		expandableSpec.setContent(expandableIntent);
		tabHost.addTab(expandableSpec);
		
//		Intent remoteIntent = new Intent();
//		remoteIntent.setClass(this, FarmListActivity.class);
//		TabHost.TabSpec remoteSpec = tabHost.newTabSpec("Remote");
//		remoteSpec.setIndicator("Remote", res.getDrawable(R.drawable.movie));
//		remoteSpec.setContent(remoteIntent);
//		tabHost.addTab(remoteSpec);
		
		Intent localIntent = new Intent();
		localIntent.setClass(this, LocalActivity.class);
		TabHost.TabSpec localSpec = tabHost.newTabSpec("Local");
		localSpec.setIndicator("Local",res.getDrawable(R.drawable.local));
		localSpec.setContent(localIntent);
		tabHost.addTab(localSpec);
		
		Intent marketIntent = new Intent();
		marketIntent.setClass(this, MarketMapActivity.class);
		TabHost.TabSpec marketSpec = tabHost.newTabSpec("Market");
		marketSpec.setIndicator("Market",res.getDrawable(R.drawable.market));
		marketSpec.setContent(marketIntent);
		tabHost.addTab(marketSpec);
		
		
	}

}
