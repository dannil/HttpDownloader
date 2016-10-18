package com.github.dannil.httpdownloader.model;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Objects;

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
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
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
	 *            the download's title
	 * @param url
	 *            the download's URL (Uniform Resource Locator)
	 */
	public Download(String title, String url) {
		this();
		this.title = title;
		this.url = url;
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param title
	 *            the download's title
	 * @param url
	 *            the download's URL (Uniform Resource Locator)
	 * @param startDate
	 *            the date the download was started
	 */
	public Download(String title, String url, DateTime startDate) {
		this(title, url);
		this.startDate = startDate;
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param title
	 *            the download's title
	 * @param url
	 *            the download's URL (Uniform Resource Locator)
	 * @param startDate
	 *            the date the download was started
	 * @param endDate
	 *            the date the download was completed
	 * @param user
	 *            the user which owns this download
	 */
	public Download(String title, String url, DateTime startDate, DateTime endDate, User user) {
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
	 *            the object to copy
	 */
	public Download(Download download) {
		this(download.getTitle(), download.getUrl(), download.getStartDate(), download.getEndDate(), download.getUser());
		this.id = download.getId();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DateTime getStartDate() {
		return this.startDate;
	}

	/**
	 * <p>
	 * Get the start date formatted by the pattern.
	 * </p>
	 * 
	 * @return a string representation of the start date
	 */
	public String getStartDateFormatted() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		if (this.startDate != null) {
			return this.startDate.toString(format);
		}
		return null;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getEndDate() {
		return this.endDate;
	}

	/**
	 * <p>
	 * Get the end date formatted by the pattern.
	 * </p>
	 * 
	 * @return a string representation of the end date
	 */
	public String getEndDateFormatted() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		if (this.endDate != null) {
			return this.endDate.toString(format);
		}
		return null;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * <p>
	 * Return the filename of the download, which is is generated from the URL.
	 * </p>
	 * 
	 * <pre>
	 * example.com / example.txt-- &gt; example.txt
	 * </pre>
	 * 
	 * @return the download's name
	 */
	public String getFilename() {
		return FilenameUtils.getName(this.url);
	}

	/**
	 * <p>
	 * The format which identifies a download. It generates a near-unique string based on the hash
	 * code, the UID and the filename. This method can be used for storing several downloads of the
	 * same title on the file system without collision occurring.
	 * </p>
	 * 
	 * @return a formatted string which identifies a download
	 * 
	 * @see com.github.dannil.httpdownloader.model.Download#getFilename()
	 */
	public String getFormat() {
		return (this.hashCode() * serialVersionUID) + "_" + this.getFilename();
	}

	@Override
	public int hashCode() {
		// We can only use the user and the url of the download to reliably get
		// a consistent hash code, as the rest of the values can either be null
		// (start date and end date) or changeable (title)
		return Objects.hash(this.url, this.user);
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
		return Objects.equals(this.title, other.title) && Objects.equals(this.url, other.url)
				&& Objects.equals(this.startDate, other.startDate) && Objects.equals(this.endDate, other.endDate);

		// if (this.title == null) {
		// if (other.title != null) {
		// return false;
		// }
		// } else if (!this.title.equals(other.title)) {
		// return false;
		// }
		// if (this.url == null) {
		// if (other.url != null) {
		// return false;
		// }
		// } else if (!this.url.equals(other.url)) {
		// return false;
		// }
		// if (this.startDate == null) {
		// if (other.startDate != null) {
		// return false;
		// }
		// } else if (!this.startDate.equals(other.startDate)) {
		// return false;
		// }
		// if (this.endDate == null) {
		// if (other.endDate != null) {
		// return false;
		// }
		// } else if (!this.endDate.equals(other.endDate)) {
		// return false;
		// }
		// return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(64);
		builder.append("Download [id=");
		builder.append(this.id);
		builder.append(", title=");
		builder.append(this.title);
		builder.append(", url=");
		builder.append(this.url);
		builder.append(", startDate=");
		builder.append(this.startDate);
		builder.append(", endDate=");
		builder.append(this.endDate);
		builder.append(", user=");
		builder.append(this.user);
		builder.append(']');
		return builder.toString();
	}

}
