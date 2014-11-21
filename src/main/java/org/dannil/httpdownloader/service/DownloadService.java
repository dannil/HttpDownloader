package org.dannil.httpdownloader.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.model.UserDownload;
import org.dannil.httpdownloader.repository.DownloadRepository;
import org.dannil.httpdownloader.repository.UserDownloadRepository;
import org.dannil.httpdownloader.utility.PathUtility;
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
		final Download tempDownload = this.downloadRepository.save(download);

		final UserDownload userDownload = new UserDownload();
		userDownload.setUserId(user.getUserId());
		userDownload.setDownloadId(download.getDownloadId());
		final UserDownload tempUserDownload = this.userDownloadRepository.save(userDownload);

		final File file;
		try {
			file = this.getFileFromURL(tempDownload);
			this.saveToDrive(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tempDownload;
	}

	@Override
	public final File getFileFromURL(final Download download) throws IOException {
		final String name = FilenameUtils.getBaseName(download.getUrl());
		final String extension = FilenameUtils.getExtension(download.getUrl());

		final File file = new File(download.hashCode() + "_" + name + "." + extension);
		FileUtils.copyURLToFile(new URL(download.getUrl()), file, 5000, 5000);
		return file;
	}

	@Override
	public final File saveToDrive(final File file) throws IOException {
		final File destination = new File(PathUtility.DOWNLOADS_PATH);

		FileUtils.copyFileToDirectory(file, destination);

		return file;
	}

}
