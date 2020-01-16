package com.dimen.live;

import com.dimen.jni.PusherNative;
import com.dimen.listener.LiveStateChangeListener;
import com.dimen.live.pusher.LivePusher;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements LiveStateChangeListener {
	private LivePusher live;
	private String url="";
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PusherNative.CONNECT_FAILED:
				Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
			
				break;
			case PusherNative.INIT_FAILED:
				Toast.makeText(MainActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
				break;	
			default:
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);
		//相机图像的预览
		live = new LivePusher(surfaceView.getHolder());
	}

	
	/**
	 * 开始直播
	 * @param btn
	 */
	public void mStartLive(View view) {
		Button btn = (Button)view;
		if(btn.getText().equals("开始直播")){
			live.startPusher(url,this);
			btn.setText("停止直播");
		}else{
			live.stopPusher();
			btn.setText("开始直播");
		}
	}

	/**
	 * 切换摄像头
	 * @param btn
	 */
	public void mSwitchCamera(View btn) {
		live.switchCamera();
	}

	//改方法执行仍然在子线程中，发送消息到UI主线程
	@Override
	public void onError(int code) {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(code);
	}
	
	
	
}
