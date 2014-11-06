package org.dannil.httpdownloader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "usersdownloads")
public class UserDownload {

	@Id
	@Column(name = "UserID")
	private Long userId;

	@Id
	@Column(name = "DownloadID")
	private Long downloadId;

	public final Long getUserId() {
		return this.userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
	}

	public final Long getDownloadId() {
		return this.downloadId;
	}

	public final void setDownloadId(Long downloadId) {
		this.downloadId = downloadId;
	}

}
