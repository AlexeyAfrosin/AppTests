package com.afrosin.apptests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.afrosin.apptests.R
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val packageName = context.packageName

    @Before
    fun setUp() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    companion object {
        private const val TIMEOUT = 50000L
    }

    @Test
    fun test_MainActivityIsStarted() {
        //Через uiDevice находим editText
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))

        editText.text = "UiAutomator"

        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())

        val changedText = uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            TIMEOUT
        )

        assertEquals(changedText.text.toString(), "Number of results: 42")
    }

    @Test
    fun test_OpenDetailsScreen() {

        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))

        toDetails.click()

        val changedText =
            uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)

        assertEquals(changedText.text, "Number of results: 0")
    }


}