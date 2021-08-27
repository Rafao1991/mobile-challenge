package com.rafao1991.mobilechallenge.moneyexchange.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.rafao1991.mobilechallenge.moneyexchange.R
import com.rafao1991.mobilechallenge.moneyexchange.domain.Currency
import java.math.RoundingMode
import java.text.DecimalFormat

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var buttonOriginCurrency: Button
    private lateinit var buttonTargetCurrency: Button
    private lateinit var editTextTextAmount: EditText
    private lateinit var textViewResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        activity?.let {
            loadViews(it)
            loadActions()
        }
    }

    private fun loadViews(fragmentActivity: FragmentActivity) {
        buttonOriginCurrency = fragmentActivity.findViewById(R.id.buttonOriginCurrency)
        buttonTargetCurrency = fragmentActivity.findViewById(R.id.buttonTargetCurrency)
        editTextTextAmount = fragmentActivity.findViewById(R.id.editTextTextAmount)
        textViewResult = fragmentActivity.findViewById(R.id.textViewResult)
    }

    private fun loadActions() {
        buttonOriginCurrency.text = viewModel.originCurrency.value
        buttonOriginCurrency.setOnClickListener {
            it.findNavController().navigate(
                MainFragmentDirections
                    .actionMainFragmentToCurrencyListFragment(Currency.ORIGIN))
        }

        buttonTargetCurrency.text = viewModel.targetCurrency.value
        buttonTargetCurrency.setOnClickListener {
            it.findNavController().navigate(
                MainFragmentDirections
                    .actionMainFragmentToCurrencyListFragment(Currency.TARGET))
        }

        editTextTextAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.handleExchange(s.toString())
            }
        })

        viewModel.result.observe(viewLifecycleOwner, {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            textViewResult.text = df.format(it)
        })
    }
}