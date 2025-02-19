package com.example.backend_doggo_app.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(@Value("\${jwt.secret}") private val secret: String) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val token = authorizationHeader.substring(7)
                val algorithm = Algorithm.HMAC256(secret)
                val verifier = JWT.require(algorithm).build()
                val decodedJWT = verifier.verify(token)
                val username = decodedJWT.subject
                val roles = decodedJWT.getClaim("roles").asList(String::class.java)
                val authorities = roles.map { SimpleGrantedAuthority(it) }
                val authenticationToken = UsernamePasswordAuthenticationToken(username, null, authorities)
                SecurityContextHolder.getContext().authentication = authenticationToken
            } catch (exception: Exception) {
                response.setHeader("error", exception.message)
                response.sendError(HttpServletResponse.SC_FORBIDDEN)
                return
            }
        }
        filterChain.doFilter(request, response)
    }
}

