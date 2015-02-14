package org.dannil.httpdownloader.model;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * Encapsulates all information needed about a download. Also contains logic for
 * generating unique string identifiers for a download, and related operations.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@Component
@Entity
@Table(name = "downloads")
public class Download implements Serializable {

	private static final long serialVersionUID = -9127457928753578088L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "DownloadID")
	private Long id;

	@Column(name = "Title", length = 50, nullable = false)
	@NotNull
	private String title;

	@Column(name = "URL", length = 100, nullable = false)
	@NotNull
	private String url;

	@Column(name = "StartDate")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startDate;

	@Column(name = "EndDate")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime endDate;

	@ManyToOne(fetch = EAGER)
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
	public Download(final String title, final String url, final DateTime startDate) {
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
	public Download(final String title, final String url, final DateTime startDate, final DateTime endDate, final User user) {
		this(title, url, startDate);
		this.endDate = endDate;

		if (user != null) {
			this.user = new User(user);
		}
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

	public final void setId(final Long id) {
		this.id = id;
	}

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

	public final DateTime getStartDate() {
		return this.startDate;
	}

	/**
	 * <p>Get the start date formatted by the pattern.</p>
	 * 
	 * @return a string representation of the start date
	 */
	public final String getStartDateFormatted() {
		final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		if (this.startDate != null) {
			return this.startDate.toString(format);
		}
		return null;
	}

	public final void setStartDate(final DateTime startDate) {
		this.startDate = startDate;
	}

	public final DateTime getEndDate() {
		return this.endDate;
	}

	/**
	 * <p>Get the end date formatted by the pattern.</p>
	 * 
	 * @return a string representation of the end date
	 */
	public final String getEndDateFormatted() {
		final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		if (this.endDate != null) {
			return this.endDate.toString(format);
		}
		return null;
	}

	public final void setEndDate(final DateTime endDate) {
		this.endDate = endDate;
	}

	public final User getUser() {
		return this.user;
	}

	public final void setUser(final User user) {
		this.user = user;
	}

	/**
	 * <p>Return the filename of the download, which is is generated from the URL.</p>
	 * <pre>
	 * example.com/example.txt --&gt; example.txt
	 * </pre>
	 * @return the download's name
	 */
	public final String getFilename() {
		return FilenameUtils.getName(getUrl());
	}

	/**
	 * <p>The format which identifies a download. It generates a near-unique string based on 
	 * the hash code, the UID and the filename. This method can be used for storing several 
	 * downloads of the same title on the file system without collision occurring.</p>
	 * 
	 * @return a formatted string which identifies a download
	 * 
	 * @see org.dannil.httpdownloader.model.Download#getFilename()
	 */
	public final String getFormat() {
		return (this.hashCode() * serialVersionUID) + "_" + this.getFilename();
	}

	@Override
	public final int hashCode() {
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
	public final boolean equals(final Object obj) {
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
		if (this.startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!this.startDate.equals(other.startDate)) {
			return false;
		}
		if (this.endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!this.endDate.equals(other.endDate)) {
			return false;
		}
		return true;
	}

	@Override
	public final String toString() {
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
