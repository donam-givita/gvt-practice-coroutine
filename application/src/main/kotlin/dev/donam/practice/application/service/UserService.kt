package dev.donam.practice.application.service

import dev.donam.practice.application.port.`in`.CreateUserReq
import dev.donam.practice.application.port.`in`.User
import dev.donam.practice.application.port.`in`.UserUseCase
import dev.donam.practice.application.port.out.UserDbPort

class UserService(
    private val userDbPort: UserDbPort
): UserUseCase {
    override suspend fun getUser(userId: Long): User =
        userDbPort.getUser(userId)

    override suspend fun createUser(req: CreateUserReq): User =
        userDbPort.createUser(req)
}
