package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.BottomSheetTheaterBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.domain.repository.DummyTheaters
import woowacourse.movie.ui.detail.ScreenDetailActivity
import woowacourse.movie.ui.home.adapter.TheaterAdapter

class TheaterBottomSheet : BottomSheetDialogFragment(), TheatersScreeningMovieContract.View {
    private var _binding: BottomSheetTheaterBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: TheatersScreeningMoviePresenter
    private lateinit var theaterAdapter: TheaterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenId = arguments?.getInt(ARGUMENT_SCREEN_ID) ?: throw IllegalArgumentException("ScreenId is required")
        presenter = TheatersScreeningMoviePresenter(this, DummyScreens(), DummyTheaters(), screenId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetTheaterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initTheaterAdapter()
        presenter.loadTheaters()
    }

    override fun initTheaterAdapter(screen: Screen) {
        theaterAdapter =
            TheaterAdapter(screen) { theaterId ->
                presenter.onTheaterSelected(theaterId)
            }
        binding.rvTheater.adapter = theaterAdapter
    }

    override fun showTheaters(
        screen: Screen,
        theaters: Theaters,
    ) {
        theaterAdapter.submitList(theaters.theaters)
    }

    override fun navigateToScreenDetail(
        screenId: Int,
        theaterId: Int,
    ) {
        ScreenDetailActivity.startActivity(requireContext(), screenId, theaterId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGUMENT_SCREEN_ID = "screenId"

        fun newInstance(screenId: Int): TheaterBottomSheet {
            val fragment = TheaterBottomSheet()
            val args = Bundle()
            args.putInt(ARGUMENT_SCREEN_ID, screenId)
            fragment.arguments = args

            return fragment
        }
    }
}
