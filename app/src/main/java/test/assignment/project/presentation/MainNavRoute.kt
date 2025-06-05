package test.assignment.project.presentation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface MainNavRoute : NavKey {
    @Serializable
    data object Main : MainNavRoute

    @Serializable
    data object Categories : MainNavRoute
}