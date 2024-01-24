package org.example.auth.dao

import jakarta.persistence.*
import lombok.Data
import java.time.Instant

@Data
@Entity(name = "token")
class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null

    @Column(nullable = false, unique = true)
    var token: String? = null

    @Column(nullable = false)
    var expiryDate: Instant? = null
}