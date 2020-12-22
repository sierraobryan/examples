package com.sierraobryan.movie_db_api.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sierraobryan.movie_db_api.R
import com.sierraobryan.movie_db_api.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        val binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.authorizeButton.setOnClickListener {
            viewModel.getRequestToken()
        }
        binding.createIdButton.setOnClickListener {
            viewModel.rateMovie()
        }

        viewModel.requestToken.observe(viewLifecycleOwner) { requestToken ->
            if (requestToken.isNotBlank()) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(
                        String.format(
                                getString(R.string.movie_db_authorize_url),
                                requestToken,
                                requestToken
                        )
                )
                startActivity(intent)
            }
        }

        viewModel.sessionId.observe(viewLifecycleOwner) { sessionId ->
            if (sessionId.isNotBlank()) {
                binding.sessionId.text = sessionId
                binding.createIdButton.isEnabled = true
            }
        }

        viewModel.rateMovieResponseMessage.observe(viewLifecycleOwner) {
            binding.movieMessage.text = it
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}