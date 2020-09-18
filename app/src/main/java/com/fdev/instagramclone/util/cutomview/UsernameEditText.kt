package com.fdev.instagramclone.util.cutomview

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.fdev.instagramclone.R
import com.fdev.instagramclone.framework.presentation.changeTextcolor
import java.util.regex.Pattern

class UsernameEditText
@JvmOverloads
constructor(
        context: Context,
        attributeSet: AttributeSet? = null
) : AppCompatEditText(context, attributeSet) {


    var warnTextView: TextView? = null


    private var _isUsernameValid = false


    //At least contain one special character
    private var usernamePattern = Pattern.compile("^[a-z]{5,18}\$")


    init {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                _isUsernameValid = false
                val usernameToValidate = s.toString()
                //Check username is empty
                if (!TextUtils.isEmpty(usernameToValidate)) {

                    //check if username contain space
                    if (!usernameToValidate.contains(" ")) {
                        //Check if username has at leat 5 length
                        if (usernameToValidate.length >= 5) {

                            //check if username has more than max lengt(10)
                            if (usernameToValidate.length <= 18) {


                                //check if same pattern
                                if (usernamePattern.matcher(usernameToValidate).matches()) {
                                    succes()
                                    _isUsernameValid = true
                                } else {
                                    error(R.string.username_only_lowercase)
                                }
                            } else {
                                error(R.string.username_maximum_length)
                            }
                        } else {
                            error(R.string.username_minimum_length)
                        }
                    } else {
                        error(R.string.username_no_blank)
                    }

                } else {
                    error(R.string.empty_field_warn)
                }
            }

        })
    }

    private fun error(@StringRes msg: Int) {
        changeToWarnEditText(msg)
        changeToWarnBackground()
    }

    private fun succes() {
        changeToValidEditText()
        changeTovalidBackground()
    }

    private fun changeToWarnEditText(@StringRes msg: Int) {
        warnTextView?.let {
            it.setText(msg)
            it.changeTextcolor(R.color.warnColor)
        }
    }

    private fun changeToValidEditText() {
        warnTextView?.let {
            it.setText("")
            it.changeTextcolor(R.color.blackLine)
        }
    }

    private fun changeTovalidBackground() {
        background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.edit_text_box,
                null
        )
    }

    private fun changeToWarnBackground() {
        background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.warn_text_box,
                null
        )
    }

    fun isValid() = _isUsernameValid
}
