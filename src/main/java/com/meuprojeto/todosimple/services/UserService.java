package com.meuprojeto.todosimple.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meuprojeto.todosimple.models.User;
import com.meuprojeto.todosimple.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		return user.orElseThrow(() -> new RuntimeException(
				"Usuario não Encontrado! ID: " + id + ", Tipo: " + User.class.getName()
			));
	}
	
	@Transactional
	public User create(User obj) {
		obj.setId(null);
		obj = this.userRepository.save(obj);
		return obj;
	}
	
	@Transactional
	public User update(User obj) {
		User newObj = findById(obj.getId());
		newObj.setPassword(obj.getPassword());
		return this.userRepository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			this.userRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Não é possivel Excluir, pois não exite entidades relacionadas!");
		}
	}
	
}
