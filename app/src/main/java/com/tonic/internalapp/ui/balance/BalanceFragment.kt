package com.tonic.internalapp.ui.balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tonic.internalapp.databinding.FragmentBalanceBinding
import com.tonic.internalapp.databinding.FragmentEditBinding
import com.tonic.internalapp.ui.edit.EditViewModel

class BalanceFragment : Fragment() {
    private lateinit var balanceViewModel : BalanceViewModel
    private var _binding: FragmentBalanceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        balanceViewModel =
            ViewModelProvider(this).get(BalanceViewModel::class.java)

        _binding = FragmentBalanceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBalance
        balanceViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}