package br.org.senai.devinhouse.servletdemo.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.senai.devinhouse.servletdemo.domain.User;
import br.org.senai.devinhouse.servletdemo.exceptions.UserNotFoundException;
import br.org.senai.devinhouse.servletdemo.repositories.UserRepository;

@WebServlet(name = "jsonServlet", value = "/json-servlet/*")
public class JsonServlet extends HttpServlet {

	private UserRepository userRepository;
	
	public void init() {
		userRepository = UserRepository.getRepository();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter writer = resp.getWriter();
		if (req.getPathInfo() != null) {
			try {
				String[] params = req.getPathInfo().split("/");
				User user = userRepository.getUserById(Integer.parseInt(params[1]));
				writer.append(mapper.writeValueAsString(user));
			} catch (UserNotFoundException e) {
				writer.append(mapper.writeValueAsString(e.getMessage()));
				resp.setStatus(404);
			}
			
		} else {
			List<User> users = userRepository.getAllUsers();
			writer.append(mapper.writeValueAsString(users));
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		String body = readBody(req);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.readValue(body, User.class);
			user = userRepository.create(user);
			PrintWriter writer = resp.getWriter();
			writer.append(mapper.writeValueAsString(user));
			resp.setStatus(201);
		} catch (Exception e) {
			resp.setStatus(500);
		}		
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter writer = resp.getWriter();
		
		if (req.getPathInfo() != null) {
			String[] params = req.getPathInfo().split("/");
			if (params.length != 2) {
				resp.setStatus(400);
			} else {
				try {
					String body = readBody(req);
					User updatedUser = mapper.readValue(body, User.class);
					updatedUser = userRepository.update(Integer.parseInt(params[1]), updatedUser);
					resp.setStatus(200);
					writer.append(mapper.writeValueAsString(updatedUser));
				} catch (UserNotFoundException e) {
					writer.append(mapper.writeValueAsString(e.getMessage()));
					resp.setStatus(404);
				}
				
			}
		} else {
			resp.setStatus(400);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter writer = resp.getWriter();
		
		if (req.getPathInfo() != null) {
			String[] params = req.getPathInfo().split("/");
			if (params.length != 2) {
				resp.setStatus(400);
			} else {
				try {
					userRepository.remove(Integer.parseInt(params[1]));
					resp.setStatus(204);
				} catch (UserNotFoundException e) {
					writer.append(mapper.writeValueAsString(e.getMessage()));
					resp.setStatus(404);
				}
				
			}
		} else {
			resp.setStatus(400);
		}
	}

	private String readBody(HttpServletRequest req) throws ServletException, IOException {
		return req.getReader().lines().collect(Collectors.joining());
	}
	

}
