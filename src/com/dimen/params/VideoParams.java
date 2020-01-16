package com.dimen.params;

public class VideoParams {
	private int width;
	private int height;
	// 码率480kbps
	private int bitrate = 480000;
	// 帧频默认25帧/s
	private int fps = 25;
	private int cameraId;

	public VideoParams(int width, int height, int cameraId) {
		super();
		this.width = width;
		this.height = height;
		this.cameraId = cameraId;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getCameraId() {
		return cameraId;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public void setCameraId(int cameraId) {
		this.cameraId = cameraId;
	}

}
