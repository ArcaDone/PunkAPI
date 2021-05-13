package com.arcadone.cheers.ui.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arcadone.cheers.CheersApp
import com.arcadone.cheers.R
import com.arcadone.cheers.adapter.BeerAdapter
import com.arcadone.cheers.databinding.FragmentMainBinding
import com.arcadone.cheers.model.BeerItem
import com.arcadone.cheers.model.Result
import com.arcadone.cheers.util.setGone
import com.arcadone.cheers.util.setVisible
import com.arcadone.picker.util.PickerUtils
import com.arcadone.picker.view.MonthYearPickerDialog
import com.arcadone.picker.listener.PickerListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

@InternalCoroutinesApi
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels {
        BeerViewModelFactory((requireActivity().application as CheersApp).repository)
    }
    private lateinit var binding: FragmentMainBinding
    private lateinit var beerAdapter: BeerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater).apply {
            lifecycleOwner = this@MainFragment
            viewModel = mainViewModel

            beerAdapter = BeerAdapter(BeerAdapter.OnClickListener {
                mainViewModel.displayPropertyDetails(it)
            })

            itemList.adapter = beerAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.uiState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    onSuccess(result)
                }
                is Result.Loading -> {
                    onLoading()
                }
                is Result.Error -> {
                    onError()
                }
            }
        }

        binding.pickAfterDateButton.setOnClickListener {
            MonthYearPickerDialog.show(
                context = requireActivity(),
                calendar = mainViewModel.calendarInstance,
                listener = object : PickerListener {
                    override fun onSetResult(calendar: Calendar) {
                        mainViewModel.calendarInstance = calendar
                        binding.dateAfter = PickerUtils.getMonthYearDisplay(
                            requireActivity(),
                            calendar,
                            PickerUtils.Format.SHORT
                        )

                        val month = PickerUtils.getMonth(calendar, PickerUtils.Format.SHORT)
                        val year = PickerUtils.getYear(calendar)

                        mainViewModel.brewedAfter.value =
                            PickerUtils.getMonthYearString(month.toString(), year.toString())
                    }
                })
        }

        binding.pickBeforeDateButton.setOnClickListener {
            MonthYearPickerDialog.show(
                context = requireActivity(),
                calendar = mainViewModel.calendarInstance,
                listener = object : PickerListener {
                    override fun onSetResult(calendar: Calendar) {
                        mainViewModel.calendarInstance = calendar
                        binding.dateBefore = PickerUtils.getMonthYearDisplay(
                            requireActivity(),
                            calendar,
                            PickerUtils.Format.SHORT
                        )

                        val month = PickerUtils.getMonth(calendar, PickerUtils.Format.SHORT)
                        val year = PickerUtils.getYear(calendar)

                        mainViewModel.brewedBefore.value =
                            PickerUtils.getMonthYearString(month.toString(), year.toString())
                    }
                })
        }

        binding.itemList.apply {
            this.adapter = beerAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == beerAdapter.itemCount - 1) {
                        mainViewModel.page += 1
                        mainViewModel.updateUiState()
                    }
                }
            })
        }

        binding.cancelAfterDate.setOnClickListener {
            binding.dateAfter = ""
            mainViewModel.brewedAfter.value = ""
            mainViewModel.updateUiState()
        }

        binding.cancelBeforeDate.setOnClickListener {
            binding.dateBefore = ""
            mainViewModel.brewedBefore.value = ""
            mainViewModel.updateUiState()
        }

        mainViewModel.navigateToDetail.observe(viewLifecycleOwner) {
            if (it != null && this@MainFragment.findNavController().currentDestination?.id == R.id.mainFragment) {
                val card = MainFragmentDirections.actionMainFragmentToDetailFragment(it)
                this@MainFragment.findNavController().navigate(card)
            }
        }


    }

    private fun onError() = with(binding) {
        retryButton.setVisible()
        progressBar.setGone()
        itemList.setGone()
        Snackbar.make(root, getString(R.string.error_text), Snackbar.LENGTH_LONG).show()
    }

    private fun onLoading() = with(binding) {
        retryButton.setGone()
        itemList.setGone()
        progressBar.setVisible()
    }

    private fun onSuccess(result: Result<List<BeerItem>>) = with(binding) {
        retryButton.setGone()
        progressBar.setGone()
        itemList.setVisible()
        result.data?.let {
            beerAdapter.submitList(it)
            if (it.isEmpty()) {
                emptyImage.setVisible()
                emptyList.setVisible()
            } else {
                emptyImage.setGone()
                emptyList.setGone()
            }
        }
    }
}
