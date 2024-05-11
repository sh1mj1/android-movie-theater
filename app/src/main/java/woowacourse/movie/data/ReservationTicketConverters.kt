package woowacourse.movie.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ReservationTicketConverters {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    private val gson: Gson =
        GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, dateTypeAdapter())
            .create()

    private fun dateTypeAdapter() =
        object : TypeAdapter<LocalDate>() {
            override fun write(
                out: JsonWriter,
                value: LocalDate?,
            ) {
                if (value == null) {
                    out.nullValue()
                } else {
                    out.value(value.format(dateFormatter))
                }
            }

            override fun read(`in`: JsonReader): LocalDate? {
                return if (`in`.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    null
                } else {
                    LocalDate.parse(`in`.nextString(), dateFormatter)
                }
            }
        }

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.format(dateFormatter)

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it, dateFormatter) }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? = value?.format(timeFormatter)

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it, timeFormatter) }

    @TypeConverter
    fun fromScreen(screen: Screen?): String? = gson.toJson(screen)

    @TypeConverter
    fun toScreen(screenString: String?): Screen? = gson.fromJson(screenString, Screen::class.java)

    @TypeConverter
    fun fromSeats(seats: Seats?): String? = gson.toJson(seats)

    @TypeConverter
    fun toSeats(seatsString: String?): Seats? = gson.fromJson(seatsString, Seats::class.java)

    @TypeConverter
    fun fromTheater(theater: Theater?): String? = gson.toJson(theater)

    @TypeConverter
    fun toTheater(theaterString: String?): Theater? = gson.fromJson(theaterString, Theater::class.java)
}
