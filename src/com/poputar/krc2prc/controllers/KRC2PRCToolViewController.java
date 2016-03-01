package com.poputar.krc2prc.controllers;

import java.io.File;

import com.poputar.krc2prc.tool.Krc2PrcTool;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class KRC2PRCToolViewController {
	public VBox root;
	public TextField tfFilePath;
	public TextField tfTotalFiles;
	public TextField tfProgress;
	public TextArea taErrorMsg;
	public Button btnOk;
	public Button btnChooseFile;
	
	private int totalFiles = 0;
	private int finishFiles = 0;
	
	private Krc2PrcTool krc2PrcTool = new Krc2PrcTool();
	
	public void btnOpenFilePathHandler() {
		DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("选择文件目录");
        File filepath = dc.showDialog(root.getScene().getWindow());
        
        if (filepath != null) {
        	totalFiles = 0;
        	//finishFiles = 0;
        	 //统计KRC文件数
            countKRCFiles(filepath);
            
            tfFilePath.setText(filepath.getAbsolutePath());
        	tfTotalFiles.setText(totalFiles+"");
        	tfProgress.setText(String.format("[%d/%d]", finishFiles, totalFiles));
        }
        
        if(totalFiles > 0) {
        	btnOk.setDisable(false);
        }
	}

	
	public void btnTranseferKrc2PrcHandler() {
		btnOk.setDisable(true);
		btnChooseFile.setDisable(true);
		taErrorMsg.clear();
		finishFiles =0;
		
		long begintime = System.currentTimeMillis();
		
		tranferKrc2PrcFile(new File(tfFilePath.getText()));
		
		long endtime = System.currentTimeMillis();
		//SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		
		taErrorMsg.appendText(String.format("转换耗时：%sms\n",(endtime-begintime)));
		
		btnOk.setDisable(false);
		btnChooseFile.setDisable(false);
	}
	
	private void tranferKrc2PrcFile(File filepath) {
		if (filepath.isFile() && filepath.getName().endsWith(".krc")) {
			try {
				krc2PrcTool.parseKrc2PrcFile(filepath.getAbsolutePath());
				finishFiles++;
				//taErrorMsg.appendText(String.format("文件[%s]处理完成!\n", filepath.getAbsolutePath()));
			} catch (Exception e) {
				e.printStackTrace();
				taErrorMsg.appendText(String.format("文件[%s]转换失败!\n", filepath.getAbsolutePath()));
			}
		} else if (filepath.isDirectory()) {
			for (File f : filepath.listFiles()) {
				tranferKrc2PrcFile(f);
			}
		}
		
		tfProgress.setText(String.format("[%d/%d]", finishFiles, totalFiles));
		
	}
	
	private void countKRCFiles(File filepath) {
		if (filepath.isFile() && filepath.getName().endsWith(".krc")) {
			totalFiles++;
		} else if (filepath.isDirectory()) {
			for (File f : filepath.listFiles()) {
				countKRCFiles(f);
			}
		}
	}
	
}
