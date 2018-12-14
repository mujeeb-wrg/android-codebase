package whiterabbit.com.codebase.sample

import android.os.Bundle
import whiterabbit.core.ui.activity.WrgActivity

class MainActivity : WrgActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setData() {

    }

    override fun initUi() {
        navigateTo(MainFragment())
    }

    override fun registerUiEvents() {

    }

}

