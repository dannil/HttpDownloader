package org.dannil.httpdownloader.repository;

import java.util.List;

import org.dannil.httpdownloader.model.Download;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, Long> {

	public Download findByDownloadId(final Long id);

	public List<Download> findByUserId(final Long userId);

}