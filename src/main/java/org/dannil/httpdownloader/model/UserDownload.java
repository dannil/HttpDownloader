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
public class UserDownload implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UserID")
	@NotNull
	private Long userId;

	@Id
	@Column(name = "DownloadID")
	@NotNull
	private Long downloadId;

	public UserDownload() {

	}

	public UserDownload(final long userId, final long downloadId) {
		this();
		this.userId = userId;
		this.downloadId = downloadId;
	}

	public final Long getUserId() {
		return this.userId;
	}

	public final void setUserId(final long userId) {
		this.userId = userId;
	}

	public final Long getDownloadId() {
		return this.downloadId;
	}

	public final void setDownloadId(final long downloadId) {
		this.downloadId = downloadId;
	}

	@Override
	public int hashCode() {
		// Value to multiply the hash by is a prime, therefore reducing
		// the amount of possible hash collisions
		final int prime = 31;
		int hashCode = 1;
		hashCode = prime * hashCode + ((this.downloadId == null) ? 0 : this.downloadId.hashCode());
		hashCode = prime * hashCode + ((this.userId == null) ? 0 : this.userId.hashCode());
		return hashCode;
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

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		final String NEW_LINE = System.getProperty("line.separator");

		result.append(this.getClass().getName() + " - " + this.getClass().getSuperclass().getName() + " {" + NEW_LINE);
		result.append("\tUserID: " + this.userId + NEW_LINE);
		result.append("\tDownloadID: " + this.downloadId + NEW_LINE);
		result.append("}");

		return result.toString();
	}

}
