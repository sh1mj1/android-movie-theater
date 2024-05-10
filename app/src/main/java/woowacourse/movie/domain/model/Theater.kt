package woowacourse.movie.domain.model

data class Theater(
    val id: Int,
    val name: String,
    val screens: List<Screen>,
) {
    fun hasScreen(screen: Screen): Boolean = screens.contains(screen)

    fun allScreeningTimeCount(screen: Screen): Int = screen.allScreeningTime(WeeklyScreenTimePolicy()).count()

    companion object {
        val NULL = Theater(0, "", emptyList())
    }
}
