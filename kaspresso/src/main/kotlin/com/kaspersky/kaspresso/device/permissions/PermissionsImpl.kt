package com.kaspersky.kaspresso.device.permissions

import android.os.Build
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObject
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import com.kaspersky.kaspresso.internal.extensions.other.getStackTraceAsString
import com.kaspersky.kaspresso.internal.wait.wait
import com.kaspersky.kaspresso.logger.UiTestLogger

/**
 * The implementation of the [Permissions] interface.
 */
class PermissionsImpl(
    private val logger: UiTestLogger,
    private val uiDevice: UiDevice
) : Permissions {

    private companion object {
        private const val DIALOG_TIMEOUT_MS: Long = 1_000
    }

    private val packageInstallerPackageName =
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P)
            "com.android.permissioncontroller"
        else
            "com.android.packageinstaller"

    private val buttonResNameMap = mapOf(
        Permissions.Button.ALLOW to getResIdWithPackageName("permission_allow_button"),
        Permissions.Button.ALLOW_ALWAYS to getResIdWithPackageName("permission_allow_always_button"),
        Permissions.Button.ALLOW_FOREGROUND to getResIdWithPackageName("permission_allow_foreground_only_button"),
        Permissions.Button.DENY to getResIdWithPackageName("permission_deny_button")
    )

    /**
     * Waits for 1 sec, passes the permission-requesting permissions dialog and allows permissions.
     */
    override fun allowViaDialog() =
        wait(timeoutMs = DIALOG_TIMEOUT_MS, logger = logger) { handlePermissionRequest(Permissions.Button.ALLOW) }

    /**
     * Waits for 1 sec, passes the permission-requesting permissions dialog and denies permissions.
     */
    override fun denyViaDialog() =
        wait(timeoutMs = DIALOG_TIMEOUT_MS, logger = logger) { handlePermissionRequest(Permissions.Button.DENY) }

    /**
     * Waits for 1 sec, passes the permission-requesting permissions dialog
     */
    override fun clickOn(button: Permissions.Button) =
        wait(timeoutMs = DIALOG_TIMEOUT_MS, logger = logger) { handlePermissionRequest(button) }

    /**
     * Waits for 1 sec, check the permission-requesting permissions dialog is visible.
     */
    override fun isDialogVisible(): Boolean =
        wait(timeoutMs = DIALOG_TIMEOUT_MS, logger = logger) {
            return@wait getPermissionDialogButtonAsUiObject(Permissions.Button.ALLOW)
                ?.exists()
                ?: getPermissionDialogButtonAsUiObject(Permissions.Button.DENY)
                    ?.exists()
                ?: false
        }

    /**
     * Passes the permission-requesting permissions dialog.
     *
     * @param buttonResId resource name of permission dialog button
     */
    private fun handlePermissionRequest(button: Permissions.Button) {
        val uiObjectButton = getPermissionDialogButtonAsUiObject(button)
        if (uiObjectButton != null && uiObjectButton.exists()) {
            uiObjectButton.click()
        } else {
            logger.e("In method handlePermissionRequest button=$button is not exist or is not found.")
        }
    }

    /**
     * Get button of permissions' dialog as UIObject of UI Automator or null if UiObject was not found
     */
    private fun getPermissionDialogButtonAsUiObject(button: Permissions.Button): UiObject? {
        return try {
            val btnSelector = UiSelector()
                .clickable(true)
                .checkable(false)
                .resourceId(buttonResNameMap[button])
            uiDevice.findObject(btnSelector)
        } catch (e: UiObjectNotFoundException) {
            logger.e("There are no permissions dialog to interact with.")
            null
        } catch (e: Throwable) {
            logger.e(e.getStackTraceAsString())
            throw e
        }
    }

    private fun getResIdWithPackageName(resId: String): String {
        return "$packageInstallerPackageName:id/$resId"
    }
}