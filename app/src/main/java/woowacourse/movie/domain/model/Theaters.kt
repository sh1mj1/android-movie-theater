package woowacourse.movie.domain.model

data class Theaters(val theaters: List<Theater>) {
    constructor(vararg theaters: Theater) : this(theaters.toList())

    fun screeningTheater(movie: Movie): Theaters =
        Theaters(
            theaters
                .filter { theater ->
                    theater.screens.any { screen ->
                        screen.movie.id == movie.id
                    }
                },
        )
}
