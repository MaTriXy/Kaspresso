package com.kaspersky.kaspresso.testcases.runners

import com.kaspersky.kaspresso.testcases.SubCase
import com.kaspersky.kaspresso.testcases.Scenario

/**
 * An implementation of [ScenarioRunner] for [SubCase]'s usage.
 */
class SubCaseRunner(private val title: String) : ScenarioRunner {

    /**
     * Runs [SubCase]'s [steps]. Called by [SubCase.run] with [SubCase.steps] as an argument.
     *
     * @param steps steps to run.
     */
    override fun runSteps(steps: Scenario.() -> Unit) {
        val scenario = Scenario(title) { title, description -> "$title STEP: \"$description\"" }
        steps.invoke(scenario)
    }
}