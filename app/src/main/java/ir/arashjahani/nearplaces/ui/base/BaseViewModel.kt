package ir.arashjahani.nearplaces.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import ir.arashjahani.nearplaces.data.DataRepository
import java.lang.ref.WeakReference

/**
 * Created By ArashJahani on 2020/04/17
 */

open class BaseViewModel<N> constructor(val dataRepository: DataRepository): ViewModel() {

    protected var compositeDisposable = CompositeDisposable()

    protected lateinit var isLoading: Observable<Boolean>

    private var mNavigator: WeakReference<N>? = null

    var navigator: N?
        get() = mNavigator!!.get()
        set(navigator) {
            this.mNavigator = WeakReference<N>(navigator)
        }

    init {
        compositeDisposable = CompositeDisposable()
    }


    override fun onCleared() {

        compositeDisposable.dispose()

        super.onCleared()
    }

}