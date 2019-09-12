package com.kaspersky.kaspressample.configurator_tests.interceptors

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.view.View
import com.kaspersky.kaspressample.configurator_tests.helpers.CheckCustomInterceptorsStorage
import com.kaspersky.kaspresso.interceptors.watcher.view.ViewAssertionWatcherInterceptor

class CustomViewAssertionWatcherInterceptor : ViewAssertionWatcherInterceptor {

    private var previousViewAssertion: ViewAssertion? = null
    private var previousView: View? = null

    override fun intercept(viewAssertion: ViewAssertion, view: View?, exception: NoMatchingViewException?) {
        // we don't count retry attempts to execute an assertion, but in the example they are
        if (previousView?.id == view?.id && previousViewAssertion.toString() == viewAssertion.toString()) {
            return
        }
        CheckCustomInterceptorsStorage.putToViewAssertionInterceptorCheckList()
        previousView = view
        previousViewAssertion = viewAssertion
    }
}