package org.dannil.httpdownloader.repository;

import org.dannil.httpdownloader.model.UserDownload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDownloadRepository extends JpaRepository<UserDownload, Long> {

	public UserDownload findByUserId(final Long id);

	public UserDownload findByDownloadId(final Long id);

	public UserDownload findByUserIdAndDownloadId(final Long userId, final Long downloadId);

}
