package org.dannil.httpdownloader.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.repository.DownloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class which handles backend operations for downloads.
 * 
 * @author Daniel
 */
@Service(value = "DownloadService")
public final class DownloadService implements IDownloadService {

	@Autowired
	private DownloadRepository downloadRepository;

	@Override
	public final Download findById(final long id) {
		return this.downloadRepository.findByDownloadId(id);
	}

	@Override
	public final Download save(final Download download) {
		Download tempDownload = download;
		try {
			tempDownload.setData(this.getContentFromURL(new URL(download.getUrl())));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(tempDownload);

		return this.downloadRepository.save(tempDownload);
	}

	@Override
	public final String getContentFromURL(URL url) {
		File file = new File("test");
		try {
			FileUtils.copyURLToFile(url, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String content = "";
		try {
			content = FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

}
