package test.assignment.project.presentation

import androidx.navigation3.runtime.NavKey
import test.assignment.project.R

sealed class BottomNavigationItem(
    val title: String,
    val iconRes: Int,
    val route: MainNavRoute
) : NavKey {
    object Main : BottomNavigationItem(
        title = "Main",
        iconRes = R.drawable.ic_book,
        route = MainNavRoute.Main
    )

    object Categories : BottomNavigationItem(
        title = "Categories",
        iconRes = R.drawable.ic_library,
        route = MainNavRoute.Categories
    )
}