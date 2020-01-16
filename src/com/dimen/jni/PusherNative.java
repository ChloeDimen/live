package com.dimen.jni;

import android.provider.ContactsContract.StatusUpdates;

public class PusherNative {

	public native void startPush(String url);

	public native void stopPush();

	public native void realease();

	/**
	 * 设置视频参数
	 * 
	 * @param width
	 * @param height
	 * @param bitrate
	 * @param fps
	 */
	public native void setVideoOptions(int width, int height, int bitrate,
			int fps);

	/**
	 * 设置音频参数
	 * 
	 * @param sampleRateInHz
	 * @param channel
	 */
	public native void setAudioOptions(int sampleRateInHz, int channel);

	/**
	 * 发送视频数据
	 * 
	 * @param data
	 */
	public native void fireVideo(byte[] data);

	/**
	 * 发送音频
	 * 
	 * @param data
	 * @param len
	 */
	public native void fireAudio(byte[] data, int len);
	
	static{
		System.loadLibrary("live");
	}

}