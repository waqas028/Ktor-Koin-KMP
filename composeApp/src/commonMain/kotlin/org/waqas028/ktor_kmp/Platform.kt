package org.waqas028.ktor_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform