package com.example.backend_doggo_app.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.backend_doggo_app.config.JwtUtil
import com.example.backend_doggo_app.dto.LoginDto
import com.example.backend_doggo_app.dto.RegisterDto
import com.example.backend_doggo_app.dto.UpdateUserDto
import com.example.backend_doggo_app.entity.RoleEntity
import com.example.backend_doggo_app.entity.UserEntity
import com.example.backend_doggo_app.repository.RoleRepository
import com.example.backend_doggo_app.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*


@Service
class UserSecurityService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) : UserDetailsService {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    private val authenticationService: AuthenticationService by lazy {
        applicationContext.getBean(AuthenticationService::class.java)
    }

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")

        val authorities = userEntity.roles.map {
            SimpleGrantedAuthority("ROLE_${it.role}")
        }

        return User(
            userEntity.email,
            userEntity.password,
            !userEntity.disabled,
            true,
            true,
            !userEntity.locked,
            authorities
        )
    }

    fun login(loginDto: LoginDto): Map<String, String> {
        val authentication = authenticationService.authenticate(loginDto.email, loginDto.password)
        val user = loadUserByUsername(loginDto.email)
        val token = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withClaim("roles", user.authorities.map { it.authority })
            .sign(Algorithm.HMAC256(secret.toByteArray()))
        return mapOf("token" to token)
    }

    fun register(registerDto: RegisterDto): UserEntity {
        val user = UserEntity().apply {
            name = registerDto.name
            email = registerDto.email
            password = passwordEncoder.encode(registerDto.password)
            registrationDate = Timestamp.valueOf(LocalDateTime.now())
            locked = false
            disabled = false
        }

        val savedUser = userRepository.save(user)

        val role = RoleEntity().apply {
            this.role = "USER"
            this.user = savedUser
        }

        roleRepository.save(role)

        return savedUser
    }

    fun updateUser(userId: Long, updateUserDto: UpdateUserDto): UserEntity {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("User not found with id: $userId") }

        user.apply {
            updateUserDto.name?.let { name = it }
            updateUserDto.email?.let { email = it }
            updateUserDto.password?.let { password = passwordEncoder.encode(it) }
        }

        return userRepository.save(user)
    }

    fun deleteUser(userId: Long) {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("User not found with id: $userId") }
        userRepository.delete(user)
    }

    fun getUserById(userId: Long): UserEntity {
        return userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("User not found with id: $userId") }
    }

    fun getAllUsers(): List<UserEntity> {
        return userRepository.findAll()
    }
}

