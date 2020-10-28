package com.spring.miniposbackend.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.miniposbackend.model.security.UserToken;
import com.spring.miniposbackend.service.security.JwtUserDetailsService;
import com.spring.miniposbackend.service.security.UserTokenService;
import com.spring.miniposbackend.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserTokenService userTokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (!request.getRequestURI().contentEquals("/authenticate")) {
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				try {
					UserToken userToken = userTokenService.showByApiToken(jwtToken);
					if (userToken != null) {
						username = jwtTokenUtil.getUsernameFromToken(jwtToken);
						if(!userToken.getClientAppUserIdentity().getUser().getUsername().contentEquals(username)) {
							username = null;
						}
					} else {
						username = null;
					}
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get JWT Token");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT Token has expired");
				} catch (JwtException e) {
					System.out.println("Invalid to: " + e.getMessage());
				}
			} else {
				logger.warn("JWT Token does not begin with Bearer String");
			}
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
				validateUserDetails(userDetails);
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch (UsernameNotFoundException e) {
				System.out.println(e.getMessage());

			} catch (LockedException e) {
				System.out.println(e.getMessage());
			} catch (DisabledException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		chain.doFilter(request, response);
	}

	private void validateUserDetails(UserDetails userDetails) throws DisabledException, LockedException {
		if (!userDetails.isEnabled()) {
			throw new DisabledException("USER_DISABLED");
		}
		if (!userDetails.isAccountNonLocked()) {
			throw new LockedException("USER_LOCKED");
		}
//		if (!userDetails.isAccountNonExpired()) {
//			throw new LockedException("AccountExcept");
//		}
//		if (!userDetails.isCredentialsNonExpired()) {
//			throw new LockedException("CredentialExpire");
//		}
	}
}