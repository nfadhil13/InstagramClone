package com.fdev.instagramclone.util.cutomview

import android.widget.TextView
import com.fdev.instagramclone.framework.presentation.changeTextcolor
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.fdev.instagramclone.R
import java.util.regex.Pattern


class PasswordEditText
@JvmOverloads
constructor(
        context: Context,
        attributeSet: AttributeSet? = null
) : AppCompatEditText(context, attributeSet) {



    var textView : TextView? = null


    private var _isPasswordValid = false


    //At least contain one special character
    private var specialCharacterPattern = Pattern
            .compile("^(?=.*[@#\$%^&+=!]).{1,}\$")


    //At least contain one number
    private var numberCharacterPattern = Pattern
            .compile("^(?=.*[0-9]).{1,}\$")


    //At least contain one upper case and one lower case letter
    private var letterPattern = Pattern
            .compile("^(?=.*[a-z])(?=.*[A-Z]).{1,}\$")

    init {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                _isPasswordValid = false
                val passwordToValidate = s.toString()
                //Check if password is empty
                if(!TextUtils.isEmpty(passwordToValidate)){

                    //Check if password has pass minum and maximum length
                    if(passwordToValidate.length >= 8 && passwordToValidate.length<=20 ){

                        //Check if password atleast has one special character
                        if(specialCharacterPattern.matcher(passwordToValidate).matches()){

                            //check if password  At least has one number
                            if(numberCharacterPattern.matcher(passwordToValidate).matches()){

                                //At least contain one upper case and one lower case letter
                                if(letterPattern.matcher(passwordToValidate).matches()){
                                    succes()
                                    _isPasswordValid = true
                                }else{
                                    error(R.string.password_letter_required)
                                }
                            }else{
                                error(R.string.password_require_number)
                            }

                        }else{
                           error(R.string.password_require_special_character)
                        }
                    }else{
                        error(R.string.passwor_minimum_warn)
                    }
                }else{
                    error(R.string.password_empty_warn)
                }
            }

        })
    }

    private fun error(@StringRes msg : Int){
        changeToWarnEditText(msg)
        changeToWarnBackground()
    }

    private fun succes(){
        changeToValidEditText()
        changeTovalidBackground()
    }

    private fun changeToWarnEditText(@StringRes msg : Int){
        textView?.let{
            it.setText(msg)
            it.changeTextcolor(R.color.warnColor)
        }
    }

    private fun changeToValidEditText(){
        textView?.let{
            it.setText("")
            it.changeTextcolor(R.color.blackLine)
        }
    }

    private fun changeTovalidBackground(){
        background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.edit_text_box,
                null
        )
    }

    private fun changeToWarnBackground(){
        background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.warn_text_box,
                null
        )
    }

    fun isPasswordValid() = _isPasswordValid

}

