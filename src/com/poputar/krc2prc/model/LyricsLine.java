package com.poputar.krc2prc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <b> </b>
 * <p>
 * 功能:
 * </p>
 * 
 * @作者 stone
 * @创建时间 2016年2月29日 下午6:12:05
 * @修改内容
 * @修改时间
 */
public class LyricsLine {
	private double stime;
	private double duration;
	
	private List<Word> words;
	
	public LyricsLine() {
		stime = 0;
		duration = 0;
		words = new ArrayList<Word>();
	}

	public double getStime() {
		return stime;
	}

	public void setStime(double stime) {
		this.stime = stime;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	
}
