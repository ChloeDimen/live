package com.dimen.live.pusher;

import com.dimen.jni.PusherNative;
import com.dimen.params.AudioParams;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;

public class AudioPusher extends Pusher {

	private AudioRecord audioRecord;
	private AudioParams audioParams;
	private boolean isPushing = false;
	private int minBufferSize;
	private PusherNative pusherNative;

	public AudioPusher(AudioParams audioParams, PusherNative pusherNative) {
		this.audioParams = audioParams;
		this.pusherNative = pusherNative;
		int channelConfig = audioParams.getChannel() == 1 ? AudioFormat.CHANNEL_IN_MONO
				: AudioFormat.CHANNEL_IN_STEREO;
		// 最小缓冲区大小
		minBufferSize = AudioRecord.getMinBufferSize(
				audioParams.getSampleRateInHz(), channelConfig,
				AudioFormat.ENCODING_PCM_16BIT);
		audioRecord = new AudioRecord(AudioSource.MIC,
				audioParams.getSampleRateInHz(), channelConfig,
				AudioFormat.ENCODING_PCM_16BIT, minBufferSize);
	}

	@Override
	public void startPush() {
		// 设置音频参数
		pusherNative.setAudioOptions(audioParams.getSampleRateInHz(),
				audioParams.getChannel());
		isPushing = true;
		// 启动一个录音的线程
		new Thread(new AudioRecordTask()).start();
	}

	@Override
	public void stopPush() {

		isPushing = false;
		audioRecord.stop();

	}

	@Override
	public void release() {
		if (audioRecord != null) {
			audioRecord.release();
			audioRecord = null;
		}

	}

	class AudioRecordTask implements Runnable {

		@Override
		public void run() {
			// 开始录音
			audioRecord.startRecording();
			//
			while (isPushing) {
				// 通过AudioRecord 不断读取音频数据
				byte[] buffer = new byte[minBufferSize];
				int len = audioRecord.read(buffer, 0, buffer.length);
				if (len > 0) {
					// 传给Native代码，进行音频编码
					pusherNative.fireAudio(buffer, len);
				}

			}
		}
	}

}
