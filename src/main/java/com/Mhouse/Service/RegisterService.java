package com.Mhouse.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityNotFoundException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mhouse.Model.RegisterModel;
import com.Mhouse.Repository.RegisterRepository;

@Service
public class RegisterService {

	@Autowired
	RegisterRepository userRepository;

	public String register(String name, String mobile, String email, String password, String confirmPassword) {
		RegisterModel rm = new RegisterModel();
		rm.setName(name);
		rm.setMobile(mobile);
		rm.setEmail(email);
		rm.setPassword(password);
		rm.setConfirmPassword(confirmPassword);
		rm.setDatetime(new Date());

		if (name == null || name.isEmpty() || mobile == null || email == null || email.isEmpty() || password == null
				|| password.isEmpty() || confirmPassword.isEmpty() || confirmPassword == null) {
			return "Please Enter all details.";
		}

		// Email validation regex pattern
		String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
		Pattern emailRegex = Pattern.compile(emailPattern);
		Matcher emailMatcher = emailRegex.matcher(email);

		if (!emailMatcher.matches()) {
			return "Invalid email address.";
		}

		// Mobile number validation regex pattern
		String mobilePattern = "^[789]\\d{9}$"; // First digit is 7, 8, or 9 and followed by 9 digits
		Pattern mobileRegex = Pattern.compile(mobilePattern);
		Matcher mobileMatcher = mobileRegex.matcher(mobile);

		if (!mobileMatcher.matches()) {
			return "Invalid mobile number.";
		}

		if (!password.equals(confirmPassword)) {
			return "Password does not match";
		}

		if (userRepository.findByEmail(rm.getEmail()).isPresent()) {
			return "Already Registered";
		} else {
			userRepository.save(rm);
			return rm.getName() + " has been registered successfully";
		}
	}

	public boolean authenticate(String email, String plainTextPassword) {
		Optional<RegisterModel> rm = userRepository.findByEmail(email);
		if (rm.isPresent()) {
			RegisterModel user = rm.get();
			return user.getEmail().equals(email) && user.isPasswordConfirmed(plainTextPassword);
		}
		return false;
	}

	public RegisterModel getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	public String updateUserByEmail(String email, String name, String mobile, String password) {
		RegisterModel user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("User not found with email-id: " + email));

		// Compare the entered password with the hashed password stored in the database
		if (!BCrypt.checkpw(password, user.getPassword())) {
			return "Password is wrong.";
		} else {
			user.setName(name);
			user.setDatetime(new Date());

			// Mobile number validation regex pattern
			String mobilePattern = "^[6789]\\d{9}$"; // First digit is 7, 8, or 9 and followed by 9 digits
			Pattern mobileRegex = Pattern.compile(mobilePattern);
			Matcher mobileMatcher = mobileRegex.matcher(mobile);

			if (!mobileMatcher.matches()) {
				return "Invalid mobile number.";
			} else {
				user.setMobile(mobile);
				userRepository.save(user);
				return user.getName() + " has been updated succssfully";
			}
		}
	}

	public RegisterModel getUserById(Long userId) {
		if (userRepository.findById(userId).isPresent())
			return userRepository.findById(userId).get();
		else
			return null;
	}

	public String deleteUser(Long userId, String email, String password) {
		Optional<RegisterModel> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {
			RegisterModel user = userOptional.get();

			if (password.isEmpty()) {
				return "Please enter password";
			} else if (email.equals(user.getEmail())) {
				if (BCrypt.checkpw(password, user.getPassword())) {
					userRepository.deleteById(userId);
					return "User deleted";
				} else {
					return "Password is wrong";
				}
			} else {
				return "Email is wrong";
			}
		} else {
			return "User is not present with id: " + userId;
		}
	}

	public List<RegisterModel> getAllUser(RegisterModel getAllUsers) {
		return userRepository.findAll();
	}

//	public List<RegisterModel> getByName(String name) {
//		return userRepository.findByName(name);
//	}
//
//	public List<RegisterModel> getByMobno(String mobno) {
//		return userRepository.findByMobno(mobno);
//	}
//
//	public List<RegisterModel> getByNameOrMobno(String data) {
//		return userRepository.findByNameOrMobno(data, data);
//	}

}
