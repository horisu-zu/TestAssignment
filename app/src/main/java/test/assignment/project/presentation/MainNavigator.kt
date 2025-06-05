package test.assignment.project.presentation

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import test.assignment.project.presentation.categories.CategoriesNewsPage
import test.assignment.project.presentation.main.MainNewsPage

@Composable
fun MainNavigator() {
    val navItems = listOf(
        BottomNavigationItem.Main,
        BottomNavigationItem.Categories
    )
    val mainBackStack = rememberNavBackStack(MainNavRoute.Main)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                navItems.forEach { navItem ->
                    val isSelected = mainBackStack.last() == navItem.route

                    NavigationBarItem(
                        selected = isSelected,
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.iconRes),
                                contentDescription = navItem.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text(navItem.title) },
                        onClick = {
                            if(!isSelected) {
                                mainBackStack.add(navItem.route)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.surface,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = mainBackStack,
            onBack = { mainBackStack.removeLastOrNull() },
            modifier = Modifier.padding(
                bottom = innerPadding.calculateBottomPadding()
            ),
            entryProvider = entryProvider {
                entry<MainNavRoute.Main> {
                    MainNewsPage(
                        modifier = Modifier.padding(
                            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
                        )
                    )
                }
                entry<MainNavRoute.Categories> {
                    CategoriesNewsPage(
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                            end = innerPadding.calculateEndPadding(LayoutDirection.Ltr))
                    )
                }
            }
        )
    }
}