package org.dannil.httpdownloader.repository;

import java.util.List;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, Long> {

	public List<Download> findByUser(final User user);

}