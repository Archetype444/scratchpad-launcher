# This fork

Home-screen app capacity is 15, arranged top-to-bottom in three equal columns of
five. Choose the number in **Settings → Apps on home screen**, then long-press
each empty slot to select an app or shortcut. Existing slot assignments and
backups remain compatible; the default app count remains unchanged.

The scratchpad keeps its space. At five rows, the app grid uses the former
four-row height plus 48dp of the old bottom gap, leaving 8dp at the bottom. Text
size is unchanged; row heights absorb pixel rounding. Extremely large
accessibility fonts may need more height to avoid clipping text. Landscape keeps
the same equal-width scratchpad and app sections. The optional recent-apps mode
retains upstream's original four pinned and four recent slots.

## Upstream updates

**Actions → Validate and sync upstream** checks `mstcgalis/scratchpad-launcher`'s
`main` every day at 05:23 UTC and can also be run manually. GitHub may delay
scheduled runs. It merges non-conflicting upstream changes and favors this
fork's edits in overlapping merge hunks, then verifies the 15-slot layout and
runs the Android build, unit tests and lint before pushing. It never force-pushes.
A harmless empty commit after 40 quiet days keeps GitHub from disabling the
requested schedule for repository inactivity.

If upstream makes an incompatible structural change, deletes a customized file,
or breaks a check, the workflow stops and leaves the last working branch intact.
No automated merge can guarantee compatibility with every future redesign;
such a run needs review in Actions. This prevents a failed sync from silently
reverting the customization or publishing a broken build.
