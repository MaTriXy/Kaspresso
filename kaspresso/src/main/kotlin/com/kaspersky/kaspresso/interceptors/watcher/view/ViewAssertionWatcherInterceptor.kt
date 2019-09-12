package com.kaspersky.kaspresso.interceptors.watcher.view

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.view.View

/**
 * The interface for all view action interceptors, used in [com.kaspersky.kaspresso.proxy.ViewAssertionProxy].
 */
interface ViewAssertionWatcherInterceptor {

    /**
     * Called to do some stuff before [androidx.test.espresso.ViewAssertion.check] is actually called.
     *
     * @param viewAssertion responsible for performing assertions on a [View] element.
     * @param view an Android [View], on which [viewAssertion] is performed.
     * @param exception indicates that a given matcher did not match any elements in the view hierarchy.
     */
    fun intercept(viewAssertion: ViewAssertion, view: View?, exception: NoMatchingViewException?)
}