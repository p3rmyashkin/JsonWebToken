package com.example.securingweb.entites;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private long id;

	@Column(unique = true)
	private String username;
	private String password;

	@ManyToOne
	private Role role;

	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
