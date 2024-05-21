package woowacourse.movie.ui.reservationhistory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import woowacourse.movie.data.ReservationTicketDatabase
import woowacourse.movie.data.model.ReservationTicket
import woowacourse.movie.data.repository.OfflineReservationRepository
import woowacourse.movie.databinding.FragmentReservationHistoryBinding
import woowacourse.movie.ui.reservation.ReservationCompleteActivity
import woowacourse.movie.ui.reservationhistory.adapter.ReservationHistoryAdapter

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View {
    private var _binding: FragmentReservationHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ReservationHistoryContract.Presenter
    private lateinit var adapter: ReservationHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        initPresenter()
        initAdapter()
        presenter.loadAllReservationHistory()
    }

    private fun initPresenter() {
        presenter =
            ReservationHistoryPresenter(
                this,
                OfflineReservationRepository(
                    ReservationTicketDatabase.getDatabase(requireContext().applicationContext).reservationDao(),
                ),
            )
    }

    private fun initAdapter() {
        adapter =
            ReservationHistoryAdapter { reservationId ->
                Log.d(TAG, "initAdapter: itemClicked $reservationId")
                showReservationHistoryInDetail(reservationId)
            }
        binding.rvReservationHistory.adapter = adapter
    }

    override fun showAllReservationHistory(reservations: List<ReservationTicket>) {
        adapter.submitList(reservations)
        Log.d(TAG, "showAllReservationHistory: $reservations")
    }

    override fun showAllReservationHistoryError(throwable: Throwable) {
        Toast.makeText(requireContext(), "예매 내역을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun showReservationHistoryInDetail(reservationTicketId: Int) {
        ReservationCompleteActivity.startActivity(
            requireContext(),
            reservationTicketId = reservationTicketId,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "ReservationHistoryFragment"
    }
}
