package com.example.sudowku.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sudowku.game.SudokuGame

class MainActivityViewModel : ViewModel() {
    val sudokuGame = SudokuGame()
}