package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MoviePoster

class FakeMovieRepository : MovieRepository {
    private val movies = listOf(fakeMovie1, fakeMovie2, fakeMovie3)

    private val movieImages =
        listOf(
            MoviePoster(1, Image.StringImage("1")),
            MoviePoster(2, Image.StringImage("2")),
            MoviePoster(3, Image.StringImage("3")),
        )

    override fun findById(id: Int): Movie = movies.find { it.id == id } ?: throw NoSuchElementException()

    override fun imageSrc(id: Int): Image<Any> = movieImages.find { it.movieId == id }?.poster ?: throw NoSuchElementException()

    companion object {
        val fakeMovie1 = Movie(1, "title1", 1, "description1")
        val fakeMovie2 = Movie(2, "title2", 2, "description2")
        val fakeMovie3 = Movie(3, "title3", 3, "description3")
    }
}
