package com.exam.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exam.service.implementation.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{


	@Autowired 
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//extracts token header
		final String requestTokenHeader = request.getHeader("Authorization");
		System.out.println(requestTokenHeader);
		
		String username=null;
		String jwtToken=null;
		if(requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer "))
		{
			//header contains Bearer and Token so we substring it to get token from token header
			jwtToken = requestTokenHeader.substring(7);
			try {
				//jwt contains method to extract username from token
				username = this.jwtUtils.extractUsername(jwtToken);
			}
			catch(ExpiredJwtException e){
				e.printStackTrace();
				System.out.println("jwt token expired");
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("error");
			}
		}
		else
		{
			System.out.println("Invalid Token, not starting with bearer string");
		}
		
		//When token is validated then we set it in SecurityContextHolder
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			final UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);
			if(this.jwtUtils.validateToken(jwtToken, userDetails))
			{
				//Token is Valid
				UsernamePasswordAuthenticationToken usernamePasswordauthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordauthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordauthentication);			
			}
		}
		else
		{
			System.out.println("Token is not valid");
		}
		filterChain.doFilter(request, response);
	}

}
