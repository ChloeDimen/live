package com.dimen.live.pusher;

import com.dimen.jni.PusherNative;
import com.dimen.listener.LiveStateChangeListener;
import com.dimen.params.AudioParams;
import com.dimen.params.VideoParams;

import android.hardware.Camera.CameraInfo;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

public class LivePusher implements Callback {
	private SurfaceHolder surfaceHolder;
	private VideoPusher videoPusher;
	private AudioPusher audioPusher;
	private VideoParams videoParams;
	private PusherNative pusherNative;
	public LivePusher(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
		surfaceHolder.addCallback(this);
		prepare();
	}

	private void prepare() {
		
		 pusherNative=new PusherNative();
		videoParams = new VideoParams(420, 320, CameraInfo.CAMERA_FACING_BACK);
		videoPusher = new VideoPusher(surfaceHolder, videoParams,pusherNative);
		AudioParams audioParams =new AudioParams();
		audioPusher = new AudioPusher(audioParams,pusherNative);

	}

	/**
	 * 切换摄像头
	 */
	public void switchCamera() {
		videoPusher.switchCamera();
	}

	/**
	 * 开始推流
	 */
	public void startPusher(String url,LiveStateChangeListener liveStateChangeListener) {
		// TODO Auto-generated method stub
		videoPusher.startPush();
		audioPusher.startPush();
		pusherNative.startPush(url);
		pusherNative.setLiveStateChangeListener(liveStateChangeListener);
	}
	
	/**
	 * 停止推流
	 */
	public void stopPusher(){
		videoPusher.stopPush();
		audioPusher.stopPush();
		pusherNative.stopPush();
		pusherNative.removeLiveStateChangeListener();
	}
	/**
	 * 释放资源
	 */
   private void release(){
	   videoPusher.release();
	   audioPusher.release();
	   pusherNative.realease();
   }
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		stopPusher();
		release();
	}
}
