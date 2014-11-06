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
public class Download {

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

	@Column(name = "Data")
	private Object data;

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

	public final void setTitle(String title) {
		this.title = title;
	}

	public final Date getStartDate() {
		return this.startDate;
	}

	public final void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public final Date getEndDate() {
		return this.endDate;
	}

	public final void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public final Object getData() {
		return this.data;
	}

	public final void setData(Object data) {
		this.data = data;
	}

}
