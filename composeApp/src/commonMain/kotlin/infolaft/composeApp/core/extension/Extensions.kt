package infolaft.composeApp.core.extension

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters

suspend fun HttpClient.postBodyFormData(
    url: String,
    parameters: Set<Pair<String, String>>,
): HttpResponse = post(url) {
    setBody(
        FormDataContent(
            Parameters.build {
                parameters.forEach { (key, value) ->
                    append(key, value)
                }
            }
        )
    )
}