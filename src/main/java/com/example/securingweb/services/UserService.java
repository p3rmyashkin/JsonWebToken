package com.example.securingweb.services;

import com.example.securingweb.entites.Role;
import com.example.securingweb.entites.User;
import com.example.securingweb.repository.RoleRepository;
import com.example.securingweb.repository.UserRepository;
import com.example.securingweb.request.UsernamePasswordRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.securingweb.SecuringWebApplication.ADMIN_ROLE_NAME;
import static com.example.securingweb.SecuringWebApplication.USER_ROLE_NAME;

@Service
public class UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final RoleRepository roleRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User createAdmin(UsernamePasswordRequestBody body){
		return createUser(body, ADMIN_ROLE_NAME);
	}

	public User createUser(UsernamePasswordRequestBody body) {
		return createUser(body, USER_ROLE_NAME);
	}

	public User createUser(UsernamePasswordRequestBody body, String role) {
		String username = body.getUsername();
		String password = body.getPassword();

		if (userRepository.findByUsername(username) == null) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(password));
			user.setRole(roleRepository.findByRoleName(role));
			return userRepository.save(user);
		}

		return null;
	}



	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
