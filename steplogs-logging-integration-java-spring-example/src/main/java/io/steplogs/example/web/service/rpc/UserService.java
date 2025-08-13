package io.steplogs.example.web.service.rpc;

import io.steplogs.example.web.model.User;

public interface UserService {
	
	public User getById(Long userId);

	public User getBySessionId(String sessionId);
	
	public User save(User user);
	
}
