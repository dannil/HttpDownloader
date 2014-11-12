package org.dannil.httpdownloader.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
@Entity
@IdClass(UserDownload.class)
@Table(name = "usersdownloads")
public final class UserDownload implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UserID")
	@NotNull
	private Long userId;

	@Id
	@Column(name = "DownloadID")
	@NotNull
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
		// Value to multiply the hash by is a prime, therefore reducing
		// the amount of possible hash collisions
		final int prime = 31;
		int hash = 1;
		hash = prime * hash + ((this.downloadId == null) ? 0 : this.downloadId.hashCode());
		hash = prime * hash + ((this.userId == null) ? 0 : this.userId.hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!(object instanceof UserDownload)) {
			return false;
		}

		UserDownload other = (UserDownload) object;
		if (this.downloadId == null) {
			if (other.downloadId != null) {
				return false;
			}
		} else if (!this.downloadId.equals(other.downloadId)) {
			return false;
		}
		if (this.userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!this.userId.equals(other.userId)) {
			return false;
		}
		return true;
	}

}
