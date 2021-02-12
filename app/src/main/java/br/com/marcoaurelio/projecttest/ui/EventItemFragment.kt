package br.com.marcoaurelio.projecttest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.marcoaurelio.projecttest.R
import br.com.marcoaurelio.projecttest.ui.EventItemActivity.Companion.EVENT_ID
import br.com.marcoaurelio.projecttest.util.Converter
import br.com.marcoaurelio.projecttest.util.Message
import br.com.marcoaurelio.projecttest.util.TargetCallback
import br.com.marcoaurelio.projecttest.viewmodel.EventDetailViewModel
import br.com.marcoaurelio.projetoTeste.model.CheckIn
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventItemFragment : Fragment() {

    interface FragmentCallback {
        fun setupToolbar(message: String)
    }

    interface DialogOkClickListener {
        fun onDialogOkClick(name: String, email: String)
    }

    var mCallback: FragmentCallback? = null
    private val viewModel: EventDetailViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mCallback = context as? FragmentCallback
        if (mCallback == null) {
            throw ClassCastException("$context must implement FragmentCallback")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.event_item_fragment, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpItemEventList()
        setupErrorHttp()
    }

    private fun replaceFragment() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentEvents_dtl, HTTPFragment())
        transaction?.disallowAddToBackStack()
        transaction?.commit()
    }
    private fun disableButtonCheckin(){
        val btn_checkin = view?.findViewById<Button>(R.id.bt_checkin_open)
        btn_checkin?.visibility = View.INVISIBLE
    }

    private fun setupErrorHttp() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    mCallback?.setupToolbar(getString(R.string.noConnection))
                    disableButtonCheckin()
                    replaceFragment()
                    Message.showMessage(context, getString(R.string.errorHttp))
                }
            }
        })
    }

    private fun setUpItemEventList() {
        viewModel.eventData.observe(viewLifecycleOwner, Observer {
            it?.let { event ->
                mCallback?.setupToolbar(event.title)
                val lblDate = getString(R.string.lbl_date)
                val lblPrice = getString(R.string.lbl_price)
                val title = view?.findViewById<TextView>(R.id.tv_title_dtl)
                val description = view?.findViewById<TextView>(R.id.tv_description_dtl)
                val image = view?.findViewById<ImageView>(R.id.iv_post_image_dtl)
                val data = view?.findViewById<TextView>(R.id.tv_date_dtl)
                val preco = view?.findViewById<TextView>(R.id.tv_preco_dtl)
                val pbItemEvent = view?.findViewById<ProgressBar>(R.id.pb_item_dtl)
                val btn_checkin = view?.findViewById<Button>(R.id.bt_checkin_open)
                btn_checkin?.visibility = View.VISIBLE

                title?.text = event.title
                description?.text = event.description
                val dataText = "${lblDate}: ${Converter.getDateTime(event.date)}"
                data?.text = dataText
                val dataPrice = "${lblPrice}: ${Converter.currencyFormatter(event.price)}"
                preco?.text = dataPrice
                event.image.let {
                    Picasso.get()
                        .load(event.image)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(image, object : TargetCallback() {
                            override fun onAfterLoad() {
                                pbItemEvent?.visibility = View.GONE
                            }
                        })
                }
                btn_checkin?.setOnClickListener {
                    DialogFragment(object : DialogOkClickListener {
                        override fun onDialogOkClick(name: String, email: String) {
                            //it.hideKeyboard()
                            onClickConfirmCheckIn(name, email)
                        }
                    }).show(activity?.supportFragmentManager!!, "")
                }
            }
        })

        val result: Int = getEventId()
        if (result > 0)
            viewModel.getEvent(result)

        viewModel.resultCheckinLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { success ->
                if (success) showSuccessCheckin() else showErrorCheckIn()
            }
        })
    }

    private fun extractParamsCheckin(name: String, email: String): CheckIn {
        val eventId: Int = getEventId()
        return CheckIn(eventId, name, email)
    }

    private fun onClickConfirmCheckIn(name: String, email: String) {
        val paramsCheckIn = extractParamsCheckin(name, email)
        if (validateCheckin(paramsCheckIn)) {
            viewModel.postCheckIn(paramsCheckIn)
        }
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun validateCheckin(checkIn: CheckIn): Boolean {

        if (checkIn.eventId === null) {
            Message.showMessage(context, getString(R.string.errorEvent))
            return false
        }
        if (checkIn.name === null || checkIn.name.isEmpty()) {
            Message.showMessage(context, getString(R.string.nameErroEvent))
            return false
        }

        if (checkIn.email === null || checkIn.email.isEmpty()) {
            Message.showMessage(context, getString(R.string.mailErroEvent))
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(checkIn.email).matches()) {
            Message.showMessage(context, getString(R.string.invalidMail))
            return false;
        }

        return true;
    }

    private fun showErrorCheckIn() {
        Message.showMessage(context, getString(R.string.checkError));
    }

    private fun showSuccessCheckin() {
        disableButtonCheckin()
        Message.showMessage(context, getString(R.string.checkSuccess));
    }

    private fun getEventId(): Int {
        val events: Int? = activity?.intent?.getStringExtra(EVENT_ID)?.toInt()
        return events ?: 0
    }
}