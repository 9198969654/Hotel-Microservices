package com.lcwd.user.service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "micro_users")
public class User {

    @Id
    @Column(name = "ID")
    private String userId;
    @Column(name = "NAME" , length = 20)
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ABOUT")
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();

    // Builder class
    public static class Builder {
        private User user = new User();

        public Builder email(String email) {
            user.email = email;
            return this;
        }
        public Builder name(String name) {
            user.name = name;
            return this;
        }

        public Builder about(String about) {
            user.about = about;
            return this;
        }

        public Builder userId(String userId) {
            user.userId = userId;
            return this;
        }

        public User build() {
            return user;
        }
    }
    // Static method to create a new builder instance
    public static Builder builder() {
        return new Builder();
    }

}
