package com.example.mvvmmvisample.details

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.MyUsecase
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class DetailsViewModel(val myUsecase: MyUsecase): ViewModel() {

}