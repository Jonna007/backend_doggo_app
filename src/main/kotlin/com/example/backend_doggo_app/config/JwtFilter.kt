package com.example.backend_doggo_app.config

import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (!jwtToken.isNullOrEmpty()) {
            jwtToken = jwtToken.substring(7)

            val decodedJWT: DecodedJWT = jwtUtil.validateToken(jwtToken)

            val username: String = jwtUtil.extractUsername(decodedJWT)
            val stringAuthorities: String = jwtUtil.getSpecificClaim(decodedJWT, "authorities").asString()

            val authorities: Collection<GrantedAuthority?> =
                AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities)

            val context = SecurityContextHolder.createEmptyContext()
            val authenticationToken: Authentication = UsernamePasswordAuthenticationToken(username, null, authorities)
            context.authentication = authenticationToken
            SecurityContextHolder.setContext(context)
        }
        filterChain.doFilter(request, response)
    }
}

