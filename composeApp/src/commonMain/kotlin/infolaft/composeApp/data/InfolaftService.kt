package infolaft.composeApp.data

import infolaft.composeApp.core.ResultResponse
import infolaft.composeApp.core.executeRequest
import infolaft.composeApp.core.KEY_AREA_ID
import infolaft.composeApp.core.KEY_COMPANY_ID
import infolaft.composeApp.core.KEY_EMAIL
import infolaft.composeApp.core.KEY_FULL_GUIDE
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters

class InfolaftService(
    private val client: HttpClient,
) {
    suspend fun login(
        email: String,
        code: String,
    ): ResultResponse<UserEntity> = executeRequest<UserEntity> {
        client.post("/campaign_management/museum/active/") {
            setBody(FormDataContent(Parameters.build {
                append(KEY_EMAIL, email)
                append(KEY_COMPANY_ID, code)
            }))
        }
    }

    suspend fun getAreas(isFull: Boolean): ResultResponse<List<AreaEntity>> = executeRequest {
        client.get("/campaign_management/museum/areas/1/?full_guide=$isFull")
    }

    suspend fun saveProgress(
        account: AccountEntity,
        areaId: Int,
        isFull: Boolean,
    ): ResultResponse<ProgressEntity> = executeRequest {
        client.post("/campaign_management/museum/save-progress/") {
            setBody(FormDataContent(Parameters.build {
                append(KEY_EMAIL, account.email)
                append(KEY_COMPANY_ID, account.company_id)
                append(KEY_AREA_ID, areaId.toString())
                append(KEY_FULL_GUIDE, isFull.toString())
            }))
        }
    }
}