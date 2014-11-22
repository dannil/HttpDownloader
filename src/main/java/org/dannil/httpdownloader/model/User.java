package org.dannil.httpdownloader.model;

import java.io.Serializable;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
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
	private Long userId;

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

	@Transient
	private LinkedList<Download> downloads;

	public User() {

	}

	public User(final String email, final String password, final String firstname, final String lastname) {
		this();
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public final Long getUserId() {
		return this.userId;
	}

	// Commented for safety purposes
	// public final void setUserId(final long userId) {
	// this.userId = userId;
	// }

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
		return this.downloads;
	}

	public final void setDownloads(final LinkedList<Download> downloads) {
		this.downloads = downloads;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		final String NEW_LINE = System.getProperty("line.separator");

		result.append(this.getClass().getName() + " - " + this.getClass().getSuperclass().getName() + " {" + NEW_LINE);
		result.append("\tUserID: " + this.userId + NEW_LINE);
		result.append("\tE-mail: " + this.email + NEW_LINE);
		result.append("\tPassword: " + "[OMITTED]" + NEW_LINE);
		result.append("\tFirstname: " + this.firstname + NEW_LINE);
		result.append("\tLastname: " + this.lastname + NEW_LINE);
		result.append("}");

		return result.toString();
	}

}
