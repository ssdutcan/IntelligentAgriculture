package yagami.agriculture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewerActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);  
//        //不显示该Activity标题  
//        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.image_view);  
        //获取Intent  
        Intent receiveIntent=getIntent();  
        //获取图片路径  
        String picPath=receiveIntent.getStringExtra("picPath");  
        ImageView iv=(ImageView)findViewById(R.id.imageView);  
        //使用BitmapFactory在ImageView中显示图片  
        iv.setImageBitmap(BitmapFactory.decodeFile(picPath));  
    }
}
