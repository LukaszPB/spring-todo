package com.zawadzkia.springtodo.user;

import com.zawadzkia.springtodo.auth.Authority;
import com.zawadzkia.springtodo.task.TaskModel;
import com.zawadzkia.springtodo.task.category.TaskCategoryModel;
import com.zawadzkia.springtodo.task.status.TaskStatusModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "users")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uc_usermodel_username", columnNames = { "username" })
})
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Column(nullable = true, unique = true)
    private String username;

    @Column(nullable = true, unique = true)
    private String password;

    @Column(nullable = true)
    private boolean enabled;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "owner", orphanRemoval = true) private Set<TaskModel> tasks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "owner", orphanRemoval = true) private Set<TaskCategoryModel> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy= "owner", orphanRemoval = true) private Set<TaskStatusModel> statuses = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserModel user = (UserModel) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "username = " + username + ", " +
                "enabled = " + enabled + ")";
    }
}
