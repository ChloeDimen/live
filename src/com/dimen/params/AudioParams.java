package com.dimen.params;

import android.media.AudioRecord;

public class AudioParams {
	// new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat,
	// bufferSizeInBytes)
	// 采样率
	private int sampleRateInHz = 44100;
	// 声道个数
	private int channel = 1;

	public int getSampleRateInHz() {
		return sampleRateInHz;
	}

	public void setSampleRateInHz(int sampleRateInHz) {
		this.sampleRateInHz = sampleRateInHz;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public AudioParams(int sampleRateInHz, int channel) {
		super();
		this.sampleRateInHz = sampleRateInHz;
		this.channel = channel;
	}

	public AudioParams() {
		// TODO Auto-generated constructor stub
	}

}
