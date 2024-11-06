package infolaft.composeApp.data

import infolaft.composeApp.core.ResultResponse

class InfolaftRepository(
    private val infolaftService: InfolaftService,
    private val infolaftCache: InfolaftCache,
) {

    suspend fun login(
        email: String,
        code: String,
    ) = infolaftService.login(email, code)

    fun saveAccount(accountEntity: AccountEntity) {
        infolaftCache.saveAccount(accountEntity)
    }

    fun getAccount(): AccountEntity? {
        return infolaftCache.getAccount()
    }

    fun getUser(): UserEntity? {
        return infolaftCache.getUser()
    }

    fun saveUser(userEntity: UserEntity) {
        infolaftCache.saveUser(userEntity)
    }

    suspend fun getAreas(isFull: Boolean) = infolaftService.getAreas(isFull)

    suspend fun saveProgress(
        account: AccountEntity,
        areaId: Int,
        isFull: Boolean,
    ): ResultResponse<ProgressEntity> = infolaftService.saveProgress(
        account = account,
        areaId = areaId,
        isFull = isFull
    )

    fun saveSession(sessionEntity: SessionEntity) {
        infolaftCache.saveSession(sessionEntity)
    }

    fun loadSession() {
        infolaftCache.loadSession()
    }

    fun clearSession() {
        infolaftCache.clearSession()
    }

}