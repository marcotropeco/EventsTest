package br.com.marcoaurelio.projecttest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import br.com.marcoaurelio.projecttest.R


class DialogFragment(val param: EventItemFragment.DialogOkClickListener) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_dialog, container, false)
        setupInitButtons(view)
        return view
    }

    private fun setupInitButtons(view : View) {
        val buttonConfirm = view.findViewById<Button>(R.id.bt_complete)
        val buttonCancel = view.findViewById<Button>(R.id.btn_close)
        buttonConfirm.setOnClickListener {
            val tv_name_checkin = view.findViewById<EditText>(R.id.tv_name_checkin)
            val tv_email_checkin = view.findViewById<EditText>(R.id.tv_email_checkin)
            val name: String = tv_name_checkin.text.toString()
            val email: String = tv_email_checkin.text.toString()
            param.onDialogOkClick(name, email)
        }
        buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        dialog!!.window?.setLayout(width, height)
    }


}