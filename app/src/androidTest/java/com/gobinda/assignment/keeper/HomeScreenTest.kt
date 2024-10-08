import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gobinda.assignment.keeper.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_appBar() {
        composeTestRule.onNodeWithText("Home").assertExists()
    }

    @Test
    fun test_loading() {
        with(composeTestRule){
            waitUntil(1000){
                onNodeWithText("Loading...").isDisplayed()
            }
        }
    }

    @Test
    fun test_item_displayedActionClickOnProduct() {
        with(composeTestRule){
            waitUntil(1000000){
                onNodeWithText("Camera").isDisplayed()
            }
            onNodeWithText("Camera").performClick()
        }
    }

}