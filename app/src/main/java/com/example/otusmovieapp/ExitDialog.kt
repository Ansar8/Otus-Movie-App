package com.example.otusmovieapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ExitDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Warning!")
            .setView(R.layout.dialog_exit)
            .setNegativeButton("Cancel") { dialog, which -> dismiss() }
            .setPositiveButton("Confirm"){ dialog, which -> activity?.finish() }
            .create()
    }
}