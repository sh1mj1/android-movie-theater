package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenAd
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.domain.repository.DummyMovies
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.ui.detail.ScreenDetailActivity
import woowacourse.movie.ui.home.adapter.ScreenAdapter
import woowacourse.movie.ui.home.adapter.TheaterAdapter

class HomeFragment : Fragment(), HomeContract.View {
    private lateinit var adapter: ScreenAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homePresenter: HomeContract.Presenter by lazy {
        HomePresenter(
            this,
            DummyMovies(),
            DummyScreens(),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        initAdapter()
        homePresenter.loadScreen()

        return binding.root
    }

    private fun initAdapter() {
        adapter =
            ScreenAdapter(
                { screenId -> homePresenter.loadTheaters(screenId) },
                { adId -> Toast.makeText(activity, "광고 클릭 id: $adId", Toast.LENGTH_SHORT).show() },
            )
        binding.adapter = adapter
    }

    override fun showScreens(screens: List<ScreenAd>) {
        adapter.submitList(screens)
    }

    override fun showTheaters(
        screen: Screen,
        theaters: Theaters,
    ) {
        val theaterAdapter =
            TheaterAdapter(screen) { screenId, theaterId ->
                ScreenDetailActivity.startActivity(requireContext(), screenId, theaterId)
            }

        val theaterBottomSheet = TheaterBottomSheet(theaterAdapter)

        theaterAdapter.submitList(theaters.theaters)
        theaterBottomSheet.show(parentFragmentManager, "theaterBottomSheet")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
