package org.dannil.httpdownloader.model;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
 * Encapsulates all information needed about an user. Also contains logic for
 * associating a download with a specific user, deleting a fetching a download.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
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
	 * Default constructor
	 */
	public User() {
		this.downloads = new LinkedList<Download>();
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

		for (Download d : downloads) {
			this.addDownload(d);
		}
	}

	/**
	 * Copy constructor
	 * 
	 * @param user 
	 * 				the object to copy
	 */
	public User(final User user) {
		this(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getDownloads());
		this.id = user.getId();
	}

	public final Long getId() {
		return this.id;
	}

	public final void setId(final Long id) {
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

	public final void setDownloads(final List<Download> downloads) {
		this.downloads = downloads;
	}

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
		if (download.getId() == null) {
			throw new IllegalArgumentException("ID can't be null");
		}
		download.setUser(new User(this));
		this.downloads.add(download);
	}

	/**
	 * Delete the download with the specified id from the user's 
	 * 
	 * @param id 
	 * 				the id of the download to delete
	 * 
	 * @see org.dannil.httpdownloader.model.User#getDownload(long)
	 */
	public final void deleteDownload(final long id) {
		this.deleteDownload(this.getDownload(id));
	}

	/**
	 * Delete the specified download from the user's downloads list. Performs a 
	 * null check on the download before searching the list for it.
	 * 
	 * @param download 
	 * 					the download to delete
	 */
	public final void deleteDownload(final Download download) {
		if (download == null) {
			return;
		}
		if (download.getId() == null) {
			throw new IllegalArgumentException("ID can't be null");
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
		if (this.downloads.size() <= 0) {
			return null;
		}
		for (Download temp : this.downloads) {
			if (temp.getId().equals(id)) {
				return temp;
			}
		}
		return null;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
		result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
		result = prime * result + ((this.firstname == null) ? 0 : this.firstname.hashCode());
		result = prime * result + ((this.lastname == null) ? 0 : this.lastname.hashCode());
		result = prime * result + ((this.downloads == null) ? 0 : this.downloads.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
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
		if (this.email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!this.email.equals(other.email)) {
			return false;
		}
		if (this.password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!this.password.equals(other.password)) {
			return false;
		}
		if (this.firstname == null) {
			if (other.firstname != null) {
				return false;
			}
		} else if (!this.firstname.equals(other.firstname)) {
			return false;
		}
		if (this.lastname == null) {
			if (other.lastname != null) {
				return false;
			}
		} else if (!this.lastname.equals(other.lastname)) {
			return false;
		}
		if (this.downloads == null) {
			if (other.downloads != null) {
				return false;
			}
		} else if (!this.downloads.equals(other.downloads)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
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
		builder.append("]");
		return builder.toString();
	}

}
