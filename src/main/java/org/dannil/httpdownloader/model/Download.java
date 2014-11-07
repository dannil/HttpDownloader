package org.dannil.httpdownloader.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "downloads")
public final class Download {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DownloadID")
	private Long downloadId;

	@Column(name = "Title")
	private String title;

	@Column(name = "StartDate")
	private Date startDate;

	@Column(name = "EndDate")
	private Date endDate;

	public final Long getDownloadId() {
		return this.downloadId;
	}

	// Commented for safety purposes
	/*
	 * public final void setDownloadId(Long downloadId) {
	 * this.downloadId = downloadId;
	 * }
	 */

	public final String getTitle() {
		return this.title;
	}

	public final void setTitle(final String title) {
		this.title = title;
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
	public String toString() {
		final StringBuilder result = new StringBuilder();
		final String NEW_LINE = System.getProperty("line.separator");

		result.append(this.getClass().getName() + " - " + this.getClass().getSuperclass().getName() + " {" + NEW_LINE);
		result.append("\tDownloadID: " + this.downloadId + NEW_LINE);
		result.append("\tTitle: " + this.title + NEW_LINE);
		result.append("\tStart date: " + this.startDate + NEW_LINE);
		result.append("\tEnd date: " + this.endDate + NEW_LINE);
		result.append("}");

		return result.toString();
	}

}
