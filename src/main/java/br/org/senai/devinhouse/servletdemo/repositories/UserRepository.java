package br.org.senai.devinhouse.servletdemo.repositories;

import java.util.ArrayList;
import java.util.List;

import br.org.senai.devinhouse.servletdemo.domain.User;
import br.org.senai.devinhouse.servletdemo.exceptions.UserNotFoundException;

public class UserRepository {

	private static UserRepository userRepository;
	
	private int maxId = 1; 
	
	private List<User> users;
	
	private UserRepository() {
		this.users = new ArrayList<>();
	}
	
	public List<User> getAllUsers() {
		return this.users;
	}
	
	public User getUserById(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		throw new UserNotFoundException("Usuário com id " + id + " não encontrado");
	}
	
	public User create(User user) {
		user.setId(maxId++);
		this.users.add(user);
		return user;
	}
	
	public User update(int id, User updatedUser) {
		User currentUser = this.getUserById(id);
		currentUser.setName(updatedUser.getName());
		currentUser.setAge(updatedUser.getAge());
		return currentUser;
		
	}
	
	public void remove(int id) {
		User currentUser = this.getUserById(id);
		users.remove(currentUser);
	}
	
	public static UserRepository getRepository() {
		if (userRepository == null) {
			userRepository = new UserRepository();
		}
		return userRepository;
	}
	
	
	
}
