package com.dimen.live.pusher;

import java.io.IOException;

import com.dimen.jni.PusherNative;
import com.dimen.params.VideoParams;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

public class VideoPusher extends Pusher implements Callback, PreviewCallback {
	private SurfaceHolder surfaceHolder;
	private Camera mCamera;
	private VideoParams videoParams;
	private byte[] buffers;
	private boolean isPushing = false;
	private PusherNative pusherNative;

	public VideoPusher(SurfaceHolder surfaceHolder, VideoParams videoParams,
			PusherNative pusherNative) {
		this.surfaceHolder = surfaceHolder;
		this.videoParams = videoParams;
		this.pusherNative = pusherNative;
		surfaceHolder.addCallback(this);

	}

	@Override
	public void startPush() {
		// 设置视频参数
		pusherNative.setVideoOptions(videoParams.getWidth(),
				videoParams.getHeight(), videoParams.getBitrate(),
				videoParams.getFps());
		isPushing = true;
	}

	@Override
	public void stopPush() {
		// TODO Auto-generated method stub
		isPushing = false;
	}

	@Override
	public void release() {
		stopPreview();

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		startPreview();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {

		if (mCamera != null) {
			mCamera.addCallbackBuffer(buffers);
		}
		if (isPushing) {
			// 回调函数中获取图像数据，然后改Native代码编码
			pusherNative.fireVideo(data);
		}
	}

	/**
	 * 开始预览
	 */
	private void startPreview() {

		try {

			mCamera = Camera.open(videoParams.getCameraId());
			mCamera.setPreviewDisplay(surfaceHolder);
			// 设置相机的参数
			Parameters pCamera = mCamera.getParameters();
			pCamera.setPreviewFormat(ImageFormat.NV21);// YUV
			//预览图像的画面宽高
			pCamera.setPreviewSize(videoParams.getWidth(), videoParams.getHeight());
            //pCamera.setPreviewFpsRange(videoParams.getFps()-1, videoParams.getFps());
			// 获取视频预览的数据
			buffers = new byte[videoParams.getWidth() * videoParams.getHeight()
					* 4];
			mCamera.addCallbackBuffer(buffers);
			mCamera.setPreviewCallbackWithBuffer(this);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 停止预览
	 */
	private void stopPreview() {
		if (mCamera != null) {
			mCamera.setPreviewCallbackWithBuffer(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	/**
	 * 切换摄像头
	 */
	public void switchCamera() {
		if (videoParams.getCameraId() == CameraInfo.CAMERA_FACING_BACK) {
			videoParams.setCameraId(CameraInfo.CAMERA_FACING_FRONT);
		} else {
			videoParams.setCameraId(CameraInfo.CAMERA_FACING_BACK);
		}
		// 重新预览
		stopPreview();
		startPreview();
	}

}
