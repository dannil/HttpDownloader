package com.github.dannil.httpdownloader.repository;

import java.util.List;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for persisting downloads and other operations on these.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
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
    List<Download> findByUser(User user);

}
