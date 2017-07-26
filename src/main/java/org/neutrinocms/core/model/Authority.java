package org.neutrinocms.core.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.neutrinocms.core.model.independant.User;

import java.util.List;

@Entity
@Table(name = "authority")
public class Authority {

//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
//    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
//    private Long id;
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    @Column(name = "name", length = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}