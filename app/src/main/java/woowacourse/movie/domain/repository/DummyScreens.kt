package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import java.time.LocalDate

class DummyScreens(
    private val seatsRepository: SeatsRepository = DummySeats(),
) : ScreenRepository {
    // TODO 더미 데이터
    private val screens =
        listOf(
            Screen(
                id = 1,
                Movie(
                    id = 1,
                    "해리 포터와 마법사의 돌",
                    151,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 15)),
            ),
            Screen(
                id = 2,
                Movie(
                    id = 2,
                    "해리 포터와 마법사의 돌2",
                    152,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 3, 17)),
            ),
            Screen(
                id = 3,
                Movie(
                    id = 3,
                    "해리 포터와 마법사의 돌3",
                    153,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 19)),
            ),
            Screen(
                id = 4,
                Movie(
                    id = 1,
                    "해리 포터와 마법사의 돌",
                    151,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 20)),
            ),
            Screen(
                id = 5,
                Movie(
                    id = 2,
                    "해리 포터와 마법사의 돌2",
                    152,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 21)),
            ),
            Screen(
                id = 6,
                Movie(
                    id = 3,
                    "해리 포터와 마법사의 돌3",
                    153,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 24)),
            ),
            Screen(
                id = 7,
                Movie(
                    id = 1,
                    "해리 포터와 마법사의 돌",
                    151,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 25)),
            ),
            Screen(
                id = 8,
                Movie(
                    id = 2,
                    "해리 포터와 마법사의 돌2",
                    152,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 25)),
            ),
            Screen(
                id = 9,
                Movie(
                    id = 3,
                    "해리 포터와 마법사의 돌3",
                    153,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 26)),
            ),
            Screen(
                id = 10,
                Movie(
                    id = 1,
                    "해리 포터와 마법사의 돌",
                    151,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 27)),
            ),
            Screen(
                id = 11,
                Movie(
                    id = 2,
                    "해리 포터와 마법사의 돌2",
                    152,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 28)),
            ),
            Screen(
                id = 12,
                Movie(
                    id = 3,
                    "해리 포터와 마법사의 돌3",
                    153,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 29)),
            ),
        )

    override fun load(): List<Screen> = screens

    override fun findById(id: Int): Result<Screen> = runCatching { screens.find { it.id == id } ?: throw NoSuchElementException() }

    override fun seats(screenId: Int): Seats = seatsRepository.findById(screenId)
}
