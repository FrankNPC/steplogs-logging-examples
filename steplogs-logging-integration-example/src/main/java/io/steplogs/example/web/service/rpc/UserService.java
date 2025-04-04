package io.steplogs.example.web.service.rpc;

import io.steplogs.example.web.model.User;
import io.steplogs.refino.Return;

public interface UserService {
	
	public Return<User> getById(Long userId);

	public Return<User> getBySessionId(String sessionId);
	
}
