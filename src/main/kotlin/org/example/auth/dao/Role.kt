package org.example.auth.dao

import jakarta.persistence.*

@Entity
@Table(name = "roles")
class Role {

    constructor()
    constructor(name: String?) {
        this.name = name
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var name: String? = null
}