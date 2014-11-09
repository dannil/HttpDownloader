package org.dannil.httpdownloader.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@IdClass(UserDownload.class)
@Table(name = "usersdownloads")
public final class UserDownload implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@Override
	public int hashCode() {
		// Values to multiply the hash by are derived from moderately large
		// prime numbers. This results in lowered chance of hash collisions by
		// reducing the amount of available factors
		int hash = 1;
		hash = (int) (hash * (17 + this.userId));
		hash = (int) (hash * (23 + this.downloadId));
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof UserDownload && ((UserDownload) object).getUserId() == this.userId && ((UserDownload) object).getDownloadId() == this.downloadId) {
			return true;
		}
		return false;
	}

}
