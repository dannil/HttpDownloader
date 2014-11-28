package org.dannil.httpdownloader.repository;

import java.util.List;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for persisting downloads and other operations on these.
 * 
 * @author Daniel Nilsson
 */
public interface DownloadRepository extends JpaRepository<Download, Long> {

	/**
	 * Find a list of downloads for the specified user.
	 * 
	 * @param user
	 * 				- The user to retrieve downloads for
	 * 
	 * @return A list of downloads for the specified user
	 */
	public List<Download> findByUser(final User user);

}