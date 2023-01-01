package com.bcits.mdas.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.bcits.mdas.utility.FilterUnit;

public class FTPUploadFile {

	FTPClient ftpClient;

	public FTPUploadFile() throws IOException {
		ftpClient = new FTPClient();
		ftpClient.connect(FilterUnit.ftpURL, 21);
		ftpClient.login(FilterUnit.ftpUser, FilterUnit.ftpPass);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	}

	public String uploadFile(String file) {
		try {
			File firstLocalFile = new File(file);
			String firstRemoteFile = FilterUnit.ftpServerFolder + firstLocalFile.getName();
			InputStream inputStream = new FileInputStream(firstLocalFile);
			System.out.println("Start uploading first file " + firstLocalFile.getName());
			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			System.out.println(done);
			inputStream.close();
			if (done) {
				return "SUCCESS";
			} else {
				return "FTP UPLOAD : " + ftpClient.getReplyCode();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			return "FTP UPLOAD : " + ex.getMessage();
		}
	}

	public void closeFTP() {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
