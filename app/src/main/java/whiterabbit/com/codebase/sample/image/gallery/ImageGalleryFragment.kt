package whiterabbit.com.codebase.sample.image.gallery


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.whiterabbit.base.module.image.gallery.ImageGalleryActivity
import whiterabbit.com.codebase.sample.R
import whiterabbit.core.ui.fragment.WrgFragment

/**
 * A simple [Fragment] subclass.
 *
 */
class ImageGalleryFragment : WrgFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_gallery, container, false)
    }

    override fun initUi() {
        val images =
                arrayListOf("https://www.androidpolice.com/wp-content/themes/ap2/ap_resize/ap_resize.php?src=https%3A%2F%2Fwww.androidpolice.com%2Fwp-content%2Fuploads%2F2016%2F07%2Fnexus2cee_2016-07-25-11_16_36-OnePlus-3-Soft-Gold-YouTube-728x402.png&w=728"
                        ,"https://www.androidpolice.com/wp-content/themes/ap2/ap_resize/ap_resize.php?src=https%3A%2F%2Fwww.androidpolice.com%2Fwp-content%2Fuploads%2F2018%2F03%2Fnexus2cee_Dutch-Galaxy-S9-HERO-HERO-IS-BACK-BABY-728x409.png&w=728"
                        ,"https://www.androidpolice.com/wp-content/themes/ap2/ap_resize/ap_resize.php?src=https%3A%2F%2Fwww.androidpolice.com%2Fwp-content%2Fuploads%2F2017%2F11%2Fnexus2cee_OnePlus5T-FrontLaying-e1527820099563-728x389.png&w=728")
        startActivity(ImageGalleryActivity.intent(activity!!, images,0))
    }

    override fun registerUiEvents() {

    }

}
