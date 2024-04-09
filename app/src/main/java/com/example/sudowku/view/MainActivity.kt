package com.example.sudowku.view

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


import com.example.sudowku.R
import com.example.sudowku.game.Cell
import com.example.sudowku.view.custom.SudokuBoardView
import com.example.sudowku.viewmodel.MainActivityViewModel



import kotlinx.android.synthetic.main.activity_sudoku_juego.*
class MainActivity : ComponentActivity(), SudokuBoardView.OnTouchListener {
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku_juego)

        sudokuBoardView.registerListener(this)


        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })
        viewModel.sudokuGame.cellsLiveData.observe(this, Observer { updateCells(it)})
        val numberButton = listOf(oneButton, twoButton, threeButton, fourButton, sixButton, sevenButton, eightButton, nineButton)
        numberButton.forEachIndexed { index, button ->
            button.setOnClickListener{
                viewModel.sudokuGame.handleInput(index + 1)}
        }
    }
    private fun updateCells(cells: List<Cell>?) = cells?.let{
        sudokuBoardView.updateCells(cells)
    }
    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let{
        sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }
    override fun onCellTouched(row: Int, col: Int){
        viewModel.sudokuGame.updateSelectedCell(row,col)
    }
}

