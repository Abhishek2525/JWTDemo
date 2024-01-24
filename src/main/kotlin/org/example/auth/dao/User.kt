package org.example.auth.dao

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
@Entity
@Table(
    name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @NotBlank
    @Size(min = 6, max = 20, message = "Username length must be minimum 6")
    @Column(name = "username",unique = true)
    var username: String? = null

    @NotBlank
    @Size(max = 50, message = "Email Should Be Valid")
    @Email
    @Column(name = "email",unique = true,nullable = false)
    var email: String? = null

    @NotBlank
    @Size(max = 120)
    var password: String? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Set<Role> = HashSet()

    constructor()

    constructor(username: String?, email: String?, password: String?) {
        this.username = username
        this.email = email
        this.password = password
    }
}
