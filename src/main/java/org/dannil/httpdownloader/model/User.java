package org.dannil.httpdownloader.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 5312673884211830942L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserID")
	@JoinColumn(name = "UserID")
	private Long id;

	@Column(name = "Email")
	@NotNull
	private String email;

	@Column(name = "Password")
	@NotNull
	private String password;

	@Column(name = "Firstname")
	@NotNull
	private String firstname;

	@Column(name = "Lastname")
	@NotNull
	private String lastname;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Collection<Download> downloads;

	/**
	 * Default constructor
	 */
	public User() {

	}

	/**
	 * Overloaded constructor
	 * 
	 * @param email
	 * 					the user's email
	 * @param password
	 * 					the user's password
	 * @param firstname
	 * 					the user's firstname
	 * @param lastname
	 * 					the user's lastname
	 */
	public User(final String email, final String password, final String firstname, final String lastname) {
		this();
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param email
	 * 					the user's email
	 * @param password
	 * 					the user's password
	 * @param firstname
	 * 					the user's firstname
	 * @param lastname
	 * 					the user's lastname
	 * @param downloads
	 * 					the user's downloads
	 */
	public User(final String email, final String password, final String firstname, final String lastname, final LinkedList<Download> downloads) {
		this(email, password, firstname, lastname);
		this.downloads = new LinkedList<Download>(downloads);
	}

	/**
	 * Copy constructor
	 * 
	 * @param user 
	 * 				the object to copy
	 */
	public User(final User user) {
		this(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), new LinkedList<Download>(user.getDownloads()));
		this.id = user.getId();
	}

	public final Long getId() {
		return this.id;
	}

	public final void setId(final long id) {
		this.id = id;
	}

	public final String getEmail() {
		return this.email;
	}

	public final void setEmail(final String email) {
		this.email = email;
	}

	public final String getPassword() {
		return this.password;
	}

	public final void setPassword(final String password) {
		this.password = password;
	}

	public final String getFirstname() {
		return this.firstname;
	}

	public final void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	public final String getLastname() {
		return this.lastname;
	}

	public final void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public final LinkedList<Download> getDownloads() {
		return new LinkedList<Download>(this.downloads);
	}

	// public final void setDownloads(final List<Download> downloads) {
	// this.downloads = downloads;
	// }

	/**
	 * Add a download to the user. Performs a null check on the download 
	 * before adding it.
	 * 
	 * @param download
	 * 					the download to add
	 */
	public final void addDownload(final Download download) {
		if (download == null) {
			return;
		}
		if (this.downloads == null) {
			this.downloads = new LinkedList<Download>();
		}
		download.setUser(new User(this));
		this.downloads.add(download);
	}

	/**
	 * Delete the specified download from the user's downloads list. Performs a 
	 * null check on the download before searching the list for it.
	 * 
	 * @param download 
	 * 					the download to delete
	 */
	public final void deleteDownload(final Download download) {
		if (download == null || this.downloads == null || this.downloads.size() <= 0) {
			return;
		}
		for (Download temp : this.downloads) {
			if (temp.getId().equals(download.getId())) {
				this.downloads.remove(temp);
				break;
			}
		}
	}

	/**
	 * Return a download with the specified download ID. Performs a null check
	 * on the user's downloads list before fetching from it.
	 * 
	 * @param id
	 * 				the id of the download
	 * 
	 * @return a download with the specified id
	 */
	public final Download getDownload(final long id) {
		if (this.downloads == null || this.downloads.size() <= 0) {
			return null;
		}
		for (Download temp : this.downloads) {
			if (temp.getId().equals(id)) {
				return new Download(temp);
			}
		}
		return null;
	}

	@Override
	public final String toString() {
		final StringBuilder result = new StringBuilder();
		final String NEW_LINE = System.getProperty("line.separator");

		result.append(this.getClass().getName() + " - " + this.getClass().getSuperclass().getName() + " {" + NEW_LINE);
		result.append("\tID: " + this.id + NEW_LINE);
		result.append("\tE-mail: " + this.email + NEW_LINE);
		result.append("\tPassword: " + "[OMITTED]" + NEW_LINE);
		result.append("\tFirstname: " + this.firstname + NEW_LINE);
		result.append("\tLastname: " + this.lastname + NEW_LINE);
		result.append("}");

		return result.toString();
	}

}
