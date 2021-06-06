package com.project.bean;

import java.io.File;

public class PSNRBean {
	private File f1;
	private File f2;
	private double  peak, signal, noise, mse;
	private double psnr,snr;
	public File getF1() {
		return f1;
	}
	public void setF1(File f1) {
		this.f1 = f1;
	}
	public File getF2() {
		return f2;
	}
	public void setF2(File f2) {
		this.f2 = f2;
	}
	public double getPeak() {
		return peak;
	}
	public void setPeak(double peak) {
		this.peak = peak;
	}
	public double getSignal() {
		return signal;
	}
	public void setSignal(double signal) {
		this.signal = signal;
	}
	public double getNoise() {
		return noise;
	}
	public void setNoise(double noise) {
		this.noise = noise;
	}
	public double getMse() {
		return mse;
	}
	public void setMse(double mse) {
		this.mse = mse;
	}
	public double getPsnr() {
		return psnr;
	}
	public void setPsnr(double psnr) {
		this.psnr = psnr;
	}
	public double getSnr() {
		return snr;
	}
	public void setSnr(double snr) {
		this.snr = snr;
	}
	
}
