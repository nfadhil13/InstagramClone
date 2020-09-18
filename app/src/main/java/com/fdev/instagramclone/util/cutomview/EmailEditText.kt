package com.fdev.instagramclone.util.cutomview

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.fdev.instagramclone.R
import com.fdev.instagramclone.util.printLogD


class EmailEditText
@JvmOverloads
constructor(

        context: Context,
        attributeSet: AttributeSet? = null
) : AppCompatEditText(context, attributeSet) {

    var emailEditTextCallback: EmailEditTextCallback? = null



    init {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                printLogD("EmailEditText", "before Text Change")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                printLogD("EmailEditText", "onTextChange")
            }

            override fun afterTextChanged(s: Editable?) {
                if(!TextUtils.isEmpty(text.toString())){
                    if(Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()){
                        emailEditTextCallback?.onvalidInput()
                        changeTovalidBackground()
                    }else{
                        emailEditTextCallback?.onInvalidEmailInput()
                        changeToWarnBackground()
                    }
                }else{
                    emailEditTextCallback?.onEmptyInput()
                    changeToWarnBackground()
                }

            }

        })
    }

    fun changeTovalidBackground(){
        background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.edit_text_box,
                null
        )
    }

    fun changeToWarnBackground(){
        background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.warn_text_box,
                null
        )
    }

    fun validateEmail(): Boolean =
            !TextUtils.isEmpty(text.toString()) && Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()


    fun addOnInvalidCallBack(emailEditTextCallback: EmailEditTextCallback){
        this.emailEditTextCallback = emailEditTextCallback
    }
}

interface EmailEditTextCallback {
    fun onInvalidEmailInput()
    fun onEmptyInput()
    fun onvalidInput()
}