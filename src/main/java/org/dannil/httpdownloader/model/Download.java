package org.dannil.httpdownloader.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "downloads")
public class Download implements Serializable {

	private static final long serialVersionUID = -9127457928753578088L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DownloadID")
	private Long id;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UserID", referencedColumnName = "UserID")
	private User user;

	/**
	 * Default constructor
	 */
	public Download() {

	}

	/**
	 * Overloaded constructor
	 * 
	 * @param title
	 * 				the download's title
	 * @param url
	 * 				the download's URL (Uniform Resource Locator)
	 */
	public Download(final String title, final String url) {
		this();
		this.title = title;
		this.url = url;
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param title
	 * 					the download's title
	 * @param url
	 * 					the download's URL (Uniform Resource Locator)
	 * @param startDate
	 * 					the date the download was started
	 */
	public Download(final String title, final String url, final Date startDate) {
		this(title, url);
		this.startDate = startDate;
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param title
	 * 					the download's title
	 * @param url
	 * 					the download's URL (Uniform Resource Locator)
	 * @param startDate
	 * 					the date the download was started
	 * @param endDate
	 * 					the date the download was completed
	 * @param user
	 * 					the user which owns this download
	 */
	public Download(final String title, final String url, final Date startDate, final Date endDate, final User user) {
		this(title, url, startDate);
		this.endDate = endDate;
		this.user = new User(user);
	}

	/**
	 * Copy constructor
	 * 
	 * @param download 
	 * 					the object to copy
	 */
	public Download(final Download download) {
		this(download.getTitle(), download.getUrl(), download.getStartDate(), download.getEndDate(), download.getUser());
		this.id = download.getId();
	}

	public final Long getId() {
		return this.id;
	}

	// Commented for safety purposes
	// public final void setId(Long id) {
	// this.id = id;
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

	public final User getUser() {
		return this.user;
	}

	public final void setUser(User user) {
		this.user = new User(user);
	}

	/**
	 * <p>The format which identifies a download. It generates a string based on 
	 * the hash code and the title which can be used for storing several downloads
	 * of the same title on the file system without collision occurring.</p>
	 * <p>The title of the download is generated from the filename of the URL.</p>
	 * <pre>
	 * example.com/example.txt --&gt; example.txt
	 * </pre>
	 * 
	 * @return a formatted string which identifies a download
	 */
	public final String getFormat() {
		return this.hashCode() + "_" + FilenameUtils.getName(getUrl());
	}

	@Override
	public int hashCode() {
		// Value to multiply the hash by is a prime, therefore reducing
		// the amount of possible hash collisions
		final int prime = 31;
		int hashCode = 1;

		// We can only use the id and the url of the download to reliably get a
		// consistent hash code, as the rest of the values can either be null
		// (start date and end date) or changeable (title)
		hashCode = prime * hashCode + ((this.id == null) ? 0 : this.id.hashCode());
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
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
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
		result.append("\tID: " + this.id + NEW_LINE);
		result.append("\tTitle: " + this.title + NEW_LINE);
		result.append("\tURL: " + this.url + NEW_LINE);
		result.append("\tStart date: " + this.startDate + NEW_LINE);
		result.append("\tEnd date: " + this.endDate + NEW_LINE);
		result.append("\tUser: " + this.user + NEW_LINE);
		result.append("}");

		return result.toString();
	}

}
