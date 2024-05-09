package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.ui.ScreenAd
import woowacourse.movie.ui.home.adapter.ScreenAdapter

class HomeFragment : Fragment(), HomeContract.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homePresenter: HomeContract.Presenter by lazy {
        HomePresenter(this)
    }
    private lateinit var screenAdapter: ScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        homePresenter.loadScreen()
    }

    private fun initAdapter() {
        screenAdapter =
            ScreenAdapter(
                { screenId -> homePresenter.loadTheaters(screenId) },
                { adId -> Toast.makeText(activity, "광고 클릭 id: $adId", Toast.LENGTH_SHORT).show() },
            )
        binding.rvScreens.adapter = screenAdapter
    }

    override fun showScreens(screens: List<ScreenAd>) {
        screenAdapter.submitList(screens)
    }

    override fun showTheatersScreeningMovie(screen: Screen) {
        val theaterScreeningMovieBottomSheet = TheaterScreeningMovieBottomSheet.newInstance(screen.id)
        theaterScreeningMovieBottomSheet.show(childFragmentManager, "TheaterBottomSheet")
    }

    override fun showTheatersFail(throwable: Throwable) {
        Toast.makeText(requireContext(), "상영관 정보를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
