package org.dannil.httpdownloader.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserID")
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

	public final Long getUserId() {
		return this.userId;
	}

	// Commented for safety purposes
	// public final void setUserId(final Long userId) {
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
