package dev.donam.practice.application.port.`in`

interface UserUseCase {
    suspend fun getUser(userId: Long): User
    suspend fun createUser(req: CreateUserReq): User
}

class CreateUserReq(
    val id: Long,
    val name: String
)

class User(
    val id: Long,
    val name: String
)
