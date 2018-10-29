package com.arctouch.codechallenge.view

import android.content.Context
import android.support.v7.app.AlertDialog
import com.arctouch.codechallenge.R


object ProgressDialog {
    private lateinit var progressDialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder
    fun show(context: Context) {
        if(!::builder.isInitialized) {
            builder = AlertDialog.Builder(context)
            builder.setView(R.layout.progress)
            builder.setCancelable(false)
            progressDialog = builder.create()
        }
        progressDialog.show()
    }

    fun hide() {
        progressDialog.dismiss()
    }
}