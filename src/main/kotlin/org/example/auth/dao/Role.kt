package org.example.auth.dao

import jakarta.persistence.*
import lombok.*

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
class Role{
    constructor()
    constructor(name: String?) {
        this.name = name
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

   // @Enumerated(EnumType.STRING)
   @Column(name = "name",unique = true,nullable = false)
    var name: String? = null
}