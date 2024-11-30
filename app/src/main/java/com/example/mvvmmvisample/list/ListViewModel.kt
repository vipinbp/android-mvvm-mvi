package com.example.mvvmmvisample.list

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.MyUsecase
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ListViewModel(val myUsecase: MyUsecase): ViewModel() {

}