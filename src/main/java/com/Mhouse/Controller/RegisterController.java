package com.Mhouse.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Mhouse.Model.RegisterModel;
import com.Mhouse.Service.RegisterService;

@RestController
@CrossOrigin("*")
public class RegisterController {

	@Autowired
	RegisterService userService;

	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestParam("name") String name, @RequestParam("mobile") String mobile,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("cnfpassword") String confirmPassword) {
		String user = userService.register(name, mobile, email, password, confirmPassword);
		return ResponseEntity.ok(user);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam("email") String email,
			@RequestParam("password") String password) {

		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			return ResponseEntity.badRequest().body("Email and password are required.");
		}

		boolean isAuthenticated = userService.authenticate(email, password);

		if (isAuthenticated) {
			return ResponseEntity.ok("Authentication successful");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
		}
	}

	@GetMapping("/getUserByEmail")
	public ResponseEntity<RegisterModel> getUserByEmail(@RequestParam String email) {
		RegisterModel user = userService.getUserByEmail(email);
		if (user != null) {
			return ResponseEntity.ok(user); // Email found, return the user
		} else {
			return ResponseEntity.notFound().build(); // Email not found, return 404 Not Found
		}
	}

	@PutMapping("/updateByEmail/{email}")
	public String updateUserByEmail(@PathVariable String email, String name, String mobile, String password) {
		return userService.updateUserByEmail(email, name, mobile, password);
	}

	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam Long userId, @RequestParam String email, @RequestParam String password) {
		return userService.deleteUser(userId, email, password);
	}

	@GetMapping("/getAllUser")
	public List<RegisterModel> getAllUser(RegisterModel getAllUsers) {
		return userService.getAllUser(getAllUsers);
	}

//	@GetMapping("/getById")
//	public Optional<RegisterModel> getById(Long userId) {
//		return userService.getById(userId);
//	}
//
//	@GetMapping("/getByName")
//	public List<RegisterModel> getByName(String name) {
//		return userService.getByName(name);
//	}
//
//	@GetMapping("/getByMobno")
//	public List<RegisterModel> getByMobno(String mobno) {
//		return userService.getByMobno(mobno);
//	}
//
//	@GetMapping("/getByNameOrMobno")
//	public List<RegisterModel> findByNameOrMobno(@RequestParam("data") String data) {
//		return userService.getByNameOrMobno(data);
//	}

}