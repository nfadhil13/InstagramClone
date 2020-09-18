package com.fdev.instagramclone.util

import android.widget.TextView
import com.fdev.instagramclone.framework.presentation.changeTextcolor
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.fdev.instagramclone.R


class PasswordEditText
@JvmOverloads
constructor(
        context: Context,
        attributeSet: AttributeSet? = null
) : AppCompatEditText(context, attributeSet) {



    var textView : TextView? = null

    var passMinimunLength = 8

    private var _isPasswordValid = false

    init {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                _isPasswordValid = false
                val passwordToValidate = s.toString()
                if(!TextUtils.isEmpty(passwordToValidate)){
                    if(passwordToValidate.length > passMinimunLength){
                        textView?.let{
                            it.setText("")
                            it.changeTextcolor(R.color.blackLine)
                        }
                        changeTovalidBackground()
                        _isPasswordValid = true
                    }else{
                        textView?.let{
                            it.setText(R.string.passwor_minimum_warn)
                            it.changeTextcolor(R.color.warnColor)
                        }
                        changeToWarnBackground()
                    }
                }else{
                    textView?.let{
                        it.setText(R.string.password_empty_warn)
                        it.changeTextcolor(R.color.warnColor)
                    }
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

    fun isPasswordValid() = _isPasswordValid

}

