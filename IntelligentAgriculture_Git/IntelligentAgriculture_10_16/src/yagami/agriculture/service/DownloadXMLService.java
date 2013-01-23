package yagami.agriculture.service;


import yagami.download.HttpDownloader;
import yagami.model.FarmInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadXMLService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		FarmInfo farmInfo = (FarmInfo)intent.getSerializableExtra("Information");
		DownloadThread downloadThread = new DownloadThread(farmInfo);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class DownloadThread implements Runnable{
		private FarmInfo farmInfo = null;
//		private String ipv4 = "10.0.0.8";
//		private String ipv4 = "10.10.250.211";
		private String ipv4 = "10.10.250.195";
		private String phoneNumber = "15840891610";
		public DownloadThread(FarmInfo farmInfo){
			this.farmInfo = farmInfo;
		}
		@Override
		public void run() {
			String dataUrl = "http://"+ ipv4 +":8080/farm/"+phoneNumber+"/"+farmInfo.getName()+".xml";
			System.out.println("dataUrl--->"+dataUrl);
			HttpDownloader httpDownloader = new HttpDownloader();
			int result = httpDownloader.downFile(dataUrl, phoneNumber+"/", farmInfo.getName()+".xml");
			// TODO Auto-generated method stub
			
		}
	}

}
