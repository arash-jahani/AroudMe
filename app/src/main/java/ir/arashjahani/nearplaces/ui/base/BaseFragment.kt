package ir.arashjahani.nearplaces.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

/**
 * Created By ArashJahani on 2020/04/17
 */
open abstract class BaseFragment<V : BaseViewModel<*>> : Fragment()  {

    lateinit var mRootView: View
    lateinit var mViewModel: V


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidSupportInjection.inject(this);

        mViewModel = getViewModel()

    }

    fun hideActionBar(){
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    abstract fun getViewModel():V


}