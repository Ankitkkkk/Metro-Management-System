package com.mms.mms.security

import com.mms.mms.security.service.UserAuthService
import com.mms.mms.security.token.JwtUtil
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val jwtUtil: JwtUtil,
                              private val userAuthService: UserAuthService
                              ) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // get Jwt
        // check for Bearer
        // validate

        val authorizationToken: String? = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String? = null

        if((authorizationToken != null) && authorizationToken.startsWith("Bearer ")) {
            jwtToken = authorizationToken.substring(7)

            try{
                username = jwtUtil.extractUsername(jwtToken)

            }catch (e: Exception) {
                e.printStackTrace()
                throw e
            }

            //security check
            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails: UserDetails = this.userAuthService.loadUserByUsername(username)

                val usernamePasswordAuthenticationToken : UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails,null,userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
            else throw Exception("token not validated")

        }
        filterChain.doFilter(request,response)
    }

}