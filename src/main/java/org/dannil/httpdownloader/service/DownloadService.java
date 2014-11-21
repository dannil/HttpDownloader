package org.dannil.httpdownloader.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.model.UserDownload;
import org.dannil.httpdownloader.repository.DownloadRepository;
import org.dannil.httpdownloader.repository.UserDownloadRepository;
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

	@Autowired
	private UserDownloadRepository userDownloadRepository;

	@Override
	public final Download findById(final long id) {
		return this.downloadRepository.findByDownloadId(id);
	}

	@Override
	public final Download save(final User user, final Download download) {
		Download tempDownload = this.downloadRepository.save(download);

		UserDownload userDownload = new UserDownload();
		userDownload.setUserId(user.getUserId());
		userDownload.setDownloadId(download.getDownloadId());
		UserDownload tempUserDownload = this.userDownloadRepository.save(userDownload);

		final File file;
		try {
			file = this.getFileFromURL(download);
			this.saveToDrive(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tempDownload;
	}

	@Override
	public final File getFileFromURL(final Download download) throws MalformedURLException, IOException {
		final String name = FilenameUtils.getBaseName(download.getUrl());
		final String extension = FilenameUtils.getExtension(download.getUrl());

		final File file = new File(name + "-" + download.hashCode() + "." + extension);
		FileUtils.copyURLToFile(new URL(download.getUrl()), file);
		return file;
	}

	@Override
	public final File saveToDrive(final File file) throws IOException {
		final File destination = new File(this.getClass().getClassLoader().getResource("").getPath() + "../downloads");

		FileUtils.copyFileToDirectory(file, destination);

		return file;
	}

}
