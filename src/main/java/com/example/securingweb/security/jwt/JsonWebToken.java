package com.example.securingweb.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Getter
@ToString
public class JsonWebToken {
	private final String value;
	private final Integer duration;
}
