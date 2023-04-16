package com.example.booksearch.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.booksearch.R
import com.example.booksearch.databinding.FragmentSettingsBinding
import com.example.booksearch.ui.viewmodel.SettingsViewModel
import com.example.booksearch.util.Sort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    //    private lateinit var bookSearchViewModel: BookSearchViewModel
//    private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        saveSettings()
        loadSettings()
        showWorkStatus()
    }

    private fun saveSettings() {
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value = when (checkedId) {
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }
            settingsViewModel.saveSortMode(value)
        }

        binding.swCacheDelete.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.savCacheDeleteMode(isChecked)
            if (isChecked) {
                settingsViewModel.setWork()
            } else {
                settingsViewModel.deleteWork()
            }
        }
    }

    private fun loadSettings() {
        lifecycleScope.launch {
            val buttonId = when (settingsViewModel.getSortMode()) {
                Sort.ACCURACY.value -> R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }

        //WorkManager
        lifecycleScope.launch {
            val mode = settingsViewModel.getCacheDeleteMode()
            binding.swCacheDelete.isChecked = mode
        }
    }

    private fun showWorkStatus() {
        settingsViewModel.getWorkStatus().observe(viewLifecycleOwner) { workInfo ->
            Log.d("WorkManager", workInfo.toString())
            if (workInfo.isEmpty()) {
                binding.tvWorkStatus.text = "No works"
            } else {
                binding.tvWorkStatus.text = workInfo[0].state.toString()
            }
        }
    }

    //필요없을 경우 null처리
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}