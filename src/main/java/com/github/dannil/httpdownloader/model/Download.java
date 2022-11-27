package com.github.dannil.httpdownloader.model;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

/**
 * Encapsulates all information needed about a download. Also contains logic for
 * generating unique string identifiers for a download, and related operations.
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
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
    private LocalDateTime startDate;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    // @ManyToOne(fetch = EAGER)
    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private User user;

    /**
     * Private constructor
     */
    private Download() {
        // Needed for Spring Framework to enable autowire
    }

    /**
     * Overloaded constructor
     * 
     * @param title
     *            the downloads title
     * @param url
     *            the downloads URL (Uniform Resource Locator)
     */
    public Download(String title, String url) {
        this();
        this.title = Objects.requireNonNull(title);
        this.url = Objects.requireNonNull(url);
    }

    /**
     * Overloaded constructor
     * 
     * @param title
     *            the downloads title
     * @param url
     *            the downloads URL (Uniform Resource Locator)
     * @param startDate
     *            the date the download was started
     */
    public Download(String title, String url, LocalDateTime startDate) {
        this(title, url);
        this.startDate = Objects.requireNonNull(startDate);
    }

    /**
     * Overloaded constructor
     * 
     * @param title
     *            the downloads title
     * @param url
     *            the downloads URL (Uniform Resource Locator)
     * @param startDate
     *            the date the download was started
     * @param endDate
     *            the date the download was completed
     * @param user
     *            the user which owns this download
     */
    public Download(String title, String url, LocalDateTime startDate, LocalDateTime endDate, User user) {
        this(title, url, startDate);
        this.endDate = Objects.requireNonNull(endDate);
        setUser(user);
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

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    /**
     * <p> Get the start date formatted by the pattern. </p>
     * 
     * @return a string representation of the start date
     */
    public String getStartDateFormatted() {
        if (this.startDate != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return this.startDate.format(format);
        }
        return null;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    /**
     * <p> Get the end date formatted by the pattern. </p>
     * 
     * @return a string representation of the end date
     */
    public String getEndDateFormatted() {
        if (this.endDate != null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return this.endDate.format(format);
        }
        return null;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User newUser) {
        if (newUser == null && this.user != null) {
            this.user.deleteDownload(this);
        }
        this.user = newUser;
    }

    /**
     * <p> Return the filename of the download, which is is generated from the URL. </p>
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
     * <p> The format which identifies a download. It generates a near-unique string based
     * on the hash code and the filename. This method can be used for storing several downloads
     * of the same title for different users on the file system without collision occurring. </p>
     * 
     * @return a formatted string which identifies a download
     * 
     * @see com.github.dannil.httpdownloader.model.Download#getFilename()
     */
    public String getFormat() {
        return hashCode() + "_" + getFilename();
    }

    @Override
    public int hashCode() {
        // We can only use the user and the URL of the download to reliably get
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
        if (this.user != null) {
            builder.append(this.user.getEmail());
        } else {
            builder.append("null");
        }
        builder.append(']');
        return builder.toString();
    }

}
