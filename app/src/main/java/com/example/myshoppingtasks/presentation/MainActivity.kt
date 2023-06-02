package com.example.myshoppingtasks.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.myshoppingtasks.R
import com.example.myshoppingtasks.ui.theme.MyShoppingTasksTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MaiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MaiViewModel::class.java)

        viewModel.shopList.observe(this) {
            Log.d("MainActivityTest", it.toString())
        }

    }
}

