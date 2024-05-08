package woowacourse.movie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.databinding.BottomSheetTheaterBinding
import woowacourse.movie.domain.model.TheaterScreeningCount
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.domain.repository.DummyTheaters
import woowacourse.movie.ui.detail.ScreenDetailActivity
import woowacourse.movie.ui.home.adapter.TheaterScreeningCountAdapter

class TheaterScreeningMovieBottomSheet : BottomSheetDialogFragment(), TheatersScreeningMovieContract.View {
    private var _binding: BottomSheetTheaterBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: TheatersScreeningMoviePresenter

    private lateinit var theaterScreeningCountAdapter: TheaterScreeningCountAdapter

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
        initTheaterAdapter()
        presenter.loadTheaters()
    }

    private fun initTheaterAdapter() {
        theaterScreeningCountAdapter =
            TheaterScreeningCountAdapter { theaterId ->
                presenter.selectTheater(theaterId)
            }
        binding.rvTheater.adapter = theaterScreeningCountAdapter
    }

    override fun showTheatersScreeningcount(theaterScreeningCount: List<TheaterScreeningCount>) {
        theaterScreeningCountAdapter.submitList(theaterScreeningCount)
    }

    override fun showScreenDetail(
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

        fun newInstance(screenId: Int): TheaterScreeningMovieBottomSheet {
            val fragment = TheaterScreeningMovieBottomSheet()
            val args = Bundle()
            args.putInt(ARGUMENT_SCREEN_ID, screenId)
            fragment.arguments = args

            return fragment
        }
    }
}
