package dev.donam.practice.application.port.out

import dev.donam.practice.application.port.`in`.CreateUserReq
import dev.donam.practice.application.port.`in`.User

interface UserDbPort {
    suspend fun getUser(userId: Long): User
    suspend fun createUser(req: CreateUserReq): User
}
