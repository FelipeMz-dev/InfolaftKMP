package infolaft.composeApp.core

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

suspend inline fun <reified T> executeRequest(request: () -> HttpResponse): ResultResponse<T> = try {
    val response = request().body<String>()
    println(response)
    val jsonElement = Json.parseToJsonElement(response)
    if (jsonElement is JsonObject && "error" in jsonElement) {
        val errorResponse = Json.decodeFromJsonElement<ErrorResponse>(jsonElement)
        ResultResponse.ResultError(errorResponse)
    } else {
        val successResponse = Json.decodeFromString<T>(response)
        ResultResponse.Success(successResponse)
    }
} catch (e: ClientRequestException) {
    println("Client error: ${e.response.status}")
    ResultResponse.Error(e)
} catch (e: ServerResponseException) {
    println("Server error: ${e.response.status}")
    ResultResponse.Error(e)
} catch (e: HttpRequestTimeoutException) {
    println("Request timeout")
    ResultResponse.Error(e)
} catch (e: Exception) {
    println("Unexpected error: ${e.message}")
    ResultResponse.Error(e)
}

@Serializable
data class ErrorResponse(val error: String)

sealed class ResultResponse<out T> {
    data class Success<T>(val data: T) : ResultResponse<T>()
    data class ResultError(val error: ErrorResponse) : ResultResponse<Nothing>()
    data class Error(val error: Exception) : ResultResponse<Nothing>()
}