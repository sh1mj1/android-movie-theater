package woowacourse.movie.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.domain.model.TimeReservation

class ReservationRepositoryTest {
    private lateinit var repository: ReservationRepository

    @BeforeEach
    fun setUp() {
        repository = FakeReservationRepository()
    }

    @Test
    fun `find reservation by id`() {
        val reservation = repository.findById(-1).getOrThrow()
        assertThat(reservation).isEqualTo(ReservationTicket.NULL)
    }

    @Test
    fun `find the timeReservation with timeReservationId`() {
        val timeReservation = repository.loadTimeReservation(0)

        assertThat(timeReservation).isEqualTo(TimeReservation.NULL)
    }
}
