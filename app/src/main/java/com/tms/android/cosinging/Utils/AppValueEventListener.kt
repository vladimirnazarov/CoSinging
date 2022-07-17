package com.tms.android.cosinging.Utils

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.protobuf.Value

class AppValueEventListener (val onSuccess: (DataSnapshot) -> Unit): ValueEventListener {
    override fun onCancelled(error: DatabaseError) {
        Log.e("Crashed AppValueEventListener", "Failed to load data")
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        onSuccess(snapshot)
    }
}