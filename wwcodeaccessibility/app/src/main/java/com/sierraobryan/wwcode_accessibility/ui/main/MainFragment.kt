package com.sierraobryan.wwcode_accessibility.ui.main

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.TextKeyListener.clear
import android.text.style.TtsSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sierraobryan.wwcode_accessibility.CommitItemBinding
import com.sierraobryan.wwcode_accessibility.R
import com.sierraobryan.wwcode_accessibility.data.models.Commit
import com.sierraobryan.wwcode_accessibility.databinding.MainFragmentBinding
import com.sierraobryan.wwcode_accessibility.utils.ArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        binding.commits.layoutManager = LinearLayoutManager(activity)
        binding.commits.adapter = CommitsAdapter()
        val title = "5 meter"
        val spannable = SpannableString(title)
        spannable.setSpan(TtsSpan.MeasureBuilder().setNumber(5).setUnit("meter"), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tester.text = spannable
        return binding.root
    }

    private class CommitsAdapter : ArrayAdapter<Commit, CommitViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: CommitItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_item_commit, parent, false)
            return CommitViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
            val commit = getItemAtPosition(position)
            holder.bind(commit)
        }
    }

    private class CommitViewHolder(
        private val binding: CommitItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Commit) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

}