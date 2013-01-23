package yagami.agriculture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewerActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);  
//        //����ʾ��Activity����  
//        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.image_view);  
        //��ȡIntent  
        Intent receiveIntent=getIntent();  
        //��ȡͼƬ·��  
        String picPath=receiveIntent.getStringExtra("picPath");  
        ImageView iv=(ImageView)findViewById(R.id.imageView);  
        //ʹ��BitmapFactory��ImageView����ʾͼƬ  
        iv.setImageBitmap(BitmapFactory.decodeFile(picPath));  
    }
}
