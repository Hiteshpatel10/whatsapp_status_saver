package com.geekaid.whatsappstatussaver.components.permission

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@ExperimentalPermissionsApi
fun PermissionState.isPermanentlyDenied(): Boolean {

    return !hasPermission && !shouldShowRationale
}