package com.acilgin.otpview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.ArrayList


open class OtpView : ConstraintLayout, TextWatcher, OnFocusChangeListener, View.OnKeyListener,
    View.OnClickListener {
    constructor(context: Context)
            : super(context) {
        initOtpView(context, null, 0);
    }

    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs, 0) {
        initOtpView(context, null, 0); }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        initOtpView(context, null, 0); }


    lateinit var edtCode1: EditText
    lateinit var edtCode2: EditText
    lateinit var edtCode3: EditText
    lateinit var edtCode4: EditText
    lateinit var edtCode5: EditText
    lateinit var edtCode6: EditText

    private var index = 1
    fun initOtpView(context: Context, attrs: AttributeSet?, defStyleRes: Int) {
        inflate(context, R.layout.otp_view, this)
        edtCode1 = findViewById(R.id.edtCode1)
        edtCode2 = findViewById(R.id.edtCode2)
        edtCode3 = findViewById(R.id.edtCode3)
        edtCode4 = findViewById(R.id.edtCode4)
        edtCode5 = findViewById(R.id.edtCode5)
        edtCode6 = findViewById(R.id.edtCode6)

        var editTexts = ArrayList<EditText>()
        editTexts.add(edtCode1)
        editTexts.add(edtCode2)
        editTexts.add(edtCode3)
        editTexts.add(edtCode4)
        editTexts.add(edtCode5)
        editTexts.add(edtCode6)

        setAllListener(editTexts)
    }

    private fun setAllListener(editTexts: List<EditText>) {
        for (editText in editTexts) {
            editText.addTextChangedListener(this)
            editText.setOnFocusChangeListener(this)
            editText.setOnKeyListener(this)
            editText.setOnClickListener(this)
        }
    }

    private fun edtFocus(edt: EditText, index: Int) {
        edt.post {
            edt.requestFocusFromTouch()
            val lManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            lManager.showSoftInput(edt, 0)
        }
        this.index = index
    }

    private fun verifacitionCodeWahtcher(charHasCode: Int) {
        if (edtCode1!!.text.hashCode() == charHasCode) {
            if (!getCode1().isEmpty()) {
                edtFocus(edtCode2!!, 2)
            }
        } else if (edtCode2!!.text.hashCode() == charHasCode) {
            if (!getCode2().isEmpty()) {
                edtFocus(edtCode3!!, 3)
            } else {
            }
        } else if (edtCode3!!.text.hashCode() == charHasCode) {
            if (getCode3().isEmpty()) {
                edtFocus(edtCode4!!, 4)
            } else {
            }
        } else if (edtCode4!!.text.hashCode() == charHasCode) {
            if (getCode4().isEmpty()) {
                edtFocus(edtCode5!!, 5)
            } else {
            }
        } else if (edtCode5!!.text.hashCode() == charHasCode) {
            if (getCode5().isEmpty()) {
                edtFocus(edtCode6!!, 6)
            } else {
            }
        } else if (edtCode6!!.text.hashCode() == charHasCode) {
            if (getCode6().isEmpty()) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edtCode6!!.windowToken, 0)
            } else {
            }
        }
        if (!getCode1().isEmpty() && !getCode2().isEmpty() && !
            getCode3().isEmpty()
            && getCode4().isEmpty() && !getCode5().isEmpty() && !
            getCode6().isEmpty()
        ) {
            if (listener != null) listener!!.onAllFieldFill()
        } else {
            if (listener != null) listener!!.onNotFieldFill()
        }
    }

    private fun deleteCode(v: View) {
        when (v.id) {
            R.id.edtCode1 -> {}
            R.id.edtCode2 -> {
                edtFocus(edtCode1!!, 1)
                edtCode1!!.setText("")
            }
            R.id.edtCode3 -> {
                edtFocus(edtCode2!!, 2)
                edtCode2!!.setText("")
            }
            R.id.edtCode4 -> {
                edtFocus(edtCode3!!, 3)
                edtCode3!!.setText("")
            }
            R.id.edtCode5 -> {
                edtFocus(edtCode4!!, 4)
                edtCode4!!.setText("")
            }
            R.id.edtCode6 -> {
                edtFocus(edtCode5!!, 5)
                edtCode5!!.setText("")
            }
        }
    }

    open fun getCode(): String? {
        return getCode1() + getCode2() + getCode3() + getCode4() + getCode5() + getCode6()
    }

    open fun setCode1(c: String?) {
        edtCode1!!.setText(c)
    }

    open fun setCode2(c: String?) {
        edtCode2!!.setText(c)
    }

    open fun setCode3(c: String?) {
        edtCode3!!.setText(c)
    }

    open fun setCode4(c: String?) {
        edtCode4!!.setText(c)
    }

    open fun setCode5(c: String?) {
        edtCode5!!.setText(c)
    }

    open fun setCode6(c: String?) {
        edtCode6!!.setText(c)
    }

    open fun getCode1(): String {
        return edtCode1!!.text.toString()
    }

    open fun getCode2(): String {
        return edtCode2!!.text.toString()
    }

    open fun getCode3(): String {
        return edtCode3!!.text.toString()
    }

    open fun getCode4(): String {
        return edtCode4!!.text.toString()
    }

    open fun getCode5(): String {
        return edtCode5!!.text.toString()
    }

    open fun getCode6(): String {
        return edtCode6!!.text.toString()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        verifacitionCodeWahtcher(s.hashCode())
    }

    override fun afterTextChanged(s: Editable?) {}

    private var listener: OnVerifyCodesViewListener? = null

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        when (v.id) {
            R.id.edtCode1 -> if (hasFocus) {
                edtCode1!!.hint = ""
            } else {
                edtCode1!!.hint = "•"
            }
            R.id.edtCode2 -> if (hasFocus) {
                edtCode2!!.hint = ""
            } else {
                edtCode2!!.hint = "•"
            }
            R.id.edtCode3 -> if (hasFocus) {
                edtCode3!!.hint = ""
            } else {
                edtCode3!!.hint = "•"
            }
            R.id.edtCode4 -> if (hasFocus) {
                edtCode4!!.hint = ""
            } else {
                edtCode4!!.hint = "•"
            }
            R.id.edtCode5 -> if (hasFocus) {
                edtCode5!!.hint = ""
            } else {
                edtCode5!!.hint = "•"
            }
            R.id.edtCode6 -> if (hasFocus) {
            } else {
                edtCode6!!.hint = "•"
            }
        }
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DEL -> deleteCode(v)
        }
        return false
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.edtCode1 -> edtCode1!!.setText("")
            R.id.edtCode2 -> edtCode2!!.setText("")
            R.id.edtCode3 -> edtCode3!!.setText("")
            R.id.edtCode4 -> edtCode4!!.setText("")
            R.id.edtCode5 -> edtCode5!!.setText("")
            R.id.edtCode6 -> edtCode6!!.setText("")
        }
    }

    interface OnVerifyCodesViewListener {
        fun onAllFieldFill()
        fun onNotFieldFill()
    }

    open fun setOnVerifyCodesViewListener(listener: OnVerifyCodesViewListener?) {
        this.listener = listener
    }
}