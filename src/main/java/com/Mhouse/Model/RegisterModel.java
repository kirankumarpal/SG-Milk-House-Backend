package com.Mhouse.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.mindrot.jbcrypt.BCrypt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RegisterModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	private String mobile;
	private String email;
	private String password;
	private String confirmPassword;
	private Date datetime;

	public void setPassword(String password) {
		// Hash the password before storing it
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public void setConfirmPassword(String confirmPassword) {
		// Hash the confirmPassword before storing it
		this.confirmPassword = BCrypt.hashpw(confirmPassword, BCrypt.gensalt());
	}

	public boolean isPasswordConfirmed(String plainTextPassword) {
		// Verify if the plain text password matches the stored hashed password
		return BCrypt.checkpw(plainTextPassword, this.password);
	}

	public boolean isConfirmPasswordMatch(String plainTextConfirmPassword) {
		// Verify if the plain text confirmPassword matches the stored hashed
		// confirmPassword
		return BCrypt.checkpw(plainTextConfirmPassword, this.confirmPassword);
	}

}
