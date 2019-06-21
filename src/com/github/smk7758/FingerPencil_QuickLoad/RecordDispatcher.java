package com.github.smk7758.FingerPencil_QuickLoad;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.smk7758.FingerPencil_QuickLoad.Main.LogLevel;

import javafx.animation.AnimationTimer;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class RecordDispatcher {
	AnimationTimer imageAnimation = null;
	RecordService recordService = null;
	// boolean isRecording = false, isStarted = false;

	public RecordDispatcher(TextField videoOutputFolderPath, TextField cameraID, ImageView imageView) {
		if (videoOutputFolderPath.getText().isEmpty()) {
			Main.printDebug("VideoOutputPath is empty.", LogLevel.ERROR);
			return;
		}

		Main.printDebug("videoOutputFolderPath: " + Paths.get(videoOutputFolderPath.getText()).toString(),
				LogLevel.DEBUG);

		try {
			short cameraID_ = Short.valueOf(cameraID.getText());
			recordService = new RecordService(Paths.get(videoOutputFolderPath.getText()), cameraID_);
		} catch (NumberFormatException ex) {
			Main.printDebug("CameraID is not short number type.", LogLevel.INFO);
			recordService = new RecordService(Paths.get(videoOutputFolderPath.getText()));
		}

		imageAnimation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				imageView.setImage(recordService.getLastValue());
			}
		};
	}

	public void startRecord() {
		imageAnimation.start();
		recordService.start();

		Main.printDebug("started REC", LogLevel.DEBUG);
	}

	public void stopRecord(ImageView imageView) {
		Main.printDebug("stopButton", LogLevel.DEBUG);
		imageAnimation.stop();
		recordService.cancel();

		imageView.setImage(null);
		imageAnimation = null;
		recordService = null;
	}

	public boolean isRecording() {
		return (imageAnimation == null || recordService == null);
	}

	public Path getVideoOutputFilePath() {
		return recordService.getVideoOutputFilePath();
	}
}
