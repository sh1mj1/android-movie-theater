package woowacourse.movie.domain.repository

import woowacourse.movie.R
import woowacourse.movie.domain.model.Image
import woowacourse.movie.ui.ScreenAd

class DummyAdvertisement : AdRepository {
    override fun load(): ScreenAd.Advertisement =
        ScreenAd.Advertisement(
            0,
            Image.DrawableImage(R.drawable.advertisement),
        )
}

interface AdRepository {
    fun load(): ScreenAd.Advertisement
}
