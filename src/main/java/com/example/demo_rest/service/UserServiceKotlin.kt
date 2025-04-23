package com.example.demo_rest.service

import com.example.demo_rest.dto.EditUserDtoKotlin
import com.example.demo_rest.dto.UserShowDto
import com.example.demo_rest.entity.Role
import com.example.demo_rest.entity.User
import com.example.demo_rest.exception.UserException
import com.example.demo_rest.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
open class UserServiceKotlin @Autowired constructor (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
){

    fun getAllUsers(): List<UserShowDto> = userRepository.findAll().map { UserShowDto(it) }

    fun checkUserAccess(id: Long, toDelete: Boolean): User {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentUser = authentication.principal as User

        if (currentUser.id != id && currentUser.role != Role.ADMIN) {
            throw AccessDeniedException("Tylko Admin może edytować lub usuwać nie swoje konto")
        }
        if (currentUser.id == id && toDelete) {
            throw AccessDeniedException("Nie możesz usunąć swojego konta, skontaktuj się z Adminem")
        }

        return userRepository.findById(id).orElseThrow { UserException("Brak użytkownika o id: $id") }
    }

    fun editUser(editUserDto: EditUserDtoKotlin, id: Long): UserShowDto {
        var password = editUserDto.password
        val user = checkUserAccess(id, false).apply {
            name = editUserDto.name
            phoneNumber = editUserDto.phoneNumber
            password = passwordEncoder.encode(password)
        }
        userRepository.save(user)
        return UserShowDto(user)
    }

    fun deleteUser(id: Long): UserShowDto {
        val user = checkUserAccess(id, true)
        userRepository.delete(user)
        return UserShowDto(user)
    }

    fun getUserVerificationCode(id: Long): String {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val currentUser = authentication.principal as User

        if (currentUser.role != Role.ADMIN) {
            throw AccessDeniedException("Tylko Admin może sprawdzić kod weryfikacyjny")
        }
        if (currentUser.id == id) {
            throw AccessDeniedException("Nie możesz dostać kodu weryfikacyjnego swojego konta, sprawdź swój adres email")
        }

        val user = userRepository.findById(id).orElseThrow { UserException("Brak użytkownika o id: $id") }
        val verificationCode: String? = user.verificationCode
        return verificationCode ?: throw UserException("Konto jest już zweryfikowane")
    }
}
