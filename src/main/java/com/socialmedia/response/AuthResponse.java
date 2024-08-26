package com.socialmedia.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

//	public AuthResponse(String jwt, boolean status) {
//		super();
//		this.jwt = jwt;
//		this.status = status;
//	}
	private String jwt;
	private boolean status;

}
