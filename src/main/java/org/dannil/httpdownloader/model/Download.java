package org.dannil.httpdownloader.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "downloads")
public class Download {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DownloadID")
	private Long downloadId;

	@Column(name = "Title")
	@NotNull
	private String title;

	@Column(name = "URL")
	@NotNull
	private String url;

	@Column(name = "StartDate")
	private Date startDate;

	@Column(name = "EndDate")
	private Date endDate;

	public Download() {

	}

	public Download(final String title, final String url) {
		this();
		this.title = title;
		this.url = url;
	}

	public Download(final String title, final String url, final Date startDate) {
		this(title, url);
		this.startDate = startDate;
	}

	public final Long getDownloadId() {
		return this.downloadId;
	}

	// Commented for safety purposes
	// public final void setDownloadId(Long downloadId) {
	// this.downloadId = downloadId;
	// }

	public final String getTitle() {
		return this.title;
	}

	public final void setTitle(final String title) {
		this.title = title;
	}

	public final String getUrl() {
		return this.url;
	}

	public final void setUrl(final String url) {
		this.url = url;
	}

	public final Date getStartDate() {
		return this.startDate;
	}

	public final void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public final Date getEndDate() {
		return this.endDate;
	}

	public final void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		// Value to multiply the hash by is a prime, therefore reducing
		// the amount of possible hash collisions
		final int prime = 31;
		int hashCode = 1;
		hashCode = prime * hashCode + ((this.downloadId == null) ? 0 : this.downloadId.hashCode());
		hashCode = prime * hashCode + ((this.title == null) ? 0 : this.title.hashCode());
		hashCode = prime * hashCode + ((this.url == null) ? 0 : this.url.hashCode());
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Download)) {
			return false;
		}
		Download other = (Download) obj;
		if (this.downloadId == null) {
			if (other.downloadId != null) {
				return false;
			}
		} else if (!this.downloadId.equals(other.downloadId)) {
			return false;
		}
		if (this.endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!this.endDate.equals(other.endDate)) {
			return false;
		}
		if (this.startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!this.startDate.equals(other.startDate)) {
			return false;
		}
		if (this.title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!this.title.equals(other.title)) {
			return false;
		}
		if (this.url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!this.url.equals(other.url)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		final String NEW_LINE = System.getProperty("line.separator");

		result.append(this.getClass().getName() + " - " + this.getClass().getSuperclass().getName() + " {" + NEW_LINE);
		result.append("\tDownloadID: " + this.downloadId + NEW_LINE);
		result.append("\tTitle: " + this.title + NEW_LINE);
		result.append("\tURL: " + this.url + NEW_LINE);
		result.append("\tStart date: " + this.startDate + NEW_LINE);
		result.append("\tEnd date: " + this.endDate + NEW_LINE);
		result.append("}");

		return result.toString();
	}

}
