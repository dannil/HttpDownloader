package org.dannil.httpdownloader.repository;

import org.dannil.httpdownloader.model.Download;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, Long> {

	public Download findByDownloadId(final long id);

}
