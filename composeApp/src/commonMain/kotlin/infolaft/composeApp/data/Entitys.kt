package infolaft.composeApp.data

import infolaft.composeApp.core.EMPTY_INT
import infolaft.composeApp.core.EMPTY_STRING
import kotlinx.serialization.Serializable

@Serializable
data class AreaEntity(
    val id: Int = EMPTY_INT,
    val content: List<ContentAreaEntity>? = null,
    val title: String = EMPTY_STRING,
    val color: String = EMPTY_STRING,
    val color_name: String = EMPTY_STRING,
    val full_guide: Boolean = false,
    val museum: Int = EMPTY_INT,
)

@Serializable
data class ContentAreaEntity(
    val id: Int = EMPTY_INT,
    val files: List<FileEntity>? = null,
    val subtitle: String = EMPTY_STRING,
    val paragraphs: String = EMPTY_STRING,
    val area: Int = EMPTY_INT
)

@Serializable
data class FileEntity(
    val id: Int = EMPTY_INT,
    val file: String = EMPTY_STRING,
    val content_area: Int = EMPTY_INT
)

@Serializable
data class UserEntity(
    val id: Int = EMPTY_INT,
    val company_id: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val subtitle: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val files: String = EMPTY_STRING,
    val start_date: String = EMPTY_STRING,
    val end_date: String = EMPTY_STRING,
    val created_at: String = EMPTY_STRING,
    val updated_at: String = EMPTY_STRING,
    val user: Int = EMPTY_INT,
)

data class AccountEntity(
    val email: String,
    val company_id: String
)

@Serializable
data class ProgressEntity(val progress: Double = 0.0)

data class SessionEntity(
    val account: AccountEntity? = null,
    val user: UserEntity? = null,
)

fun SessionEntity.fromString(session: String): SessionEntity{
    val accountString = session.substringAfter("account=").substringBefore(", user=")
    val userString = session.substringAfter("user=").substringBefore(")")
    val account = AccountEntity(
        email = accountString.substringAfter("email=").substringBefore(", company_id="),
        company_id = accountString.substringAfter("company_id=").substringBefore("}"))
    val user = UserEntity(
        id = userString.substringAfter("id=").substringBefore(", company_id=").toInt(),
        company_id = userString.substringAfter("company_id=").substringBefore(", title="),
        title = userString.substringAfter("title=").substringBefore(", subtitle="),
        subtitle = userString.substringAfter("subtitle=").substringBefore(", description="),
        description = userString.substringAfter("description=").substringBefore(", files="),
        files = userString.substringAfter("files=").substringBefore(", start_date="),
        start_date = userString.substringAfter("start_date=").substringBefore(", end_date="),
        end_date = userString.substringAfter("end_date=").substringBefore(", created_at="),
        created_at = userString.substringAfter("created_at=").substringBefore(", updated_at="),
        updated_at = userString.substringAfter("updated_at=").substringBefore(", user="),
        user = userString.substringAfter("user=").substringBefore(")").toInt()
    )
    return SessionEntity(account, user)
}