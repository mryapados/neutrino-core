package org.neutrinocms.core.dao;

import org.neutrinocms.core.model.independant.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface UserDao extends BaseDao<User> {
	
	@Query("SELECT u FROM #{#entityName} u WHERE u.login =:login")
	User FindByLogin(@Param("login") String login);

}
