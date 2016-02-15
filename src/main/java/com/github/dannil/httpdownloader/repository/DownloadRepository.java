package com.github.dannil.httpdownloader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;

/**
 * Repository for persisting downloads and other operations on these.
 * 
 * @author Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version 1.0.0
 * @since 0.0.1-SNAPSHOT
 */
public interface DownloadRepository extends JpaRepository<Download, Long> {

	/**
	 * Find a list of downloads for the specified user.
	 * 
	 * @param user
	 *            the user to retrieve downloads for
	 * 
	 * @return a list of downloads for the specified user
	 */
	List<Download> findByUser(final User user);

}