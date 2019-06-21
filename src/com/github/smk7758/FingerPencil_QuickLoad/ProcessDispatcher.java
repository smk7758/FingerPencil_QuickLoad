package com.github.smk7758.FingerPencil_QuickLoad;

import java.nio.file.Path;

import com.github.smk7758.FingerPencil.Main_;

public class ProcessDispatcher {
	private final Path videoOutputFilePath;
	private Main_ main = null;

	public ProcessDispatcher(Path videoOutputFilePath) {
		this.videoOutputFilePath = videoOutputFilePath;
	}

	public void start() {
		main = new Main_(videoOutputFilePath);
		main.lunchProcess();
	}

	public boolean isRunning() {
		return (main != null && main.isRunning()) ? true : false;
	}
}
