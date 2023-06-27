package com.example.myshoppingtasks.presentation

import androidx.databinding.BindingAdapter
import com.example.myshoppingtasks.R
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter("errorInputName")
fun bindErrorInputName(til: TextInputLayout, isError: Boolean) {
    til.error = if (isError) {
        til.resources.getString(R.string.error_input_name)
    } else {
        null
    }
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(til: TextInputLayout, isError: Boolean) {
    til.error = if (isError) {
        til.resources.getString(R.string.error_input_count)
    } else {
        null
    }
}
