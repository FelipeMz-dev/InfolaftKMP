package infolaft.composeApp.core.manager

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

object DateTimeManager {

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun getCurrentDateTime(): String {
        val currentMoment = Clock.System.now()
        val currentDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        return formatter.format(currentDateTime.date)
    }

    fun isDateAfterCurrent(dateString: String): Boolean = try {
        val currentDate = LocalDate.parse(getCurrentDateTime())
        val providedDate = LocalDate.parse(dateString)
        providedDate >= currentDate
    } catch (e: Exception) {
        false
    }
}