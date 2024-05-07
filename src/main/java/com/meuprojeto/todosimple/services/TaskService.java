package com.meuprojeto.todosimple.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meuprojeto.todosimple.models.Task;
import com.meuprojeto.todosimple.models.User;
import com.meuprojeto.todosimple.repositories.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskrepository;

	@Autowired
	private UserService userService;

	public Task findById(Long id) {
		Optional<Task> task = this.taskrepository.findById(id);
		return task.orElseThrow(
				() -> new RuntimeException("Tarefa não encontrada!" + id + ", Tipo: " + Task.class.getName()));
	}
	
	public List<Task> findAllByUserId(Long userId){
		List<Task> tasks = this.taskrepository.findByUser_Id(userId);
		return tasks;
	}

	@Transactional
	public Task create(Task obj) {
		User user = this.userService.findById(obj.getUser().getId());
		obj.setUser(user);
		obj = this.taskrepository.save(obj);
		return obj;
	}

	@Transactional
	public Task update(Task obj) {
		Task newObj = findById(obj.getId());
		newObj.setDescription(obj.getDescription());
		return this.taskrepository.save(newObj);
	}

	public void delete(Long id) {
		findById(id);
		try {
			this.taskrepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Não é possivel Excluir, pois não exite entidades relacionadas!");
		}
	}

}
