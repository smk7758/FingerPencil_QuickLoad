package com.github.smk7758.FingerPencil_QuickLoad;

import java.nio.file.Paths;

import com.github.smk7758.FingerPencil_QuickLoad.Main.LogLevel;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Controller {
	@FXML
	TextField cameraID, videoOutputFolderPath, fileName;
	@FXML
	ImageView imageView;
	@FXML
	Text recSecText;
	@FXML
	Button recButton, startButton;

	AnimationTimer imageAnimation = null;
	RecordService recordService = null;
	boolean isRecording = false, isStarted = false;

	@FXML
	public void initialize() {
	}

	@FXML
	public void onRecButton() {
		Main.printDebug("RecButton", LogLevel.DEBUG);

		if (imageAnimation == null || recordService == null) {
			Main.printDebug("START REC", LogLevel.DEBUG);
			startRecord();
		} else {
			stopRecord();
		}
	}

	public void startRecord() {
		prepareRecording();

		imageAnimation.start();
		recordService.start();

		Main.printDebug("started REC", LogLevel.DEBUG);
	}

	private void prepareRecording() {
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

	public void stopRecord() {
		Main.printDebug("stopButton", LogLevel.DEBUG);
		imageAnimation.stop();
		recordService.cancel();

		imageView.setImage(null);
		imageAnimation = null;
		recordService = null;
	}

	@FXML
	public void onStartButton() {
	}

}
