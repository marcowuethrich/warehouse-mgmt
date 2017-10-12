package ch.evel.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="db_user_role")
public class UserRole extends EntityModel{
	
	@Column(name="role")
	private String role;

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> users;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
