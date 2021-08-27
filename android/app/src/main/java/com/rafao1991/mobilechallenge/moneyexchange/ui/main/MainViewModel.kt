package com.rafao1991.mobilechallenge.moneyexchange.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafao1991.mobilechallenge.moneyexchange.data.CurrencyApi
import com.rafao1991.mobilechallenge.moneyexchange.data.CurrencyList
import com.rafao1991.mobilechallenge.moneyexchange.data.CurrencyLiveQuotes
import com.rafao1991.mobilechallenge.moneyexchange.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await

class MainViewModel : ViewModel() {

    companion object {

    }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _currencyList = MutableLiveData<CurrencyList>()
    val currencyList: LiveData<CurrencyList>
        get() = _currencyList

    private val _currencyLiveQuotes = MutableLiveData<CurrencyLiveQuotes>()
    val currencyLiveQuotes: LiveData<CurrencyLiveQuotes>
        get() = _currencyLiveQuotes

    private val _originCurrency = MutableLiveData<String>()
    val originCurrency: LiveData<String>
        get() = _originCurrency

    private val _targetCurrency = MutableLiveData<String>()
    val targetCurrency: LiveData<String>
        get() = _targetCurrency

    private val _result = MutableLiveData<Double>()
    val result: LiveData<Double>
        get() = _result

    init {
        getDataFromApis()
        _originCurrency.value = USD_TEXT
        _targetCurrency.value = BRL_TEXT
    }

    private fun getDataFromApis() {
        coroutineScope.launch {
            try {
                _currencyList.value = CurrencyApi.service.getList().await()
                _currencyLiveQuotes.value = CurrencyApi.service.getLiveQuotes().await()
            } catch (e: Exception) {
                Log.e(javaClass.name, e.message, e)
            }
        }
    }

    fun setCurrency(currencyType: Currency, item: String) {
        when(currencyType) {
            Currency.ORIGIN -> _originCurrency.value = _currencyList.value?.currencies?.get(item)
            Currency.TARGET -> _targetCurrency.value = _currencyList.value?.currencies?.get(item)
        }
    }

    fun getOriginCurrencyKey(): String {
        _currencyList.value?.currencies?.forEach {
            if (it.value == _originCurrency.value) {
                return it.key
            }
        }

        return USD
    }

    fun getTargetCurrencyKey(): String {
        _currencyList.value?.currencies?.forEach {
            if (it.value == _targetCurrency.value) {
                return it.key
            }
        }

        return BRL
    }

    fun handleExchange(amount: String?) {
        if (amount.isNullOrBlank()) {
            _result.value = 0.0
        } else {
            _result.value = Exchange(
                amount.toString().toDouble(),
                getOriginCurrencyKey(),
                getTargetCurrencyKey(),
                _currencyLiveQuotes.value?.quotes!!).getExchanged()
        }
    }
}