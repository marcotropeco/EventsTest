package br.com.marcoaurelio.projecttest.ui
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.marcoaurelio.projecttest.R
import br.com.marcoaurelio.projecttest.ui.EventItemActivity.Companion.EVENT_ID
import br.com.marcoaurelio.projecttest.util.Converter
import br.com.marcoaurelio.projecttest.util.TargetCallback
import br.com.marcoaurelio.projetoTeste.model.Events
import com.squareup.picasso.Picasso

class EventsAdapter(
    var events: List<Events>
) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, view: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val events : Events = events[position]
        holder.bind(events)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EventItemActivity::class.java)

            intent.putExtra(EVENT_ID, events.id)
            it.context.startActivity(intent)
        }
    }

    fun updateList(newList: List<Events>) {
        this.events = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title = view.findViewById<TextView>(R.id.tv_title)
        val description = view.findViewById<TextView>(R.id.tv_description)
        val image = view.findViewById<ImageView>(R.id.iv_post_image)
        val data = view.findViewById<TextView>(R.id.tv_date)
        var pbItemEvent = view.findViewById<ProgressBar>(R.id.pb_item)

        fun bind(event: Events) {
            title.text = event.title
            description.text = event.description
            data.text = Converter.getDateTime(event.date)
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
        }

    }
}