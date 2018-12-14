package whiterabbit.com.codebase.sample


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.row_home.view.*
import whiterabbit.com.codebase.sample.image.gallery.ImageGalleryFragment
import whiterabbit.com.codebase.sample.progress.ProgressLayoutFragment
import whiterabbit.core.ui.fragment.WrgFragment
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : WrgFragment(), HomeAdapter.Listener {

    private val fragments =
            hashMapOf("Image Gallery" to ImageGalleryFragment::class.java,
                    "Progress Layout" to ProgressLayoutFragment::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun initUi() {

    }

    override fun registerUiEvents() {

    }


    override fun setAdapter() {
        val homeAdapter =  HomeAdapter(fragments, this)
        recyclerView.adapter = homeAdapter
    }

    override fun onClickFragment(fragment: Class<out WrgFragment>) {
        val intent = Intent()
        intent.putExtra("", fragment)
        switchFragment(fragment.newInstance())
    }


}


class HomeAdapter(private val fragments: HashMap<String, Class<out WrgFragment>>
                  , val listener: Listener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    interface Listener{
        fun onClickFragment(fragment:Class<out WrgFragment>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_home
                , parent, false))
    }

    override fun getItemCount(): Int {
        return fragments.count()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind()
    }

    inner class HomeViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener {
                listener.onClickFragment(fragments.entries.elementAt(adapterPosition).value)
            }
        }

        fun bind() {
            itemView.textView.text = fragments.keys.elementAt(adapterPosition)
        }

    }
}
