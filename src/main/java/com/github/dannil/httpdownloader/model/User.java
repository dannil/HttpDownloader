package com.github.dannil.httpdownloader.model;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

/**
 * Encapsulates all information needed about an user. Also contains logic for associating
 * a download with a specific user, deleting a fetching a download.
 *
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 5312673884211830942L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "UserID")
    @JoinColumn(name = "UserID")
    private Long id;

    @Column(name = "Email", length = 128, nullable = false)
    @NotNull
    private String email;

    @Column(name = "Password", length = 512, nullable = false)
    @NotNull
    private String password;

    @Column(name = "Firstname", length = 50, nullable = false)
    @NotNull
    private String firstname;

    @Column(name = "Lastname", length = 50, nullable = false)
    @NotNull
    private String lastname;

    @OneToMany(mappedBy = "user", fetch = EAGER)
    private Collection<Download> downloads;

    /**
     * Private constructor
     */
    private User() {
        // Constructor needed for Spring Framework to enable autowire

        this.downloads = new ArrayList<>();
    }

    /**
     * Overloaded constructor
     *
     * @param email
     *            the users email
     * @param password
     *            the users password
     * @param firstname
     *            the users firstname
     * @param lastname
     *            the users lastname
     */
    public User(String email, String password, String firstname, String lastname) {
        this();
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.firstname = Objects.requireNonNull(firstname);
        this.lastname = Objects.requireNonNull(lastname);
    }

    /**
     * Overloaded constructor
     *
     * @param email
     *            the users email
     * @param password
     *            the users password
     * @param firstname
     *            the users firstname
     * @param lastname
     *            the users lastname
     * @param downloads
     *            the users downloads
     */
    public User(String email, String password, String firstname, String lastname, Collection<Download> downloads) {
        this(email, password, firstname, lastname);

        for (Download d : downloads) {
            addDownload(d);
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Collection<Download> getDownloads() {
        return this.downloads;
    }

    public void setDownloads(Collection<Download> downloads) {
        this.downloads = downloads;
    }

    /**
     * Add a download to the user. Performs a null check on the download before adding it.
     *
     * @param download
     *            the download to add
     */
    public void addDownload(Download download) {
        if (download == null) {
            return;
        }
        if (download.getId() == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        download.setUser(this);
        this.downloads.add(download);
    }

    /**
     * Delete the download with the specified id
     *
     * @param id
     *            the id of the download to delete
     *
     * @see com.github.dannil.httpdownloader.model.User#getDownload(long)
     */
    public void deleteDownload(long id) {
        deleteDownload(getDownload(id));
    }

    /**
     * Delete the specified download from the users downloads list. Performs a null check
     * on the download before searching the list for it.
     *
     * @param download
     *            the download to delete
     */
    public void deleteDownload(Download download) {
        if (download == null) {
            return;
        }
        if (download.getId() == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        Download d = getDownload(download.getId());
        this.downloads.remove(d);
    }

    /**
     * Return a download with the specified download ID. Performs a null check on the
     * users downloads list before fetching from it.
     *
     * @param id
     *            the id of the download
     *
     * @return a download with the specified id
     */
    public Download getDownload(long id) {
        if (this.downloads.isEmpty()) {
            return null;
        }
        for (Download d : this.downloads) {
            if (Objects.equals(id, d.getId())) {
                return d;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email, this.password, this.firstname, this.lastname, this.downloads);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;
        return Objects.equals(this.email, other.email) && Objects.equals(this.password, other.password)
                && Objects.equals(this.firstname, other.firstname) && Objects.equals(this.lastname, other.lastname)
                && Objects.equals(this.downloads, other.downloads);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        builder.append("User [id=");
        builder.append(this.id);
        builder.append(", email=");
        builder.append(this.email);
        builder.append(", password=");
        builder.append(this.password);
        builder.append(", firstname=");
        builder.append(this.firstname);
        builder.append(", lastname=");
        builder.append(this.lastname);
        builder.append(", downloads=");
        builder.append(this.downloads);
        builder.append(']');
        return builder.toString();
    }

}
