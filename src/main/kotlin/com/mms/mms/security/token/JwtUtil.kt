package com.mms.mms.security.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class JwtUtil {
    private val SECRET_KEY = "9a81f763-318b-44e2-8a21-dbc183b70d6c"

    fun extractUsername (token: String) : String {
        return extractClaim(token,Claims::getSubject)
    }


    fun extractExpiration(token: String) : Date {
        return extractClaim(token,Claims::getExpiration)
    }

    private fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>) : T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String) : Claims {
        return Jwts
            .parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims : MutableMap<String, Any> = HashMap()
        return createToken(claims,userDetails.username)
    }

    private fun createToken (claims: MutableMap<String, Any>,subject: String): String {
        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000*60*60*10))
            .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
            .compact()
    }

    fun validateToken(token: String,userDetails: UserDetails): Boolean {
        val username: String = extractUsername(token)
        return (username == userDetails.username) and !isTokenExpired(token)
    }

}