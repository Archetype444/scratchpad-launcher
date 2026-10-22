package app.olauncher.helper

import app.olauncher.data.Constants

/** Fit five rows into the former four-row area plus the reclaimed bottom gap. */
object HomeGridSizing {
    fun rowHeight(textHeight: Int, verticalPadding: Int, reclaimedGap: Int): Int =
        maxOf(textHeight, (4 * (textHeight + 2 * verticalPadding) + reclaimedGap) / Constants.HOME_APP_ROWS)
}
