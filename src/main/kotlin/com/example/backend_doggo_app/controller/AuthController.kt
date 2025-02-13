package com.example.backend_doggo_app.controller

import com.example.backend_doggo_app.dto.LoginDto
import com.example.backend_doggo_app.dto.RegisterDto
import com.example.backend_doggo_app.dto.UpdateUserDto
import com.example.backend_doggo_app.entity.UserEntity
import com.example.backend_doggo_app.service.UserSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    private val userSecurityService: UserSecurityService by lazy {
        applicationContext.getBean(UserSecurityService::class.java)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<Map<String, String>> {
        val response = userSecurityService.login(loginDto)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto): ResponseEntity<UserEntity> {
        val user = userSecurityService.register(registerDto)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @PutMapping("/user/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody updateUserDto: UpdateUserDto): ResponseEntity<UserEntity> {
        val updatedUser = userSecurityService.updateUser(userId, updateUserDto)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    @DeleteMapping("/user/{userId}")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<Unit> {
        userSecurityService.deleteUser(userId)
        return ResponseEntity(Unit, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<List<UserEntity>> {
        val users = userSecurityService.getAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    @GetMapping("/user/{userId}")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<UserEntity> {
        val user = userSecurityService.getUserById(userId)
        return ResponseEntity(user, HttpStatus.OK)
    }
}

