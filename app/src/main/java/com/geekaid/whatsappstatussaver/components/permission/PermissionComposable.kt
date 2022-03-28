package com.geekaid.whatsappstatussaver.components.permission

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@ExperimentalPermissionsApi
@Composable
fun PermissionComposable(permissionsStateMultiple: MultiplePermissionsState) {

    Column {

        Log.i("permissionComp", "PermissionComposable: else triggered ")
        permissionsStateMultiple.permissions.forEach { permissionState ->

            when (permissionState.permission) {
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {

                    when {
                        permissionState.hasPermission -> {
                            Text(text = "write has permission")
                        }

                        permissionState.shouldShowRationale -> {
                            Text(text = "write should show rationale")
                        }

                        permissionState.isPermanentlyDenied() -> {
                            Text(text = "write permanently denied")
                        }
                    }
                }

                Manifest.permission.READ_EXTERNAL_STORAGE -> {

                    when {
                        permissionState.hasPermission -> {
                            Text(text = "read has permission")
                        }

                        permissionState.shouldShowRationale -> {
                            Text(text = "read should show rationale")
                        }

                        permissionState.isPermanentlyDenied() -> {
                            Text(text = "read permanently denied")
                        }
                    }
                }
            }
        }
    }

}

