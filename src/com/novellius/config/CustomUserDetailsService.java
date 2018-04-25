package com.novellius.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.novellius.dao.UsuarioDAO;
import com.novellius.pojo.Usuario;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Usuario usuario = usuarioDAO.findByUsername(username);
		
		if (usuario != null) {
			authorities.add(new SimpleGrantedAuthority(usuario.getPermiso()));
			User user = new User(usuario.getUsuario(), usuario.getClave(), authorities);
			return user;
		} 
		else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
	}

}
