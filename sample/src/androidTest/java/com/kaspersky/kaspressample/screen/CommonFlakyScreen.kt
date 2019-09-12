package com.kaspersky.kaspressample.screen

import android.support.test.espresso.matcher.ViewMatchers.withId
import com.agoda.kakao.text.KButton
import com.kaspersky.kaspressample.R
import com.kaspersky.kaspressample.flaky.CommonFlakyActivity

object CommonFlakyScreen : KScreen<CommonFlakyScreen>() {

    override val layoutId: Int? = R.layout.activity_common_flaky
    override val viewClass: Class<*>? = CommonFlakyActivity::class.java

    val btn1 = KButton {
        withId(R.id.scroll_view_btn1)
    }

    val btn3 = KButton {
        withId(R.id.scroll_view_btn3)
    }

    val btn5 = KButton {
        withId(R.id.scroll_view_btn5)
    }

    val tv6 = KButton {
        withId(R.id.scroll_view_tv6)
    }
}