package app.olauncher.helper

import app.olauncher.data.Constants
import org.junit.Assert.*
import org.junit.Test

class HomeGridTest {
    @Test fun allFifteenSlotsHaveDistinctPickerFlagsWithoutOverridingGestures() {
        val flags = (1..15).map(Constants::homeAppFlag)
        assertEquals(15, flags.toSet().size)
        val otherActions = setOf(Constants.FLAG_SET_SWIPE_LEFT_APP, Constants.FLAG_SET_SWIPE_RIGHT_APP,
            Constants.FLAG_SET_CLOCK_APP, Constants.FLAG_SET_CALENDAR_APP, Constants.FLAG_SET_SCREEN_TIME_APP,
            Constants.FLAG_LAUNCH_APP, Constants.FLAG_HIDDEN_APPS)
        assertTrue(flags.none { it in otherActions })
        (1..15).forEach { assertEquals(it, Constants.homeAppPosition(Constants.homeAppFlag(it))) }
        otherActions.forEach { assertNull(Constants.homeAppPosition(it)) }
    }

    @Test fun fiveRowsPreserveTheOldScratchpadBoundaryAcrossScreenDensities() {
        // Representative default font heights and padding from each resource density.
        for ((text, padding) in listOf(31 to 6, 34 to 8, 37 to 10, 40 to 14)) {
            for (density in 1..4) {
                val height = HomeGridSizing.rowHeight(text * density, padding * density, 48 * density)
                val oldArea = 4 * (text + 2 * padding) * density + 56 * density
                val newArea = 5 * height + 8 * density
                assertTrue("Scratchpad boundary moved: $oldArea vs $newArea", oldArea - newArea in 0..4)
                assertTrue(height >= text * density)
            }
        }
    }

    @Test fun largeAccessibilityFontsAreNeverClippedVertically() {
        assertTrue(HomeGridSizing.rowHeight(240, 10, 48) >= 240)
    }
}
