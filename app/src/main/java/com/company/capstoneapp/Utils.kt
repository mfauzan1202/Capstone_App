package com.company.capstoneapp

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun spannable(view: TextView, text: String, spanStart: Int, spanEnd: Int, context: Context, intent: Intent){
    val ss = SpannableString(text)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            context.startActivity(intent)
            (context as Activity).finish()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = context.resources.getColor(R.color.cream)
            ds.isUnderlineText = false
        }
    }
    ss.setSpan(clickableSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    view.text = ss
    view.movementMethod = LinkMovementMethod.getInstance()
}