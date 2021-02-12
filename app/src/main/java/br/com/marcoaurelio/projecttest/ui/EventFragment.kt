package br.com.marcoaurelio.projecttest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.marcoaurelio.projecttest.R
import br.com.marcoaurelio.projecttest.util.Message
import br.com.marcoaurelio.projetoTeste.model.Events
import br.com.marcoaurelio.projetoTeste.viewmodel.EventViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : Fragment(R.layout.event_fragment) {

    private val viewModel: EventViewModel by viewModel()
    private var recyclerView: RecyclerView? = null
    var eventList = listOf<Events>()
    val mAdapter by lazy { EventsAdapter(eventList) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view : View = inflater.inflate(R.layout.event_fragment, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.rvEvents)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupErrorHttp()
        setupRecycler()
        setUpEventList()
    }

    private fun setupRecycler() {
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView?.context,
                (recyclerView?.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView?.adapter = mAdapter
    }

    private fun replaceFragment(){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentEvents, HTTPFragment())
        transaction?.disallowAddToBackStack()
        transaction?.commit()
    }

    private fun setupErrorHttp(){
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    replaceFragment()
                    Message.showMessage(context, getString(R.string.errorHttp))
                }
            }
        });
    }

    private fun setUpEventList() {
        viewModel.events.observe(viewLifecycleOwner, Observer {
            it?.let {
                with(recyclerView) {
                    this?.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    this?.setHasFixedSize(true)
                    setEvent(it)
                }
            }
        })
        viewModel.getEvents()
    }

    private fun setEvent(event: List<Events>) {
        mAdapter.updateList(event)
    }
}